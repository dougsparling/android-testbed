package com.protegra.dsparling.testbed.imgur;

import com.google.gson.Gson;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;

/**
 * Created by dsparling on 2014-12-24.
 */
public class Imgur {
    public static final ImgurApi API = new RestAdapter.Builder()
            .setEndpoint("https://api.imgur.com/3")
            .setConverter(new GsonConverter(new Gson()))
            .setLog(new AndroidLog("api"))
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .build()
            .create(ImgurApi.class);
}
