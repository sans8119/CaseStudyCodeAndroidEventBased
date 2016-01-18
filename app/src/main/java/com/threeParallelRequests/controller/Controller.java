package com.threeParallelRequests.controller;

import com.threeParallelRequests.Notifications.Flow1;
import com.threeParallelRequests.Notifications.Model2ControllerNotification;
import com.threeParallelRequests.model.data.NetworkDataWrapper;

import java.util.Map;

public interface Controller {

    void getDataFromNetwork();

    void isCacheEmpty();

    void clearCache();

    void putDataInCache(String[] networkdataenvelops);

    void sendData2MainActivity(Map<String, String> screenData);

    void sendNetworkData2Model(Map<String, String> networkData);
}
