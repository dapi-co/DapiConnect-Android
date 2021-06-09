# DapiConnect-Android
This guide will show you how to integrate DapiConnect SDK for Android and use its components to interact with the API.


# Dapi Android SDK

## Overview

### Introduction

Dapi for Android is a prebuilt SDK that reduces the time it takes to integrate with Dapi's API and gain access to your users financial data.

The SDK provides direct access to Dapi endpoints and offers optional UI to manage users' accounts and money transfer.

### Requirements

- minSdkVersion 21
- App key (obtain from [Dapi Dashboard](https://dashboard.dapi.co/))

## Setup your own App

1. Update your build.gradle file to include

```kotlin
implementation "co.dapi:connect:2.6.1"
```

2. Sync the project

## Initiate SDK

1. Import Dapi in your Application class.

```kotlin
import co.dapi.connect.core.base.Dapi;
```

2. Start the SDK with your configurations in onCreate method

```kotlin
Dapi.start(
	application = this,
	appKey = "ABC",
	clientUserID = "AbdelrahmanRizq",
	configurations = DapiConfigurations(),
	onSuccess = {
	},
	onFailure = { error ->
		
	}
)
```

You can get the `appKey` from the dashboard.

The `clientUserID` is for your own reference. The best practice is to use the userID you would refer to in your system.

3. Let's now create a connection object. As previously mentioned, a connection represents a user's connection to a bank. So if they authenticate and login, through Dapi, to 2 banks there will be 2 connections. 
Since you don't know yet which bank the user will choose, you will just display the connect page. The user has to then pick the bank and enter their credentials. 

```kotlin
Dapi.presentConnect()
```

Implement OnDapiConnectListener to handle a successful or a failed connection attempt.

```kotlin
Dapi.connectListener = object : OnDapiConnectListener {

	override fun onConnectionSuccessful(connection: DapiConnection) {
             
	}

	override fun onConnectionFailure(error: DapiError, bankID: String) {
             
	}

	override fun onBankRequest(bankName: String, iban: String) {
         
	}

	override fun onDismissed() {
         
	}
}
```

That's it. You can now try to run your app on the emulator and call the `presentConnect` function and see Dapi in action!

Now lets add some functionality to your integration.

## Dapi's Functions

There are 7 main functions that you will use to interact with the SDK.

### **Create Transfer**

Let's create a transfer from an account to another account. 

This could be from the user's account to your own account as a payment.
Or from the user's account to another external account. You can do both.

So in a nutshell, you can send money `from` an account `to` an account with a specific `amount`.

**All 3 variables are optional.**
The behaviour of the SDK will differ if you decide to set or omit these varialbles.

`to`

If you are accepting a transfer into your own company's account, you don't need to set a `to`. You can simply add one in your [dashboard](https://dashboard.dapi.co/) under your app. The `to` will automatically be set to that account.

If you didn't set a beneficiary on the dashboard AND you didn't add a `to` object in the `createTransfer` function, an error will be thrown.

`from`

If you don't set a `from` account, we will simply display a popup screen for your user to pick the account from our UI. 
If you do provide a `from` object, this screen won't be displayed. 
You can create your own accounts list page from the `getAccounts` function.

`amount`

If you don't set an `amount` we will display a screen with a numpad screen for your user to enter the amount in. 

```kotlin
connection.createTransfer()
```

Now let's say you want a little more control, and would like to set the `amount` and the `from`  account yourself.

Here we will pick the first account in the connection object. Remember, a bank connection might have several accounts, so the accounts object is a list. You will need to pick which account you're sending from.

```kotlin
connection.createTransfer(
	fromAccount = connection.accounts.first(),
	amount = amount
})
```

Now let's try sending money `to` an external account.

We first need to create a new Object called `Beneficiary`. We will then need to set a few details about the bank account we're sending the money to.

```kotlin
val lineAddress = LinesAddress()
lineAddress.line1 = "baniyas road"
lineAddress.line2 = "dubai"
lineAddress.line3 = "united arab emirates"

val beneficiary = DapiBeneficiary(

address = lineAddress,
accountNumber = "0959040184901",
name = "John Doe",
bankName = "Emirates NBD Bank PJSC",
swiftCode = "EBILAEAD",
iban = "AE140260000959040184901",
country = "UNITED ARAB EMIRATES",
branchAddress = "Baniyas Road Deira PO Box 777 Dubai UAE",
branchName = "Emirates NBD Bank PJSC",
phoneNumber = "+0585859206"

)

connection.createTransfer(
	fromAccount = connection.accounts.first(),
	toBeneficiary = beneficiary,
	amount = amount
)

```

Also, you may send money to an existing beneficiary using `createTransferToExistingBeneficiary` function, but you'll need to pass `beneficiaryID` to send the money to.

```kotlin
connection.getBeneficiaries({ beneficiaries ->
    connection.createTransferToExistingBeneficiary(
        fromAccount = connection.accounts.first(),
        toBeneficiaryID = beneficiaries.beneficiaries!!.first().id,
        amount = amount,
    )
}, { error ->

})
```

Implement OnDapiTransferListener to handle a successful or a failed transfer attempt.

```kotlin
Dapi.transferListener = object : OnDapiTransferListener {    
    override fun onTransferSuccess(
        account: Accounts.DapiAccount,
        amount: Double,
        reference: String?
    ) {
      
    }

    override fun onTransferFailure(
        account: Accounts.DapiAccount?,
        error: DapiError
    ) {
        
    }
    
    override fun willTransferAmount(
        amount: Double,
        senderAccount: Accounts.DapiAccount
    ) {
        
    }

    override fun onUiDismissed() {
        
    }

}
```

### Get Accounts

Each bank `connection` will have a list of `accounts`.

```kotlin
connection.getAccounts(
	onSuccess = { accounts ->  
                      
	}, 
	onFailure = { error -> 
                            
	})
```

### Get Identity

Get the identity information that has been confirmed by the bank.

These are the identity details that you will get. Not all banks provide all this data. So we will provide as much of it as possible.

`nationality
dateOfBirth
identification (passport or national ID)
numbers
emailAddress
name
address`

```kotlin
connection.getIdentity(
	{ identity ->  
                        
	}, { error ->
                        
	})
```

### Get Transactions

We can get the list of transactions for each account.

You first have to pick an account for which you would like to access the data. You then need to provide a `from` and `to` fields for the dates. These are optional and if they aren't provided we will just fetch the transactions as far back as the bank will allow us to.

```kotlin
connection.getTransactions(
	account = connection.accounts.first(),
	fromDate = Date(),
	toDate = Date(),
	onSuccess = { transactions -> 

	},
	onFailure = { error -> 

	})
```

### Accounts Metadata

Returns the account metadata such as `swiftCode`, `branchName` and `beneficiaryCoolDownPeriod`

```kotlin
connection.getAccountsMetaData({ accountsMetaData ->
                    
}, { error ->
                    
})
```

### Get Beneficiaries

Returns the account's registered beneficiaries list.

```kotlin
connection.getBeneficiaries({ beneficiaries ->
                    
}, { error ->
                    
})
```

### Create Beneficiary

Adds a new beneficiary

```kotlin

val lineAddress = LinesAddress()
lineAddress.line1 = "baniyas road"
lineAddress.line2 = "dubai"
lineAddress.line3 = "united arab emirates"

val beneficiary = DapiBeneficiary(

address = lineAddress,
accountNumber = "0959040184901",
name = "John Doe",
bankName = "Emirates NBD Bank PJSC",
swiftCode = "EBILAEAD",
iban = "AE140260000959040184901",
country = "UNITED ARAB EMIRATES",
branchAddress = "Baniyas Road Deira PO Box 777 Dubai UAE",
branchName = "Emirates NBD Bank PJSC",
phoneNumber = "+0585859206"

)

connection.createBeneficiary(
    beneficiary = beneficiary,
    onSuccess = { createBeneficiaryResponse ->

    },
    onFailure = { error ->
                    
    }
)
```
