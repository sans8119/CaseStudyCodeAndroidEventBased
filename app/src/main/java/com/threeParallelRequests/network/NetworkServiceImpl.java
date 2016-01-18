package com.threeParallelRequests.network;

import android.util.Log;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.threeParallelRequests.Notifications.Controller2NetworkNotification;
import com.threeParallelRequests.Notifications.Network2ControllerNotification;
import com.threeParallelRequests.cache.UserCache;
import com.threeParallelRequests.controller.BusProvider;

import java.util.HashMap;
import java.util.Map;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.mime.TypedByteArray;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func3;
import rx.schedulers.Schedulers;

public class NetworkServiceImpl implements NetworkService {
    private final NetworkDataFetchWebService mWebService;
    //private NetworkErrorHandler networkErrorHandler;
    private Bus bus;

    public NetworkServiceImpl() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                request.addHeader("Accept", "text/html");
            }
        };
       // networkErrorHandler=new NetworkErrorHandler();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(WEB_SERVICE_BASE_URL)
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                //.setErrorHandler(new NetworkErrorHandler())
                .build();

        mWebService = restAdapter.create(NetworkDataFetchWebService.class);
        bus = BusProvider.INSTANCE.getBus();
        bus.register(this);
    }

    @Subscribe
    @Override
    public void answerAvailable(Controller2NetworkNotification event) {
            getDataFromNetwork();
    }

    @Override
    public void sendNetworkResultToController(Map ntwkDataMap) {
        bus.post(new Network2ControllerNotification(ntwkDataMap));
    }

    @Override
    public void sendNetworkResultToController(String errorMessage) {
        bus.post(new Network2ControllerNotification(errorMessage));
    }

    @Override
    public void getDataFromNetwork() {
        Subscription subscription = Observable.zip(fetchDataFor10thWord(), fetchDataForEvery10thWord(), fetchDataForEveryWordCount(), new Func3<Response, Response, Response, Map<String, String>>() {
            @Override
            public Map<String, String> call(Response res1, Response res2, Response res3) {
                Map<String, String> map = new HashMap<String, String>();
                map.put(UserCache.KEY_DATA_FOR_10TH_WORD, getStringFromResponse(res1));
                map.put(UserCache.keyEvery10thChar, getStringFromResponse(res2));
                map.put(UserCache.keyEveryCharsCount, getStringFromResponse(res3));
                return map;
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Map<String, String>>() {
                    @Override
                    public final void onCompleted() {
                    }

                    @Override
                    public final void onError(Throwable e) {
                        sendNetworkResultToController("Network error: "+e.getMessage());
                    }

                    @Override
                    public final void onNext(Map<String, String> map) {
                        sendNetworkResultToController(map);
                    }
                });
    }


    private interface NetworkDataFetchWebService {
        @GET("/support")
        Observable<Response> fetchDataFromNetworkToFind10thWord();

        @GET("/support")
        Observable<Response> fetchDataFromNetworkToFindEvery10thWord();

        @GET("/support")
        Observable<Response> fetchDataFromNetworkToFindEveryWordCount();

    }

    private Observable<Response> fetchDataFor10thWord() {
        return mWebService.fetchDataFromNetworkToFind10thWord();
    }

    private Observable<Response> fetchDataForEvery10thWord() {
        return mWebService.fetchDataFromNetworkToFindEvery10thWord();
    }

    private Observable<Response> fetchDataForEveryWordCount() {
        return mWebService.fetchDataFromNetworkToFindEveryWordCount();
    }

    private String getStringFromResponse(Response response) {
        return new String(((TypedByteArray) response.getBody()).getBytes());
    }

}