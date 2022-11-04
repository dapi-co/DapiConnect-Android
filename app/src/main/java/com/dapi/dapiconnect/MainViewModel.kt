package com.dapi.dapiconnect

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import co.dapi.connect.core.base.Dapi
import co.dapi.connect.core.callbacks.*
import co.dapi.connect.data.models.*
import com.dapi.dapiconnect.api.data.accounts.AccountsState
import com.dapi.dapiconnect.api.data.cards.CardsState
import com.dapi.dapiconnect.api.data.identity.IdentityState
import com.dapi.dapiconnect.api.data.transactions.TransactionsState
import com.dapi.dapiconnect.api.metadata.MetadataState
import com.dapi.dapiconnect.api.payment.PaymentState
import com.dapi.dapiconnect.api.payment.beneficiaries.BeneficiariesState
import com.dapi.dapiconnect.api.wire.beneficiaries.WireBeneficiariesState
import com.dapi.dapiconnect.api.payment.create_beneficiary.CreateBeneficiaryState
import com.dapi.dapiconnect.home.HomeState
import java.util.Date

class MainViewModel : ViewModel(), App.OnDapiStarted, DapiConnectCallback, DapiTransferCallback {
    private var operatingConnection: DapiConnection? = null

    private val _homeState = mutableStateOf(HomeState())
    val homeState: State<HomeState> = _homeState

    private val _metadataState = mutableStateOf(MetadataState())
    val metadataState: State<MetadataState> = _metadataState

    private val _identityState = mutableStateOf(IdentityState())
    val identityState: State<IdentityState> = _identityState

    private val _accountsState = mutableStateOf(AccountsState())
    val accountsState: State<AccountsState> = _accountsState

    private val _cardsState = mutableStateOf(CardsState())
    val cardsState: State<CardsState> = _cardsState

    private val _transactionsState = mutableStateOf(TransactionsState())
    val transactionsState: State<TransactionsState> = _transactionsState

    private val _paymentState = mutableStateOf(PaymentState())
    val paymentState: State<PaymentState> = _paymentState

    private val _createBeneficiaryState = mutableStateOf(CreateBeneficiaryState())
    val createBeneficiaryState: State<CreateBeneficiaryState> = _createBeneficiaryState

    private val _beneficiariesState = mutableStateOf(BeneficiariesState())
    val beneficiariesState: State<BeneficiariesState> = _beneficiariesState

    private val _wireBeneficiariesState = mutableStateOf(WireBeneficiariesState())
    val wireBeneficiariesState: State<WireBeneficiariesState> = _wireBeneficiariesState

    init {
        App.onDapiStarted = this
    }

    override fun onStarted() {
        getConnections()
    }

    //Connect callbacks
    override fun onConnectionSuccessful(result: DapiConnectResult.Success) {
        getConnections()
    }

    override fun onConnectionFailure(result: DapiConnectResult.Error) {}

    override fun onDismissed() {}

    override fun onBankRequest(result: DapiConnectResult.BankRequest) {}


    //Transfer Callbacks
    override fun onTransferFailure(result: DapiTransferResult.Error) {
        _paymentState.value = PaymentState(error = result.error.message)
    }

    override fun onTransferSuccess(result: DapiTransferResult.Success) {
        _paymentState.value = PaymentState(result = result)
    }

    override fun onUiDismissed() {}

    override fun willTransferAmount(result: DapiTransferResult.PreTransfer) {}

    fun setOperatingConnection(connection: DapiConnection) {
        operatingConnection = connection
    }

    fun presentConnect() {
        Dapi.presentConnect()
        Dapi.connectCallback = this
    }

    fun getAccountsMetadata() {
        _metadataState.value = MetadataState(loading = true)
        operatingConnection?.getAccountsMetaData(
            onSuccess = {
                _metadataState.value = MetadataState(metadata = it.accountsMetadata)
            }, onFailure = {
                _metadataState.value = MetadataState(error = it.message)
            }
        )
    }

    fun getIdentity() {
        _identityState.value = IdentityState(loading = true)
        operatingConnection?.getIdentity(
            onSuccess = {
                _identityState.value = IdentityState(identity = it.identity)
            }, onFailure = {
                _identityState.value = IdentityState(error = it.message)
            }
        )
    }

    fun getAccounts() {
        _accountsState.value = AccountsState(loading = true)
        operatingConnection?.getAccounts(
            onSuccess = {
                _accountsState.value = AccountsState(accounts = it.accounts)
            }, onFailure = {
                _accountsState.value = AccountsState(error = it.message)
            }
        )
    }

    fun getCards() {
        _cardsState.value = CardsState(loading = true)
        operatingConnection?.getCards(
            onSuccess = {
                _cardsState.value = CardsState(cards = it.cards)
            }, onFailure = {
                _cardsState.value = CardsState(error = it.message)
            }
        )
    }

    fun getTransactionsForAccount(
        fromDate: Date,
        toDate: Date,
        type: DapiTransactionsType
    ) {
        operatingConnection?.presentAccountSelection(object : DapiAccountSelectionCallback {
            override fun onDismissed() {}

            override fun onFailure(result: DapiAccountSelectionResult.Error) {}

            override fun onSelected(result: DapiAccountSelectionResult.Success) {
                _transactionsState.value = TransactionsState(loading = true)
                operatingConnection?.getTransactions(result.account, fromDate, toDate, type,
                    onSuccess = {
                        _transactionsState.value = TransactionsState(transactions = it.transactions)
                    }, onFailure = {
                        _transactionsState.value = TransactionsState(error = it.message)
                    }
                )
            }
        })
    }

    fun getTransactionsForCard(
        fromDate: Date,
        toDate: Date,
        type: DapiTransactionsType
    ) {
        _transactionsState.value = TransactionsState(loading = true)
        operatingConnection?.getCards(onSuccess = {
            if (it.cards.isEmpty()) {
                _transactionsState.value =
                    TransactionsState(error = "There are no registered credit cards for this bank account")
            } else {
                operatingConnection?.getTransactions(it.cards.first(), fromDate, toDate, type,
                    onSuccess = {
                        _transactionsState.value = TransactionsState(transactions = it.transactions)
                    }, onFailure = {
                        _transactionsState.value = TransactionsState(error = it.message)
                    }
                )
            }

        }, onFailure = {
            _transactionsState.value = TransactionsState(error = it.message)
        })
    }

    fun createTransfer() {
        _paymentState.value = PaymentState(loading = true)
        operatingConnection?.createTransfer(toBeneficiary = beneficiary())
        Dapi.transferCallback = this
    }

    fun createBeneficiary(beneficiary: DapiBeneficiary) {
        _createBeneficiaryState.value = CreateBeneficiaryState(loading = true)
        operatingConnection?.createBeneficiary(
            beneficiary = beneficiary,
            onSuccess = {
                _createBeneficiaryState.value = CreateBeneficiaryState(result = it)
            }, onFailure = {
                _createBeneficiaryState.value = CreateBeneficiaryState(error = it.message)
            }
        )
    }

    fun getBeneficiaries() {
        _beneficiariesState.value = BeneficiariesState(loading = true)
        operatingConnection?.getBeneficiaries(
            onSuccess = {
                _beneficiariesState.value = BeneficiariesState(beneficiaries = it.beneficiaries)
            }, onFailure = {
                _beneficiariesState.value = BeneficiariesState(error = it.message)
            }
        )
    }

    fun createWireTransfer() {
        _paymentState.value = PaymentState(loading = true)
        operatingConnection?.createWireTransfer(toBeneficiary = wireBeneficiary())
        Dapi.transferCallback = this
    }

    fun createWireBeneficiary(beneficiary: DapiWireBeneficiary) {
        _createBeneficiaryState.value = CreateBeneficiaryState(loading = true)
        operatingConnection?.createWireBeneficiary(
            beneficiary = beneficiary,
            onSuccess = {
                _createBeneficiaryState.value = CreateBeneficiaryState(result = it)
            }, onFailure = {
                _createBeneficiaryState.value = CreateBeneficiaryState(error = it.message)
            }
        )
    }

    fun getWireBeneficiaries() {
        _wireBeneficiariesState.value = WireBeneficiariesState(loading = true)
        operatingConnection?.getWireBeneficiaries(
            onSuccess = {
                _wireBeneficiariesState.value = WireBeneficiariesState(beneficiaries = it.beneficiaries)
            }, onFailure = {
                _wireBeneficiariesState.value = WireBeneficiariesState(error = it.message)
            }
        )
    }

    enum class AppState {
        HOME,
        METADATA,
        IDENTITY,
        ACCOUNTS,
        CARDS,
        TRANSACTIONS,
        PAYMENT,
        CREATE_BENEFICIARY,
        BENEFICIARIES,
        WIRE_BENEFICIARIES,
    }

    fun clearState(state: AppState) {
        when(state) {
            AppState.HOME -> {
                _homeState.value = HomeState()
            }
            AppState.METADATA -> {
                _metadataState.value = MetadataState()
            }
            AppState.IDENTITY -> {
                _identityState.value = IdentityState()
            }
            AppState.ACCOUNTS -> {
                _accountsState.value = AccountsState()
            }
            AppState.CARDS -> {
                _cardsState.value = CardsState()
            }
            AppState.TRANSACTIONS -> {
                _transactionsState.value = TransactionsState()
            }
            AppState.PAYMENT -> {
                _paymentState.value = PaymentState()
            }
            AppState.CREATE_BENEFICIARY -> {
                _createBeneficiaryState.value = CreateBeneficiaryState()
            }
            AppState.BENEFICIARIES -> {
                _beneficiariesState.value = BeneficiariesState()
            }
            AppState.WIRE_BENEFICIARIES -> {
                _wireBeneficiariesState.value = WireBeneficiariesState()
            }
        }
    }

    private fun getConnections() {
        if (Dapi.isStarted) {
            Dapi.getConnections(
                onSuccess = { connections ->
                    _homeState.value = HomeState(connections = connections)
                },
                onFailure = { error ->
                    _homeState.value = HomeState(error = error.message)
                }
            )
        } else {
            _homeState.value = HomeState(error = "Dapi has NOT started yet.")
        }
    }

    private fun beneficiary(): DapiBeneficiary {
        val lineAddress = LinesAddress()
        lineAddress.line1 = "baniyas road"
        lineAddress.line2 = "dubai"
        lineAddress.line3 = "united arab emirates"

        return DapiBeneficiary(
            address = lineAddress,
            accountNumber = "1647518280840289401662",
            name = "John Doe",
            bankName = "Emirates NBD Bank PJSC",
            swiftCode = "EBILAEAD",
            iban = "DAPIBANKAEHSBC1647518280840289401662",
            country = "AE",
            branchAddress = "Baniyas Road Deira PO Box 777 Dubai UAE",
            branchName = "Emirates NBD Bank PJSC",
            phoneNumber = "+0123456789"
        )
    }

    private fun wireBeneficiary(): DapiWireBeneficiary {
        val address = LinesAddress()
        address.line1 = "2400 bruce street UCA stadium park bld 6"
        address.line2 = ""
        address.line3 = ""
        return DapiWireBeneficiary(
            address = address,
            accountNumber = "1234567654321",
            name = "TestAccount",
            country = "US",
            receiverType = "retail",
            routingNumber = "953349354",
            nickname = "OmarChase",
            receiverAccountType = "checking",
            firstName = "Omar",
            lastName = "Agoor",
            zipCode = "72305",
            state = "Arkansas",
            city = "Conway",
        )
    }
}