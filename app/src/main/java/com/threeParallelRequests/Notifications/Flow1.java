package com.threeParallelRequests.Notifications;

//An interface which is extended by all Notifications which will be a part of a Flow which does the following:
// (1) check internal cache if data is available.
// (2)If data is available in internal cache fetch it and show it on ui
// (3) if not available go to network and get data
//(4) Process the raw network data to get the relevant data and store it in internal cache as well as show it on UI
public interface Flow1 {
      int RESET_BUS = -2;
      int GET_DATA = 0;
      int CLEAR_CACHE = 1;
      int IS_CACHE_EMPTY = 2;
      int PUT_DATA_IN_CACHE = 3;
}
