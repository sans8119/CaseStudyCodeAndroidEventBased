package com.threeParallelRequests.Notifications;

import java.util.Map;

public class Controller2MainActivityNotification implements Flow1 {
    private Map<String, String> data;

    public Controller2MainActivityNotification() {
    }

    public Controller2MainActivityNotification(Map<String, String> data) {
        this.data = data;
    }

    public String toString() {
        return getClass().getCanonicalName();
    }

    public Map<String, String> getData() {
        return data;
    }

}
