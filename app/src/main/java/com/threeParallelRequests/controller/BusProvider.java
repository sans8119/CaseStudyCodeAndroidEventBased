package com.threeParallelRequests.controller;

import com.squareup.otto.Bus;

public enum BusProvider {
    INSTANCE;
    private static Bus bus = new Bus();

    public Bus getBus() {
        if (bus == null)
            bus = new Bus();
        return bus;
    }

     void reset() {
        bus = null;
    }

}