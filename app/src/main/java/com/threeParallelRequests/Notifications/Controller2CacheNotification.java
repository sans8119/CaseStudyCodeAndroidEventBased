package com.threeParallelRequests.Notifications;

import com.threeParallelRequests.model.data.NetworkDataWrapper;

public class Controller2CacheNotification implements Flow1 {
    private String[]  networkDataEnvelopes;
    private int signal;

    public Controller2CacheNotification(String[]  networkDataEnvelopes) {
        this.networkDataEnvelopes = networkDataEnvelopes;
    }

    public Controller2CacheNotification() {
    }

    public String[]  getNetworkDataEnvelopes() {
        return networkDataEnvelopes;
    }

    public void setNetworkDataEnvelopes(String[]  envelopes) {
        networkDataEnvelopes = envelopes;
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
