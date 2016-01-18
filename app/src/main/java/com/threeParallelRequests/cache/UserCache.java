package com.threeParallelRequests.cache;

import com.threeParallelRequests.Notifications.Controller2CacheNotification;
import com.threeParallelRequests.model.data.NetworkDataWrapper;

public interface UserCache {
    String keyEvery10thChar="every_10th_char";
    String keyEveryCharsCount ="every_chars_count";
    String KEY_DATA_FOR_10TH_WORD ="fetch_data_for_10th_word";

    boolean isCached(final String key);
    boolean isCacheFull();
    void evictAll();
    void evict(String key);
    public String get(final String key);
    public void put(String key, String networkDataEnvelope);
    public void sendIsCacheEmptySignalToController();
    void sendDSToController(Controller2CacheNotification controller2CacheEvent);
}
