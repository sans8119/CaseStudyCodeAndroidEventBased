package com.threeParallelRequests.model;

import com.threeParallelRequests.Notifications.Model2ControllerNotification;
import com.threeParallelRequests.cache.UserCache;
import com.threeParallelRequests.model.data.NetworkDataWrapper;

import java.util.Map;

public class MakeDataStructuresUseCase extends BaseUseCase {
    private Map<String, String> networkData;

    public MakeDataStructuresUseCase(Map<String, String> networkData) {
        this.networkData = networkData;
    }

    protected void sendDS2Controller() {
        NetworkDataWrapper wrapper=new NetworkDataWrapper(networkData.get(UserCache.keyEvery10thChar),networkData.get(UserCache.keyEveryCharsCount));
        String[] uiData=new String[]{wrapper.getUiDataToFindEvery10thWord(),wrapper.getUiDataToFindWordCount()};
      /*  NetworkDataWrapper[] networkdataenvelops = new NetworkDataWrapper[2];
        networkdataenvelops[0] = makeNetworkDataEnvelopToFindEvery10thWord(networkData.get(UserCache.keyEvery10thChar));
        networkdataenvelops[1] = makeNetworkDataEnvelopToFindEveryWordCount(networkData.get(UserCache.keyEveryCharsCount));*/
        Model2ControllerNotification event = new Model2ControllerNotification(uiData);
        bus.post(event);
    }

    /*private NetworkDataWrapper makeNetworkDataEnvelopToFindEvery10thWord(String data) {
        return new NetworkDataWrapperToFindEvery10thWord(data);//
    }

    private NetworkDataWrapper makeNetworkDataEnvelopToFindEveryWordCount(String data) {
        return new NetworkDataWrapperToFindEveryWordCount(data);
    }*/

}
