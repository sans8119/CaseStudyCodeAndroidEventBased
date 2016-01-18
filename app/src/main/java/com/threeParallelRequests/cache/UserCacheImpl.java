package com.threeParallelRequests.cache;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.threeParallelRequests.Notifications.Cache2ControllerNotification;
import com.threeParallelRequests.Notifications.Controller2CacheNotification;
import com.threeParallelRequests.Notifications.Flow1;
import com.threeParallelRequests.model.data.NetworkDataWrapper;
import com.threeParallelRequests.controller.BusProvider;

import java.util.HashMap;

public class UserCacheImpl implements UserCache {

    private HashMap<String, String> internalCache;
    private Bus bus;

    public UserCacheImpl() {
        internalCache = new HashMap<String, String>();
        bus = BusProvider.INSTANCE.getBus();
        bus.register(this);
    }

    @Subscribe
    public void answerAvailable(Controller2CacheNotification event) {
        if (event.getSignal() == Flow1.PUT_DATA_IN_CACHE) {
            internalCache.put(UserCache.keyEvery10thChar, event.getNetworkDataEnvelopes()[0]);
            internalCache.put(UserCache.keyEveryCharsCount, event.getNetworkDataEnvelopes()[1]);
            sendDSToController(event);
        } else if (event.getSignal() == Flow1.IS_CACHE_EMPTY) {
            sendIsCacheEmptySignalToController();
        } else if (event.getSignal() == Flow1.CLEAR_CACHE) {
            evictAll();
        }
    }

    @Override
    public void sendIsCacheEmptySignalToController() {
        Cache2ControllerNotification event = new Cache2ControllerNotification();
        event.setSignal(Flow1.IS_CACHE_EMPTY);
        event.setCacheEmpty(internalCache.isEmpty());
        if (!internalCache.isEmpty()) {
            event.setNetworkDataEnvelopes(new String[]{internalCache.get(UserCache.keyEvery10thChar), internalCache.get(UserCache.keyEveryCharsCount)});
        }
        bus.post(event);
    }

    @Override
    public void sendDSToController(Controller2CacheNotification controller2CacheEvent) {
        Cache2ControllerNotification event = new Cache2ControllerNotification();
        event.setSignal(Flow1.PUT_DATA_IN_CACHE);
        String[] envelopes = new String[2];
        envelopes[0] = internalCache.get(UserCache.keyEvery10thChar);
        envelopes[1] = internalCache.get(UserCache.keyEveryCharsCount);
        event.setNetworkDataEnvelopes(envelopes);
        bus.post(event);
    }

    @Override
    public String get(String key) {
        String envelope = null;
        if (internalCache.containsKey(key))
            envelope = internalCache.get(key);
        return envelope;
    }

    @Override
    public void put(String key, String networkDataEnvelope) {
        if (key.equals(keyEvery10thChar) || key.equals(keyEveryCharsCount))
            internalCache.put(key, networkDataEnvelope);
    }

    @Override
    public boolean isCached(String key) {
        return internalCache.containsKey(key);
    }

    @Override
    public boolean isCacheFull() {
        boolean flag = false;
        if (internalCache.containsKey(keyEvery10thChar) && internalCache.containsKey(keyEveryCharsCount))
            flag = true;
        return flag;
    }

    @Override
    public void evictAll() {
        internalCache.clear();
    }

    @Override
    public void evict(String key) {
        internalCache.remove(key);
    }
}
