package com.threeParallelRequests.network;


import com.threeParallelRequests.Notifications.Controller2NetworkNotification;

import java.util.Map;

public interface NetworkService {
    public String WEB_SERVICE_BASE_URL = "http://www.truecaller.com";

    public void answerAvailable(Controller2NetworkNotification event);

    public void sendNetworkResultToController(Map<String, String> ntwkDataMap);

    public void sendNetworkResultToController(String errorMessage);

    public void getDataFromNetwork();
}
