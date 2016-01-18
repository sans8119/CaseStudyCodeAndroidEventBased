package com.threeParallelRequests.Notifications;

import java.util.Map;

public class Network2ControllerNotification implements Flow1 {
    private Map<String, String> map;
    private String errorMessage;
    private int signal;

    public Network2ControllerNotification(Map<String, String> ntwkDataMap) {
        map = ntwkDataMap;
    }

    public Network2ControllerNotification(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Network2ControllerNotification() {
    }

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

    public String toString() {
        return getClass().getCanonicalName();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setMap(Map<String, String> ntwkMap) {
        map = ntwkMap;
    }

    public Map<String, String> getMap() {
        return map;
    }

}
