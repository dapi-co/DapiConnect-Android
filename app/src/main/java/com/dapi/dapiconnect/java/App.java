package com.dapi.dapiconnect.java;

import android.app.Application;

import co.dapi.connect.core.base.Dapi;

/**
 * Created by Abdelrahman Rizq on 8/17/2020
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Dapi.start(this, "1d4592c4a8dd6ff75261e57eb3f80c518d7857d6617769af3f8f04b0590baceb", "JohnDoe", () -> {

          return null;
        }, error -> {

            return null;
        });
    }
}
