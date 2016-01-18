
Clicking on 'Get Data ' hits the network and initiates 3 parallel network requests. Clicking on 'Clear Cache' button will 
clear the data which was fetched from network and stored in cache. Hence any click to 'Get Data' after a click to 'Clear Cache'
button will make the app hit the network with 3 parallel requests. If 'Get Data' button is clicked after prior clicking on
'Get Data' button then the app will fetch the data from the cache instead of network.

If the number of lines to be shown is more than 2000 lines then only first 2000 lines of data is being shown. This is 
because if say we try to show 10000 lines then setting data to Textview hangs the ui. We could use a ListView instead 
of TextView to overcome this problem.

Known Bugs:
1) After orientation change data is not shown on ui; on clicking 'Get Data ' button data is shown again. App does not go to network for getting data in this case. Due to some reasons Adapter is not refreshing the ui.
   
Improvements possible:
1) Dagger may not be needed at all as we do not have a deep dependencies for creating any of the objects.
2) Instead of having 3 classes in package com.threeParallelRequests.model.data we could as of now have only one class which will have 2 fields, one to hold string foo every 10th word and one to hold string for word counts.
3) We need to think of a way to reduce number of classes in package com.threeParallelRequests.Notifications. All classes in this package are light and hence easy to create and gargage collect. But it would be good to have a way to decrease number of classes.
2) Pagination so that all data can be shown on ui.
