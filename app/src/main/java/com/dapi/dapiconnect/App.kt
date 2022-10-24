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
            "ce15a3407b6561da87bd847e27b2f530a6a84279d29d686b3daf60ca2f570cae",
            "JohnDoe",
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