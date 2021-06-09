package com.dapi.dapiconnect.java;

import android.app.Application;
import android.widget.Toast;

import co.dapi.connect.core.base.Dapi;

/**
 * Created by Abdelrahman Rizq on 8/17/2020
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Dapi.start(this, "1d4592c4a8dd6ff75261e57eb3f80c518d7857d6617769af3f8f04b0590baceb", "JohnDoe", () -> {
            Toast.makeText(this, "Started", Toast.LENGTH_LONG).show();
          return null;
        }, error -> {
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        });
    }
}
