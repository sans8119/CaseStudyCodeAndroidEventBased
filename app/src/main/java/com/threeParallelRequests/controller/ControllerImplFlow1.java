package com.threeParallelRequests.controller;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.threeParallelRequests.Notifications.Cache2ControllerNotification;
import com.threeParallelRequests.Notifications.Controller2CacheNotification;
import com.threeParallelRequests.Notifications.Controller2MainActivityNotification;
import com.threeParallelRequests.Notifications.Controller2ModelNotification;
import com.threeParallelRequests.Notifications.Controller2NetworkNotification;
import com.threeParallelRequests.Notifications.Flow1;
import com.threeParallelRequests.Notifications.MainActivity2ControllerNotification;
import com.threeParallelRequests.Notifications.Model2ControllerNotification;
import com.threeParallelRequests.Notifications.Network2ControllerNotification;
import com.threeParallelRequests.cache.UserCache;
import com.threeParallelRequests.cache.UserCacheImpl;
import com.threeParallelRequests.model.BaseUseCase;
import com.threeParallelRequests.model.data.NetworkDataWrapper;
import com.threeParallelRequests.network.NetworkService;
import com.threeParallelRequests.network.NetworkServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class ControllerImplFlow1 implements Controller {
   // @Inject
    NetworkService service;
   // @Inject
    UserCache cache;
   // @Inject
    BaseUseCase baseUseCase;
    private Bus bus;
    //private PerAppComponent perAppComponent;

    public ControllerImplFlow1() {
        service =new NetworkServiceImpl();// getPerAppComponent().provideNetworkDataService();
        baseUseCase = new BaseUseCase();//getPerAppComponent().provideBaseUseCase();
        cache = new UserCacheImpl();//getPerAppComponent().provideUserCache();
        bus = BusProvider.INSTANCE.getBus();
        bus.register(this);
    }

    @Subscribe
    public void answerAvailable(Network2ControllerNotification event) {
        if (event.getErrorMessage() == null && event.getMap() != null)
            sendNetworkData2Model(event.getMap());
        else {
            Map<String, String> map = new HashMap<String, String>();
            map.put(UserCache.keyEvery10thChar, event.getErrorMessage());
            map.put(UserCache.keyEveryCharsCount, event.getErrorMessage());
            sendData2MainActivity(map);
        }
    }

    @Subscribe
    public void answerAvailable(MainActivity2ControllerNotification event) {
        if (event.getSignal() == Flow1.GET_DATA) {
            isCacheEmpty();
        } else if (event.getSignal() == Flow1.CLEAR_CACHE) {
            clearCache();
        } else if (event.getSignal() == Flow1.RESET_BUS) {
            BusProvider.INSTANCE.reset();
        }
    }

    @Subscribe
    public void answerAvailable(Model2ControllerNotification event) {
        putDataInCache(event.getNetworkdataenvelops());
    }

    @Subscribe
    public void answerAvailable(Cache2ControllerNotification event) {
        if (event.getSignal() == Flow1.IS_CACHE_EMPTY) {
            if (event.isCacheEmpty()) {
                getDataFromNetwork();
            } else {
                String[] dataEnvelopes = event.getNetworkDataEnvelopes();
                sendData2MainActivity(createMapHavingData2ShowOnUi(dataEnvelopes));
            }
        } else if (event.getSignal() == Flow1.PUT_DATA_IN_CACHE) {
            String[] dataEnvelopes = event.getNetworkDataEnvelopes();
            sendData2MainActivity(createMapHavingData2ShowOnUi(dataEnvelopes));
        }
    }

    private Map<String, String> createMapHavingData2ShowOnUi(String[] dataEnvelopes) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(UserCache.keyEvery10thChar, dataEnvelopes[0].toString());
        map.put(UserCache.keyEveryCharsCount, dataEnvelopes[1].toString());
        return map;
    }

    @Override
    public void sendData2MainActivity(Map<String, String> screenData) {
        Controller2MainActivityNotification event = new Controller2MainActivityNotification(screenData);
        bus.post(event);
    }

    @Override
    public void getDataFromNetwork() {
        bus.post(new Controller2NetworkNotification());
    }

    @Override
    public void sendNetworkData2Model(Map<String, String> networkData) {
        Controller2ModelNotification event = new Controller2ModelNotification(networkData);
        event.setUseCase(BaseUseCase.MAKE_DS_USE_CASE);
        bus.post(event);
    }

    @Override
    public void isCacheEmpty() {
        Controller2CacheNotification event = new Controller2CacheNotification();
        event.setSignal(Flow1.IS_CACHE_EMPTY);
        bus.post(event);
    }

    @Override
    public void putDataInCache(String[] networkdataenvelops) {
        Controller2CacheNotification event = new Controller2CacheNotification();
        event.setNetworkDataEnvelopes(networkdataenvelops);
        event.setSignal(Flow1.PUT_DATA_IN_CACHE);
        bus.post(event);
    }

    @Override
    public void clearCache() {
        Controller2CacheNotification event = new Controller2CacheNotification();
        event.setSignal(Flow1.CLEAR_CACHE);
        bus.post(event);
    }

 /*   private PerAppComponent getPerAppComponent() {
        perAppComponent = DaggerPerAppComponent.builder()
                .perAppUserModule(new PerAppUserModule())
                .build();
        return perAppComponent;
    }*/

}
