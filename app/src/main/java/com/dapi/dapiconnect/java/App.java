package com.dapi.dapiconnect.java;

import android.app.Application;

import com.dapi.connect.core.enums.DapiEnvironment;
import com.dapi.connect.core.enums.DapiTheme;
import com.dapi.connect.data.models.DapiConfigurations;
import com.dapi.connect.data.models.DapiEndpoints;
import com.dapi.connect.core.base.DapiClient;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Abdelrahman Rizq on 8/17/2020
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        String appKey = "YOUR_APP_KEY";
        String baseUrl = "BASE_URL";
        ArrayList supportedCountries = new ArrayList();
        supportedCountries.add("AE");

        /*
        Here are 3 HashMaps extraBody, extraHeaders, extraParams.
        These HashMaps are used to add whatever you want to the request going to your connect-server.
        For example, if you want to add your userID or a token to all requests like getIdentity, getAccounts etc.. to make
        any operation on your backend side, this is the right place to add it.
        */
        HashMap extraBody = new HashMap<String, String>();
        HashMap extraHeaders = new HashMap<String, String>();
        HashMap extraParams = new HashMap<String, String>();

        extraBody.put("1" , "1");
        extraBody.put("2" , "2");
        extraBody.put("3" , "3");

        extraHeaders.put("1","1");
        extraHeaders.put("2","2");
        extraHeaders.put("3","3");

        extraParams.put("1", "1");
        extraParams.put("2", "2");
        extraParams.put("3", "3");


        //DapiEndpoints object is used to configure the path for any API to match your backend relative to baseUrl.
        //Default values are the ones in the docs. eg: v1/data/identity/get, v1/payment/transfer/create
        //You'll need to use this in case your backend naming doesn't match the defaults.
        DapiEndpoints dapiEndpoints = new DapiEndpoints();
        dapiEndpoints.setCreateTransfer("PATH");
        dapiEndpoints.setGetAccounts("PATH");
        dapiEndpoints.setGetBalance("PATH"); //etc




        DapiConfigurations dapiConfigurations = new DapiConfigurations(
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
                false
        );

        DapiClient dapiClient = new DapiClient(this, dapiConfigurations);

        //Must be set before doing any operation.
        //dapiClient.setClientUserID("id");


    }
}
