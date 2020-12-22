package com.dapi.dapiconnect.kotlin


import android.app.Application;

import com.dapi.connect.core.enums.DapiEnvironment
import com.dapi.connect.core.enums.DapiTheme
import com.dapi.connect.data.models.DapiConfigurations
import com.dapi.connect.data.models.DapiEndpoints
import com.dapi.connect.core.base.DapiClient
import java.util.HashMap;

/**
 * Created by Abdelrahman Rizq on 8/17/2020
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate();


        val appKey = "YOUR_APP_KEY";
        val  baseUrl = "BASE_URL";
        val supportedCountries = listOf("AE")

        /*
        Here are 3 HashMaps extraBody, extraHeaders, extraParams.
        These HashMaps are used to add whatever you want to the request going to your connect-server.
        For example, if you want to add your userID or a token to all requests like getIdentity, getAccounts etc.. to make
        any operation on your backend side, this is the right place to add it.
        */
        val extraBody =  HashMap<String, String>();
        val  extraHeaders =  HashMap<String, String>();
        val  extraParams =  HashMap<String, String>();

        extraBody["1"] = "1";
        extraBody["2"] = "2";
        extraBody["3"] = "3";

        extraHeaders["1"] = "1";
        extraHeaders["2"] = "2";
        extraHeaders["3"] = "3";

        extraParams["1"] = "1";
        extraParams["2"] = "2";
        extraParams["3"] = "3";


        //DapiEndpoints object is used to configure the path for any API to match your backend relative to baseUrl.
        //Default values are the ones in the docs. eg: v1/data/identity/get, v1/payment/transfer/create
        //You'll need to use this in case your backend naming doesn't match the defaults.
        val dapiEndpoints = DapiEndpoints();
        dapiEndpoints.createTransfer = "PATH"
        dapiEndpoints.getAccounts = "PATH"
        dapiEndpoints.getBalance = "PATH" //etc




        val dapiConfigurations = DapiConfigurations(
                appKey,
                baseUrl,
                DapiEnvironment.SANDBOX,
                supportedCountries,
                "CLIENT_USER_ID", //If you don't set the clientUserID here, since it's optional, you'll need to add it later using dapiClient.setClientUserID() as shown below
                false,
                DapiTheme.ELECTRIC,
                extraBody,
                extraHeaders,
                extraParams,
                dapiEndpoints,
                true
        );

        val dapiClient = DapiClient(this, dapiConfigurations);

        //Must be set before doing any operation.
        //dapiClient.setClientUserID("id");


    }
}