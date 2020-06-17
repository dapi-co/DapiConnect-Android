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
    implementation 'com.dapi.connect:dapi:0.1.12'
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
	    showExperimentalBanks(true/false)
            BASE_URL,
            DapiEndpoints(
                endpoint1 = "",
                endpoint2 = "",
				....
            )
        )
        Dapi.initialize(this, appKey, dapiConfig)
        Dapi.setUserID(USER_ID)

    }
	```
	>See `DapiEndpoints` for more


	You can get your app key [from here](https://dashboard.dapi.co/)

2. If you're NOT using the SDK-Server:

	We provide an SDK for your server so Dapi-Android can talk to it. By default, Dapi-Android talks to the endpoints specified in [Dapi docs](https://docs.dapi.co/). 

	If your server's custom implementation uses different endpoint naming than those mentioned in [Dapi docs](https://docs.dapi.co/), you'll need to pass the endpoints to `DapiConfig`'s `dapiEndpoints` property.


## Components


1. Connect

	Responsible for showing credential input, authorization, authentication and a list of banks. You can receive callbacks by implementing `OnDapiConnectListener` interface.

	```kotlin
	Dapi.displayConnect()
	```

	```kotlin
	Dapi.setOnDapiConnectListener(object : OnDapiConnectListener{
        	override fun onSuccess() {

        	}

		override fun onFailure() {

		}

	})

	```

2. Payment

	You can use autoflow to display the connected accounts, balance for each subaccount and a numpad to make a transaction.

	```kotlin
	Dapi.displayPayment()
	```

	```kotlin
	Dapi.setDapiTransferListener(object : OnDapiTransferListener{
		override fun onTransferCreated(
			bankID: String,
			isCreateBeneficiaryRequired: Boolean
		): DapiBeneficiaryInfo {

			return DapiBeneficiaryInfo(
				//beneficiary info
			)
		}
	})
	```

	OR you can use our functions separately and build your own flow and UI.

	For example

	```kotlin
	Dapi.getAllDapiConnections(onSuccess = { dapiConnections ->
		//Here you get all connections and needed identifiers like accessID
        }, onFailure = { dapiError ->

    })
	```

	```kotlin
	Dapi.getAccounts(accessID, onSuccess = {
            
        }, onFailure = {
            
    })
	```

	```kotlin
	Dapi.getIdentity(accessID, onSuccess = {
            
        }, onFailure = {
            
    })
	```

	>See `Dapi` for more
