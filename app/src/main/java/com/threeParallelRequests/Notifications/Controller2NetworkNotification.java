package com.threeParallelRequests.Notifications;

public class Controller2NetworkNotification implements Flow1 {
    private int signal;

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
