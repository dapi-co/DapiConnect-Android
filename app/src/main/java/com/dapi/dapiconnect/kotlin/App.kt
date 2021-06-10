package com.dapi.dapiconnect.kotlin


import android.app.Application;
import android.widget.Toast
import co.dapi.connect.core.base.Dapi


/**
 * Created by Abdelrahman Rizq on 8/17/2020
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate();

        Dapi.start(this, "APP_KEY", "JohnDoe", onSuccess = {
            Toast.makeText(this, "Started", Toast.LENGTH_LONG).show()
        }, onFailure = {
            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
        })
    }
}