# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## 1.1.2 - 2021-01-28

### Fixed

- Dubai Islamic Bank exception handling

### Changed

- `DapiConnect.setOnConnectListener()` is now called `DapiConnect.setListener()`
```java
// Example Java

connect.setListener(new OnDapiConnectListener(){
    @Override
    public void onConnectionSuccessful(@NotNull DapiConnection connection) {      
    
    }

    @Override
    public void onConnectionFailure(@NotNull DapiError dapiError, @NotNull String bankID) {     

    }
       
    @Override
    public void setBeneficiaryInfoOnConnect(@NotNull String bankID, @NotNull Function1<? super DapiBeneficiaryInfo, Unit> beneficiaryInfo) {  

    }
});
```
```kotlin
// Example Kotlin

connect.listener = object : OnDapiConnectListener {
    override fun onConnectionFailure(error: DapiError, bankID: String) {
            
    }            
    
    override fun onConnectionSuccessful(connection : DapiConnection) { 

    }
    
    override fun setBeneficiaryInfoOnConnect(bankID: String, beneficiaryInfo: (DapiBeneficiaryInfo?) -> Unit) {    

    }
}
```
- `DapiAutoFlow.setOnTransferListener()` is now called `DapiAutoFlow.setTransferListener()`
```java
// Example Java

autoFlow.setTransferListener(new OnDapiTransferListener() {
    @Override
    public void onAutoFlowSuccessful(double amount, @NotNull DapiAccount account, @Nullable String receiverID, @NotNull String jobID) {    
    
    }
    
    @Override
    public void onAutoFlowFailure(@NotNull DapiError dapiError, @NotNull DapiAccount account, @Nullable String recipientAccountID) {      

    }            

    @Override
    public void setBeneficiaryInfoOnAutoFlow(@NotNull String bankID, @NotNull Function1<? super DapiBeneficiaryInfo, Unit> beneficiaryInfo) {  

    }
    
    @Override
    public void preAutoFlowTransfer(double amount, @NotNull DapiAccount accountsItem) {    

    }
});
```
```kotlin
// Example Kotlin

autoFlow.transferListener = object : OnDapiTransferListener {
    override fun onAutoFlowSuccessful(amount: Double, senderAccount: DapiAccount, recipientAccountID: String?, jobID: String) {
    
    }
    override fun onAutoFlowFailure(error: DapiError, senderAccount: DapiAccount, recipientAccountID: String?) {
    
    }
    
    override fun preAutoFlowTransfer(amount: Double, senderAccount: DapiAccount) {
    
    } 
    
    override fun setBeneficiaryInfoOnAutoFlow(bankID: String, beneficiaryInfo: (DapiBeneficiaryInfo) -> Unit) {
    
    }
}
```
