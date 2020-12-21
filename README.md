# DapiConnect-Android	
This guide will show you how to integrate DapiConnect SDK for Android and use its components to interact with the API.


# Dapi Android SDK

## Overview

### Introduction

Dapi for Android is a prebuilt SDK that reduces the time it takes to integrate with Dapi's API and gain access to your users financial data.

The SDK provides direct access to Dapi endpoints and offers optional UI to manage users' accounts, subaccounts, balance and money transfer.

### Requirements

- minSdkVersion 21
- App key (obtain from [Dapi Dashboard](https://dashboard.dapi.co/))

## Integration

```gradle
dependencies {
    implementation 'com.dapi.connect:dapi:1.0.0'
}
```


## How it Works

DapiConnect SDK communicates with API endpoints to make network requests. Requests do NOT go to Dapi servers directly. Instead, requests first go to your server and then to Dapi servers. See the example gif below:
> *(don't worry, we also have an [SDK for your server](https://github.com/dapi-co/sdk-server))*

![dfd](https://files.readme.io/977c042-Comp_1_8.gif)

### Why?
This is a security feature that keeps control in your hands. Your server is responsible for maintaining access tokens by creating, storing, and refreshing them.

## Usage

1. Initialize the SDK in your Application class

	```kotlin
	override fun onCreate() {
        super.onCreate()
	
	val dapiConfigurations = DapiConfigurations(
            appKey, //Your app key
            baseUrl, 
            environment, //DapiEnvironment.SANDBOX or DapiEnvironment.PRODUCTION
            supportedCountriesCodes, //List of supported countries, fill up the countries you want to support using two-letter country codes (ISO 3166-1 alpha-2)
	    clientUserID, //OPTIONAL. your user ID, used to destinguish between different users on the same device
            userID, //OPTIONAL. you can obtain userID using dapiApp.connect.getConnections. Initially it will be null, but you can use this as the default userID afterwards.
            isExperimental, //OPTIONAL. for showing experimental banks.
            theme, //OPTIONAL. DapiTheme.GENERAL or DapiTheme.ELEGANT or DapiTheme.ELECTRIC
            extraHeaders, //OPTIONAL. Headers to add to all requests
            extraParams, //OPTIONAL. Params to add to all requests
            extraBody, //OPTIONAL. Body to add to all requests
	    dapiEndPoints, //OPTIONAL. DapiEndpoints settings object for different endpoints
		autoTruncate //OPTIONAl. to auto truncate beneficiary and transfer info 
	)
	val dapiClient = DapiClient(this, dapiConfigurations)
	val dapiClient2 = ...

    }
	```
	>See `DapiClient` for more


	You can get your app key [from here](https://dashboard.dapi.co/)

2. If you're NOT using the SDK-Server:

	We provide an SDK for your server so Dapi-Android can talk to it. By default, Dapi-Android talks to the endpoints specified in [Dapi docs](https://docs.dapi.co/). 

## Components


1. DapiConnect

	Responsible for showing credential input, authorization, authentication and a list of banks. You can receive callbacks by implementing `OnDapiConnectListener` interface.

	```kotlin
	val dapiClient = DapiClient.getInstance() //or val dapiClients = DapiClient.getInstances() to get all instances
	dapiClient.connect.present()
	```

	```kotlin
	dapiClient.connect.setOnConnectListener(object : OnDapiConnectListener {
            override fun onConnectionSuccessful(userID: String, bankID: String) {

            }

            override fun onConnectionFailure(error: DapiError, bankID: String) {

            }

            override fun onProceed(userID: String, bankID: String) {
	    
            }
	
            override fun setBeneficiaryInfoOnConnect(bankID: String): DapiBeneficiaryInfo? {
                val lineAddress = LinesAddress()
        	lineAddress.line1 = "line1"
        	lineAddress.line2 = "line2"
        	lineAddress.line3 = "line3"
        	val info = DapiBeneficiaryInfo(
            	linesAddress = lineAddress,
            	accountNumber = "xxxxxxxxx",
            	name = "xxxxx",
            	bankName = "xxxx",
            	swiftCode = "xxxxx",
            	iban = "xxxxxxxxxxxxxxxxxxxxxxxxx",
            	country = "UNITED ARAB EMIRATES",
            	branchAddress = "branchAddress",
            	branchName = "branchName",
            	phoneNumber = "xxxxxxxxxxx"
        	)
                return info
            }

        })

	```
	
	```kotlin
	dapiClient.connect.getConnections(onSuccess = { 
               
        }, onFailure =  {
                
        })
	 ```

2. DapiAutoFlow

	You can use autoflow to display the connected accounts, balance for each subaccount and a numpad to make a transaction.

	```kotlin
	 dapiClient.autoFlow.present()
	```

	```kotlin
	dapiClient.autoFlow.setOnTransferListener(object : OnDapiTransferListener {
            override fun onAutoFlowSuccessful(
                amount: Double,
                senderAccountID: String?,
                recipientAccountID: String?,
                jobID: String
            ) {
                
            }

            override fun onAutoFlowFailure(
                error: DapiError,
                senderAccountID: String?,
                recipientAccountID: String?
            ) {
                
            }

            override fun setBeneficiaryInfoOnAutoFlow(
                bankID: String
            ): DapiBeneficiaryInfo {
                 val lineAddress = LinesAddress()
        	lineAddress.line1 = "line1"
        	lineAddress.line2 = "line2"
        	lineAddress.line3 = "line3"
        	val info = DapiBeneficiaryInfo(
            	linesAddress = lineAddress,
            	accountNumber = "xxxxxxxxx",
            	name = "xxxxx",
            	bankName = "xxxx",
            	swiftCode = "xxxxx",
            	iban = "xxxxxxxxxxxxxxxxxxxxxxxxx",
            	country = "UNITED ARAB EMIRATES",
            	branchAddress = "branchAddress",
            	branchName = "branchName",
            	phoneNumber = "xxxxxxxxxxx"
        	)
                return info
            }
	    
	    //Optional
            override fun onPaymentStarted(bankID: String) {
                super.onPaymentStarted(bankID)
                
            }

        })
	```
3. Data, Payment, Metadata, Auth

	You can use these to use our functions separately and build your own flow and UI.

	```kotlin
		dapiClient.data.getIdentity(onSuccess = {
                    
                }, onFailure =  {

                })
		
		dapiClient.data.getAccounts(onSuccess = {
                        
                }, onFailure =  {

                })
		
		dapiClient.data.getBalance(accountID, onSuccess =  {
		
                }, onFailure =  {
			
                })
		
		dapiClient.data.getTransactions(accountID, fromDate, toDate, onSuccess = {
                    
                } , onFailure =  {
                    
                })
		
		dapiClient.payment.createBeneficiary(DapiBeneficiaryInfo(), onSuccess = {
                    
                }, onFailure =  {
                    
                })
		
         	dapiClient.payment.createTransfer(receiverID, senderID, amount, remark,onSuccess = {
                    
                }, onFailure =  {
                    
                })
		
         	dapiClient.payment.createTransfer(iban, name, senderID, amount, remark,onSuccess = {

                }, onFailure =  {

                })
		
		dapiClient.payment.createTransfer(accountNumber, name, amount, senderID, remark,onSuccess = {

                }, onFailure =  {

                })
		
                dapiClient.metadata.getAccountMetaData(onSuccess = {

                }, onFailure =  {
		
                })
		
		dapiClient.auth.delink(onSuccess = {
                    
                }, onFailure =  {
                    
                })
	```
	
Finally, you should release the SDK when your app closes using

```kotlin
dapiClient.release()
```
