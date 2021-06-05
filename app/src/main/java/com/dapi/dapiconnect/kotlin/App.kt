package com.dapi.dapiconnect.kotlin


import android.app.Application;
import co.dapi.connect.core.base.Dapi


/**
 * Created by Abdelrahman Rizq on 8/17/2020
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate();

        Dapi.start(this, "uUL4dbYtNxqltz4KOIh6R_-yI47Huu0JpgPm1G6lYSw=", "JohnDoe", onSuccess = {

        }, onFailure = {

        })
    }
}