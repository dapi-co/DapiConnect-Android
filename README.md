# DapiConnect-Android
Financial APIs to connect users' bank accounts


# Dapi Android SDK

## Overview

### Introduction

Dapi for Android is a prebuilt SDK that reduces the time it takes to integrate with Dapi's API and gain access to your users financial data.

The SDK provides direct access to Dapi endpoints and offers optional UI to manage users' accounts, subaccounts, balance and money transfer.

### Requirement

- minSdkVersion 21
- App key (obtain from [Dapi Dashboard](https://dashboard.dapi.co/))

## Integration

```gradle
dependencies {
    implementation 'com.dapi.connect:dapi:0.1.14'
}
```


## How it Works

DapiConnect SDK communicates with API endpoints to make network requests. Requests do NOT go to Dapi servers directly. Instead, requests first go to your server and then to Dapi servers. See the example gif below:
> *(don't worry, we also have an [SDK for your server](https://github.com/dapi-co/sdk-server))*

![dfd](https://github.com/dapi-co/DapiConnect-iOS/raw/master/DapiConnectGIF.gif)

This is a security feature that keeps control in your hands. Your server is responsible for maintaining access tokens by creating, storing, and refreshing them.

## Integration

1. Initialize the SDK in your Application class

	```kotlin
	override fun onCreate() {
        super.onCreate()
        val appKey = YOUR_APP_KEY
        val dapiConfig = DapiConfig(
	    DapiEnvironment.(PRODUCTION/SANDBOX),
	    showExperimentalBanks(true/false),
            BASE_URL,
	    listOf("AE", "EG"),  //List of supported countries, fill up the countries you want to support using two-letter country codes (ISO 3166-1 alpha-2)
            DapiEndpoints(
                endpoint1 = DapiEndpointConfig(
                PATH,
                headersMap,
                paramsMap,
                bodyMap
            ),
                endpoint2 = DapiEndpointConfig(...),
				....
            )
        )
        DapiApp.initialize(this, appKey, dapiConfig)
        DapiApp.setUserID(USER_ID)

    }
	```
	>See `DapiApp` and `DapiEndpoints` for more


	You can get your app key [from here](https://dashboard.dapi.co/)

2. If you're NOT using the SDK-Server:

	We provide an SDK for your server so Dapi-Android can talk to it. By default, Dapi-Android talks to the endpoints specified in [Dapi docs](https://docs.dapi.co/). 

	If your server's custom implementation uses different endpoint naming than those mentioned in [Dapi docs](https://docs.dapi.co/), you'll need to pass the endpoints and optional extra headers, params and body  to `DapiConfig`'s `dapiEndpoints` property.


## Components


1. DapiConnect

	Responsible for showing credential input, authorization, authentication and a list of banks. You can receive callbacks by implementing `OnDapiConnectListener` interface.

	```kotlin
	 val dapiConnect = DapiConnect()
         dapiConnect.present()
	```

	```kotlin
	dapiConnect.setOnConnectListener(object  : OnDapiConnectListener{
            override fun onSuccess(accessID: String, bankID: String) {

            }

            override fun onFailure(error: DapiError, bankID: String) {

            }

            override fun createBeneficiaryOnConnect(bankID: String): DapiBeneficiaryInfo? {
                return DapiBeneficiaryInfo(//beneficiary info)
            }
        })

	```

2. DapiAutoFlow

	You can use autoflow to display the connected accounts, balance for each subaccount and a numpad to make a transaction.

	```kotlin
	 val dapiAutoFlow = DapiAutoFlow()
         dapiAutoFlow.present()
	```

	```kotlin
	 dapiAutoFlow.setOnTransferListener(object : OnDapiTransferListener{
            override fun onTransferFailed(
                error: DapiError,
                senderAccountID: String?,
                recipientAccountID: String?
            ) {

            }

            override fun onTransferSucceeded(
                amount: Double,
                senderAccountID: String?,
                recipientAccountID: String?
            ) {

            }

            override fun onTransferCreated(
                bankID: String,
                isCreateBeneficiaryRequired: Boolean
            ): DapiBeneficiaryInfo {
                return DapiBeneficiaryInfo(//beneficiary info)
            }
        })
	```
3. DapiPayment
	You can use DapiPayment to use our functions separately and build your own flow and UI.

	For example

	```kotlin
	val dapiPayment = DapiPayment(accessID) //You can find accessID for any connected account using `getConnections()` in `DapiConnect`
        dapiPayment.getAccounts(onSuccess = {
            
        }, onFailure = {
            
        })
	```

	```kotlin
	dapiPayment.getIdentity(onSuccess = {
            
        }, onFailure = {
            
        })
	```

	>See `DapiPayment` for more
