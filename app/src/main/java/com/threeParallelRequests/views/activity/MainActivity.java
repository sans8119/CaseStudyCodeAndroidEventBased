package com.threeParallelRequests.views.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.threeParallelRequests.Notifications.Controller2MainActivityNotification;
import com.threeParallelRequests.Notifications.Flow1;
import com.threeParallelRequests.Notifications.MainActivity2ControllerNotification;
import com.threeParallelRequests.R;
import com.threeParallelRequests.cache.UserCache;
import com.threeParallelRequests.controller.BusProvider;
import com.threeParallelRequests.controller.Controller;
import com.threeParallelRequests.utils.Constants;
import com.threeParallelRequests.utils.ExceptionHandler;
import com.threeParallelRequests.views.adapter.FragmentPagerAdapterExtn;
import com.threeParallelRequests.views.fragment.BlankFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.threeParallelRequests.R.id;

public class MainActivity extends AppCompatActivity {

    @Bind(id.pager)
    ViewPager mViewPager;
    //@Inject
    FragmentPagerAdapterExtn fragmentPagerAdapterExtn;
    Controller controller;
    @Bind(R.id.get_data_button)
    Button getDataButton;
    @Bind(R.id.cleart_data_button)
    Button clearDataButton;
    private boolean onBackPressed;
    private Bus bus;
   // private PerActivityComponent userComponent;
    private BlankFragment blankFragment;
    private final String BLANK_FRAGMENT = "blank_fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("TrueCaller.com");
        actionBar.setTitle("3 Requests");
        actionBar.show();

        bus = BusProvider.INSTANCE.getBus();
        fragmentPagerAdapterExtn = new FragmentPagerAdapterExtn(this);//getUserComponent().provideFragmentPagerAdapter();//getAdapterComponent().provideFragmentPagerAdapter();
        blankFragment = (BlankFragment) getSupportFragmentManager()
                .findFragmentByTag(BLANK_FRAGMENT);
        if (blankFragment == null) {
            blankFragment = new BlankFragment();
            getSupportFragmentManager().beginTransaction().add(blankFragment, BLANK_FRAGMENT).commit();
            fragmentPagerAdapterExtn.setData(new String[]{"", ""});
            mViewPager.setAdapter(fragmentPagerAdapterExtn);
        } else {
            String[] strings = blankFragment.getData();
            fragmentPagerAdapterExtn.setData(strings);
            mViewPager.setAdapter(fragmentPagerAdapterExtn);
        }
        controller = blankFragment.getController();
    }

    @Override
    public void onBackPressed() {
        onBackPressed = true;
        sendBusResetEventToController();
        super.onBackPressed();
    }

    @Override
    public void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!onBackPressed)
            BusProvider.INSTANCE.getBus().unregister(this);
    }

    @Subscribe
    public void answerAvailable(Controller2MainActivityNotification event) {
        getDataButton.setEnabled(true);//clearDataButton.setEnabled(true);
        getDataButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        Map<String, String> map = event.getData();
        fragmentPagerAdapterExtn.setData(new String[]{map.get(UserCache.keyEvery10thChar), map.get(UserCache.keyEveryCharsCount)});
        fragmentPagerAdapterExtn.notifyDataSetChanged();
        blankFragment.setData(new String[]{map.get(UserCache.keyEvery10thChar), map.get(UserCache.keyEveryCharsCount)});
        Toast.makeText(this, Constants.NUMBER_OF_LINES_2_SHOW + " " + getResources().getString(R.string.of_total_data_will_be_shown), Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.get_data_button)
    public void sendEventToController() {
        getDataButton.setEnabled(false);//clearDataButton.setEnabled(false);
        getDataButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        MainActivity2ControllerNotification event = new MainActivity2ControllerNotification();
        event.setSignal(Flow1.GET_DATA);
        bus.post(event);
    }

    @OnClick(R.id.cleart_data_button)
    public void clear() {
        MainActivity2ControllerNotification event = new MainActivity2ControllerNotification();
        event.setSignal(Flow1.CLEAR_CACHE);
        bus.post(event);
        Map<String, String> map = new HashMap<String, String>();
        map.put(UserCache.keyEvery10thChar, "");
        map.put(UserCache.keyEveryCharsCount, "");
        fragmentPagerAdapterExtn.setData(new String[]{"", ""});
        fragmentPagerAdapterExtn.notifyDataSetChanged();
    }

    private void sendBusResetEventToController() {
        MainActivity2ControllerNotification event = new MainActivity2ControllerNotification();
        event.setSignal(MainActivity2ControllerNotification.RESET_BUS);
        bus.post(event);
    }

    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

/*    private PerActivityComponent getUserComponent() {
        if (userComponent == null) {
            userComponent = DaggerPerActivityComponent.builder()
                    .perActivityUserModule(new PerActivityUserModule(this))
                    .build();
        }
        return userComponent;
    }*/

}
