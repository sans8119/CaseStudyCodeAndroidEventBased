package com.threeParallelRequests.Notifications;

import com.threeParallelRequests.model.data.NetworkDataWrapper;

public class Model2ControllerNotification implements Flow1 {
    String[] networkdataenvelops;
    private int signal;

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }


    public String[] getNetworkdataenvelops() {
        return networkdataenvelops;
    }

    public Model2ControllerNotification(String[] networkdataenvelops) {
        this.networkdataenvelops = networkdataenvelops;
    }

    public String toString() {
        return getClass().getCanonicalName();
    }
}
