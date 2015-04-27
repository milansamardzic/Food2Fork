package com.milansamardzic.food2fork;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by ms on 1/14/15.
 */
public class Fork2FoodClient {
    //private final String API_KEY = "385e030f0db665f1aa2969efe478d451";
    private final String API_BASE_URL = "http://food2fork.com/api/search?key=385e030f0db665f1aa2969efe478d451";

    private HttpClient client = new DefaultHttpClient();
    private AsyncHttpClient client1;

    public Fork2FoodClient() {
        this.client = new DefaultHttpClient();
        this.client1 = new AsyncHttpClient();
    }


    public void getRecept(JsonHttpResponseHandler handler, String link){
        String link1 = "&q=" + link;
        String url = getApiUrl(link1);
        Log.d("search", url);
        client1.get(url, handler);
    }


    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

}
