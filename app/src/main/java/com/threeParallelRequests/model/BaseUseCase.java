package com.threeParallelRequests.model;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.threeParallelRequests.Notifications.Controller2ModelNotification;
import com.threeParallelRequests.controller.BusProvider;

public class BaseUseCase {
    public static final int MAKE_DS_USE_CASE = 0;
    protected Bus bus;

    public BaseUseCase() {
        bus = BusProvider.INSTANCE.getBus();
        bus.register(this);
    }

    @Subscribe
    public void answerAvailable(Controller2ModelNotification event) {
        if (event.getUseCase() == BaseUseCase.MAKE_DS_USE_CASE) {
            MakeDataStructuresUseCase useCase = new MakeDataStructuresUseCase(event.getMap());
            useCase.sendDS2Controller();
        }
    }
}
