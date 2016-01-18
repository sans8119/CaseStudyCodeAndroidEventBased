package com.threeParallelRequests.views.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.threeParallelRequests.controller.Controller;
import com.threeParallelRequests.controller.ControllerImplFlow1;

//import com.threeParallelRequests.dagger.components.PerActivityComponent;
//import com.threeParallelRequests.dagger.modules.PerActivityUserModule;

public class BlankFragment extends Fragment {

    public Controller getController() {
        return controller;
    }

   // @Inject
    private Controller controller;
    //private PerActivityComponent userComponent;

    private String[] uiData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        controller = new ControllerImplFlow1();//getUserComponent().provideControllerFlow1();
        uiData = new String[2];
    }

    public void setData(String[] data) {
        uiData[0] = data[0];
        uiData[1] = data[1];
    }

    public String[] getData() {
        return uiData;
    }

  /*  private PerActivityComponent getUserComponent() {
            userComponent = DaggerPerActivityComponent.builder()
                    .perActivityUserModule(new PerActivityUserModule((MainActivity) getActivity()))
                    .build();
        return userComponent;
    }*/

}
