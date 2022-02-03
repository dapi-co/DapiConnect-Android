# DapiConnect-Android
This guide will show you how to integrate DapiConnect SDK for Android and use its components to interact with the API.


# Dapi Android SDK

## Introduction

Dapi for Android is a prebuilt SDK that reduces the time it takes to integrate with Dapi's API and gain access to your users financial data.

The SDK provides direct access to Dapi endpoints and offers optional UI to manage users' accounts and money transfer.

## Integration

You can check out our [Android Integration Guide](https://dapi-api.readme.io/docs/how-to-integrate-with-android-sdk) for detailed information about our SDK.

## Run The Example App

1. Clone and open the project on Android Studio.
2. Open `App.kt` and replace `APP_KEY` with your appKey. Can be obtained from [Dapi Dashboard](https://dashboard.cf.dapi.co/)
3. Open build.gradle and replace applicationId value `com.dapi.app` with your Bundle ID.
4. Sync gradle and run the app.
5. Click `Connect` button to select your bank and enter credentials to login through Dapi.
6. After a successful connection, a DapiConnection object is generated and used for making calls to Dapi endpoints that you can call by clicking on any of the buttons like `Get Identity` and `Create Transfer`.

