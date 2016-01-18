package com.threeParallelRequests.Notifications;

public class MainActivity2ControllerNotification implements Flow1 {
    private int signal;

    public String toString() {
        return getClass().getCanonicalName();
    }

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }
}
