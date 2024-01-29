# DapiConnect-Android
This guide will show you how to integrate DapiConnect SDK for Android and use its components to interact with the API.


# Dapi Android SDK

## Introduction

Dapi for Android is a prebuilt SDK that reduces the time it takes to integrate with Dapi's API and gain access to your users financial data.

The SDK provides direct access to Dapi endpoints and offers optional UI to manage users' accounts and money transfer.

## Integration

You can check out our [Android Integration Guide](https://docs.dapi.com/docs/android-sdk-installation) for detailed information about our SDK.

## Run The Example App

1. Download and install [Android Studio](https://developer.android.com/studio)
2. Clone the project `git clone https://github.com/dapi-co/DapiConnect-Android.git`
3. Open the project in Android Studio and go to `Settings -> Build, Execution, Deployment ->  Build Tools -> Gradle -> Gradle JDK` and select `JDK 17` if not selected.
4. Open `App.kt` and replace `APP_KEY` with your appKey. Can be obtained from [Dapi Dashboard](https://dashboard.dapi.com/login)
5. Open build.gradle and replace applicationId value `com.dapi.app` with your Bundle ID.
6. Sync gradle.
7. Run the app.
8. Click `Connect` button to select your bank and enter credentials to login through Dapi.
9. After a successful connection, a DapiConnection object is generated and used for making calls to Dapi endpoints that you can call by clicking on any of the buttons like `Get Identity` and `Create Transfer`.

