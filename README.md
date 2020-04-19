# DapiConnect-Android
Financial APIs to connect users' bank accounts


# Dapi Android SDK

## Overview

### Introduction

Dapi for Android is a prebuilt SDK that aims to ease and reduce the time it takes to gain access to your users financial data.

The SDK provides direct access to Dapi endpoints, and also offers additoinal UI to manage accounts, subaccounts, balance and money transfer.

### Requirement

- minSdkVersion 23.
- App key; Can be obtained from the [Dapi Dashboard](https://dashboard.dapi.co/).

## Integration

```gradle
dependencies {
    implementation 'com.dapi.connect:dapi:0.1.0'
}
```


## How it Works

Dapi SDK commincates to endpoints to make network requests. At the end, requests goes to Dapi servers. However, requests do NOT go to Dapi servers directly. Instead, requests go to your server first, and then go to Dapi servers. See the example gif below.
> *(don't worry, we also have an [SDK for your server](https://github.com/dapi-co/sdk-server))*

![dfd](https://github.com/dapi-co/DapiConnect-iOS/raw/master/DapiConnectGIF.gif)

As we can see here, the example request `sendMoney(amount:)` goes first to your server, and then goes to Dapi servers. This is a security feature that keeps control in your hands. Your server is responsible for maintaing access tokens (create, store and refresh).

## Usage

1. Initialize the SDK in your Application class

	```kotlin
	override fun onCreate() {
        super.onCreate()
        val appKey = YOUR_APP_KEY
        val dapiConfig = DapiConfig(
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

2. Only if you're not using the SDK-Server

	We provide an SDK for your server so Dapi-Android can talk to it. By default, Dapi-Android talks to the endpoints specified in [Dapi docs](https://docs.dapi.co/). 

	If your custom implementation in your server exposes different endpoint naming than the mentioned in [Dapi docs](https://docs.dapi.co/), you'll need to pass the endpoints to `DapiConfig`'s `dapiEndpoints` property.


## Components


1. Connect

	Responsible for showing list of banks, credentials input, authorization and authentication. You can receive callbacks by implementing `OnDapiConnectListener` interface.

	```kotlin
	Dapi.displayConnect()
	```

	```kotlin
	Dapi.setDapiConnectListener(object : OnDapiConnectListener{
        override fun onSuccess() {

        }

		override fun onFailure() {

		}

	})

	```

2. Payment

	You can either use autoflow in which we display the connected accounts, balance for each subaccount and a numpad to make a transaction.

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

	Or you can use our functions separately and build your own flow and UI.

	For example

	```kotlin
	Dapi.getAllDapiConnections(onSuccess = { dapiConnections ->
		//Here you get all connections and needed identifiers like connectionToken and userSecret
        }, onFailure = { dapiError ->

    })
	```

	```kotlin
	Dapi.getAccounts(connectionToken, onSuccess = {
            
        }, onFailure = {
            
    })
	```

	```kotlin
	Dapi.getIdentity(connectionToken, onSuccess = {
            
        }, onFailure = {
            
    })
	```

	>See `Dapi` for more
