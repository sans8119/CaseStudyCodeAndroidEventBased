package com.threeParallelRequests.Notifications;

import java.util.Map;

public class Controller2ModelNotification implements Flow1 {
    private Map<String, String> map;
    private int useCase;
    private int signal;

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

    public void setUseCase(int useCase) {
        this.useCase = useCase;
    }

    public int getUseCase() {
        return useCase;
    }

    public String toString() {
        return getClass().getCanonicalName();
    }

    public Controller2ModelNotification(Map<String, String> ntwkDataMap) {
        map = ntwkDataMap;
    }

    public Map<String, String> getMap() {
        return map;
    }
}
