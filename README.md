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
    implementation 'com.dapi.connect:dapi:0.1.27'
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
	
	val dapiConfigurations = DapiConfigurations(
            appKey, //Your app key
            baseUrl, 
            environment, //DapiEnvironment.SANDBOX or DapiEnvironment.PRODUCTION
            supportedCountriesCodes, //List of supported countries, fill up the countries you want to support using two-letter country codes (ISO 3166-1 alpha-2)
            clientUserID, //your user ID, used to destinguish between different users on the same device
            userID, //you can obtain userID using dapiApp.connect.getConnections
            isExperimental, //for showing experimental banks.
            theme, //DapiTheme.GENERAL or DapiTheme.ELEGANT or DapiTheme.ELECTRIC
            extraHeaders, //Headers to add to all requests
            extraParams, //Params to add to all requests
            extraBody, //Body to add to all requests
	    	dapiEndPoints //DapiEndpoints settings object for different endpoints
        )
	
       	val dapiApp = DapiApp(this, dapiConfigurations)
	val dapiApp2 = ...

    }
	```
	>See `DapiApp` for more


	You can get your app key [from here](https://dashboard.dapi.co/)

2. If you're NOT using the SDK-Server:

	We provide an SDK for your server so Dapi-Android can talk to it. By default, Dapi-Android talks to the endpoints specified in [Dapi docs](https://docs.dapi.co/). 

	If your server's custom implementation uses different endpoint naming than those mentioned in [Dapi docs](https://docs.dapi.co/), you'll need to pass the endpoints and optional extra headers, params and body  to `DapiConfig`'s `dapiEndpoints` property.


## Components


1. DapiConnect

	Responsible for showing credential input, authorization, authentication and a list of banks. You can receive callbacks by implementing `OnDapiConnectListener` interface.

	```kotlin
	val dapiApp = DapiApp.getInstance() //or val dapiApps = DapiApp.getInstances() to get all instances
	dapiApp.connect.present()
	```

	```kotlin
	dapiApp.connect.setOnConnectListener(object : OnDapiConnectListener {
            override fun onSuccess(userID: String, bankID: String) {

            }

            override fun onFailure(error: DapiError, bankID: String) {

            }

            override fun onProceed(userID: String, bankID: String) {
	    
            }
	
            override fun createBeneficiaryOnConnect(bankID: String): DapiBeneficiaryInfo? {
                val info = DapiBeneficiaryInfo()
                val lineAddress = LinesAddress()
                lineAddress.line1 = "line1"
                lineAddress.line2 = "line2"
                lineAddress.line3 = "line3"
                info.linesAddress = lineAddress
                info.accountNumber = "xxxxxxxxx"
                info.bankName = "xxxx"
                info.swiftCode = "xxxxx"
                info.iban = "xxxxxxxxxxxxxxxxxxxxxxxxx"
                info.country = "UNITED ARAB EMIRATES"
                info.branchAddress = "branchAddress"
                info.branchName = "branchName"
                info.phoneNumber = "xxxxxxxxxxx"
                info.name = "xxxxx"
                return info
            }

        })

	```
	
	```kotlin
	dapiApp.connect.getConnections(onSuccess = { 
               
        }, onFailure =  {
                
        })
	 ```

2. DapiAutoFlow

	You can use autoflow to display the connected accounts, balance for each subaccount and a numpad to make a transaction.

	```kotlin
	 dapiApp.autoFlow.present()
	```

	```kotlin
	dapiApp.autoFlow.setOnTransferListener(object : OnDapiTransferListener {
            override fun onTransferSucceeded(
                amount: Double,
                senderAccountID: String?,
                recipientAccountID: String?
            ) {
                
            }

            override fun onTransferFailed(
                error: DapiError,
                senderAccountID: String?,
                recipientAccountID: String?
            ) {
                
            }

            override fun onTransferCreated(
                bankID: String,
                isCreateBeneficiaryRequired: Boolean
            ): DapiBeneficiaryInfo {
                 val info = DapiBeneficiaryInfo()
                val lineAddress = LinesAddress()
                lineAddress.line1 = "line1"
                lineAddress.line2 = "line2"
                lineAddress.line3 = "line3"
                info.linesAddress = lineAddress
                info.accountNumber = "xxxxxxxxx"
                info.bankName = "xxxx"
                info.swiftCode = "xxxxx"
                info.iban = "xxxxxxxxxxxxxxxxxxxxxxxxx"
                info.country = "UNITED ARAB EMIRATES"
                info.branchAddress = "branchAddress"
                info.branchName = "branchName"
                info.phoneNumber = "xxxxxxxxxxx"
                info.name = "xxxxx"
                return info
            }

        })
	```
3. Data, Payment, Metadata, User
	You can use these to use our functions separately and build your own flow and UI.

	For example

	```kotlin
		dapiApp.data.getIdentity(onSuccess = {
                    
                }, onFailure =  {

                })
		
		dapiApp.data.getAccounts(onSuccess = {
                        
                }, onFailure =  {

                })
		
		dapiApp.data.getBalance(accountID, onSuccess =  {
		
                }, onFailure =  {
			
                })
		
		dapiApp.data.getTransactions(accountID, fromDate, toDate, onSuccess = {
                    
                } , onFailure =  {
                    
                })
		
		dapiApp.payment.createBeneficiary(DapiBeneficiaryInfo(), onSuccess = {
                    
                }, onFailure =  {
                    
                })
		
         	dapiApp.payment.createTransfer(receiverID, senderID, amount, onSuccess = {
                    
                }, onFailure =  {
                    
                })
		
         	dapiApp.payment.createTransfer(iban, name, senderID, amount, onSuccess = {

                }, onFailure =  {

                })
		
                dapiApp.metadata.getAccountMetaData(onSuccess = {

                }, onFailure =  {
		
                })
		
		dapiApp.user.delink(onSuccess = {
                    
                }, onFailure =  {
                    
                })
	```
