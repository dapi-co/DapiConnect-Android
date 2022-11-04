package com.dapi.dapiconnect


import android.app.Application;
import android.widget.Toast
import co.dapi.connect.core.base.Dapi
import co.dapi.connect.data.models.DapiConfigurations
import co.dapi.connect.data.models.DapiEnvironment


/**
 * Created by Abdelrahman Rizq on 8/17/2020
 */
class App : Application() {
    interface OnDapiStarted {
        fun onStarted()
    }

    companion object {
        var onDapiStarted: OnDapiStarted? = null
    }

    override fun onCreate() {
        super.onCreate();
        Dapi.start(this,
            "APP_KEY",
            "CLIENT_USER_ID",
            configurations = DapiConfigurations(environment = DapiEnvironment.SANDBOX),
            onSuccess = {
                onDapiStarted?.onStarted()
            },
            onFailure = {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
        )
    }
}