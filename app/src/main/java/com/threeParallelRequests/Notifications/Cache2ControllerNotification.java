package com.threeParallelRequests.Notifications;

import com.threeParallelRequests.model.data.NetworkDataWrapper;

public class Cache2ControllerNotification implements Flow1 {
    private int signal;
    private boolean cacheEmpty;
    private String[] networkDataEnvelopes;

    public Cache2ControllerNotification(String[] networkDataEnvelopes) {
        this.networkDataEnvelopes = networkDataEnvelopes;
    }

    public Cache2ControllerNotification() {
    }

    public String[] getNetworkDataEnvelopes() {
        return networkDataEnvelopes;
    }

    public void setNetworkDataEnvelopes(String[] networkDataEnvelopes) {
        this.networkDataEnvelopes = networkDataEnvelopes;
    }

    public boolean isCacheEmpty() {
        return cacheEmpty;
    }

    public void setCacheEmpty(boolean cacheEmpty) {
        this.cacheEmpty = cacheEmpty;
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
}
