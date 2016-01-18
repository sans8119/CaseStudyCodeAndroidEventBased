package com.threeParallelRequests.model.data;

import com.threeParallelRequests.utils.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public class NetworkDataWrapper {
    protected String networkData1;// The raw data which is obtained from internet
    protected String networkData2;// The raw data which is obtained from internet
    protected String uiDataToFindEvery10thWord;//The data obtained after processing networkData;this data will be shown on uI
    protected String uiDataToFindWordCount;//The data obtained after processing networkData;this data will be shown on uI

    public NetworkDataWrapper(String networkData1,String networkData2){
        this.networkData1=networkData1;
        this.networkData2=networkData2;
        makeUiDataToFindEvery10thWord();
        makeUiDataToFindWordCount();
    }


    public String getUiDataToFindEvery10thWord() {
        return uiDataToFindEvery10thWord;
    }

    public String getUiDataToFindWordCount() {
        return uiDataToFindWordCount;
    }

    private void makeUiDataToFindEvery10thWord() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < networkData1.length(); ) {
            buffer.append("character at ").append(i).append(" position").append(":").append(networkData1.charAt(i)).append("\n");
            i += 10;
            //Showing only first Constants.NUMBER_OF_LINES_2_SHOW lines of data
            if (i == Constants.NUMBER_OF_LINES_2_SHOW * 10)
                break;
        }
        networkData1 = "";
        uiDataToFindEvery10thWord = buffer.toString();
    }

    private void makeUiDataToFindWordCount() {
        WeakHashMap<String, Integer> map = new WeakHashMap<String, Integer>();
        StringReader reader = new StringReader(networkData2);
        BufferedReader br = new BufferedReader(reader);
        String line;
        try {
            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                for (String word : words) {
                    if (map.containsKey(word.trim())) {
                        map.put(word.trim(), map.get(word.trim()) + 1);
                    } else {
                        map.put(word.trim(), 1);
                    }
                }
            }
            networkData2 = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuffer buffer = new StringBuffer();
        Set set = map.keySet();
        Iterator<String> itr = set.iterator();
        int counter = 0;
        while (itr.hasNext()) {
            String key = itr.next();
            buffer.append("Count of ' ").append(key).append(" ': ").append(map.get(key)).append("\n");
            ++counter;
            if (counter == Constants.NUMBER_OF_LINES_2_SHOW)
                break;
        }
        uiDataToFindWordCount = buffer.toString();
    }
}