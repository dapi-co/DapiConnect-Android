package com.dapi.dapiconnect.kotlin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.dapi.connect.core.base.Dapi
import co.dapi.connect.core.callbacks.OnDapiConnectListener
import co.dapi.connect.core.callbacks.OnDapiTransferListener
import co.dapi.connect.data.endpoint_models.DapiAccountsResponse
import co.dapi.connect.data.models.*
import com.dapi.dapiconnect.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), OnDapiConnectListener, OnDapiTransferListener {

    private var beneficiaryID : String? = null
    private var wireBeneficiaryID : String? = null

    //ERRORS
    companion object {
        const val NO_CONNECTED_ACCOUNTS =
            "No connected accounts. Click on CONNECT button to connect your account"
        const val DAPI_NOT_STARTED = "Dapi hasn't been started yet"
        const val RESULT_PRINTED = "Result is printed on the console"
        const val GET_ACCOUNTS_REQUIRED =
            "A successful GetAccounts call is required before doing the operation"
        const val GET_CARDS_REQUIRED =
            "A successful GetCards call is required before doing the operation"
        const val GET_BENEFICIARIES_REQUIRED =
            "A successful GetBeneficiaries call is required before doing the operation"
        const val MONTH_MILLIS = 2629800000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Dapi.connectListener = this
        Dapi.transferListener = this

        btnConnect.setOnClickListener { view: View? ->
            if (Dapi.isStarted) {
                Dapi.presentConnect()
            }
        }

        btnGetIdentity.setOnClickListener { view: View? ->
            getLastConnection {
                it.getIdentity({
                    Log.i("DapiResponse", it.toString())
                    toast(RESULT_PRINTED)
                }, {
                    toast(it.message!!)
                })
            }
        }

        btnGetAccounts.setOnClickListener { view: View? ->
            getLastConnection {
                it.getAccounts({
                    Log.i("DapiResponse", it.toString())
                    toast(RESULT_PRINTED)
                }, {
                    toast(it.message!!)
                })
            }
        }

        btnGetCards.setOnClickListener { view: View? ->
            getLastConnection {
                it.getCards({
                    Log.i("DapiResponse", it.toString())
                    toast(RESULT_PRINTED)
                }, {
                    toast(it.message!!)
                })
            }
        }


        btnGetTransactions.setOnClickListener { view: View? ->
            getLastConnection {
                if (it.accounts.isNullOrEmpty()) {
                    toast(GET_ACCOUNTS_REQUIRED)
                } else {
                    it.getTransactions(
                        it.accounts!!.first(),
                        Date(System.currentTimeMillis() - MONTH_MILLIS),
                        Date(System.currentTimeMillis()),
                        {
                            Log.i("DapiResponse", it.toString())
                            toast(RESULT_PRINTED)
                        },
                        {
                            toast(it.message!!)
                        })
                }
            }
        }

        btnGetTransactionsForCard.setOnClickListener { view: View? ->
            getLastConnection {
                if (it.cards.isNullOrEmpty()) {
                    toast(GET_CARDS_REQUIRED)
                } else {
                    it.getTransactions(
                        it.cards!!.first(),
                        Date(System.currentTimeMillis() - MONTH_MILLIS),
                        Date(System.currentTimeMillis()),
                        {
                            Log.i("DapiResponse", it.toString())
                            toast(RESULT_PRINTED)
                        },
                        {
                            toast(it.message!!)
                        })
                }
            }
        }

        btnGetAccountsMetaData.setOnClickListener { view: View? ->
            getLastConnection {
                it.getAccountsMetaData({
                    Log.i("DapiResponse", it.toString())
                    toast(RESULT_PRINTED)
                }, {
                    toast(it.message!!)
                })
            }
        }

        btnCreateTransfer.setOnClickListener { view: View? ->
            getLastConnection {
                it.createTransfer(
                    toBeneficiary = getBeneficiary()
                )
            }
        }

        btnCreateTransferToExistingBeneficiary.setOnClickListener { view: View? ->
            getLastConnection {
                when {
                    it.accounts.isNullOrEmpty() -> {
                        toast(GET_ACCOUNTS_REQUIRED)
                    }
                    beneficiaryID == null -> {
                        toast(GET_BENEFICIARIES_REQUIRED)
                    }
                    else -> {
                        it.createTransferToExistingBeneficiary(
                            it.accounts!!.first(),
                            beneficiaryID!!,
                            99.0,
                            "Test"
                        )
                    }
                }
            }
        }

        btnGetBeneficiaries.setOnClickListener { view: View? ->
            getLastConnection {
                it.getBeneficiaries({
                    Log.i("DapiResponse", it.toString())
                    toast(RESULT_PRINTED)
                    beneficiaryID = it.beneficiaries!!.first().id
                }, {
                    toast(it.message!!)
                })
            }
        }

        btnCreateBeneficiary.setOnClickListener { view: View? ->
            getLastConnection {
                it.createBeneficiary(getBeneficiary(), {
                    Log.i("DapiResponse", it.toString())
                    toast(RESULT_PRINTED)
                }, {
                    toast(it.message!!)
                })
            }
        }

        btnCreateWireTransfer.setOnClickListener { view: View? ->
            getLastConnection {
                it.createWireTransfer(
                    toBeneficiary = getWireBeneficiary()
                )
            }
        }

        btnCreateWireTransferToExistingBeneficiary.setOnClickListener { view: View? ->
            getLastConnection {
                when {
                    it.accounts.isNullOrEmpty() -> {
                        toast(GET_ACCOUNTS_REQUIRED)
                    }
                    wireBeneficiaryID == null -> {
                        toast(GET_BENEFICIARIES_REQUIRED)
                    }
                    else -> {
                        it.createWireTransferToExistingBeneficiary(
                            it.accounts!!.first(),
                            wireBeneficiaryID!!,
                            99.0,
                            "Test"
                        )
                    }
                }
            }
        }

        btnGetWireBeneficiaries.setOnClickListener { view: View? ->
            getLastConnection {
                it.getWireBeneficiaries({
                    Log.i("DapiResponse", it.toString())
                    toast(RESULT_PRINTED)
                    wireBeneficiaryID = it.beneficiaries!!.first().id
                }, {
                    toast(it.message!!)
                })
            }
        }

        btnCreateWireBeneficiary.setOnClickListener { view: View? ->
            getLastConnection {
                it.createWireBeneficiary(getWireBeneficiary(), {
                    Log.i("DapiResponse", it.toString())
                    toast(RESULT_PRINTED)
                }, {
                    toast(it.message!!)
                })
            }
        }

    }

    private fun getBeneficiary(): DapiBeneficiary {
        val lineAddress = LinesAddress()
        lineAddress.line1 = "baniyas road"
        lineAddress.line2 = "dubai"
        lineAddress.line3 = "united arab emirates"

        return DapiBeneficiary(
            address = lineAddress,
            accountNumber = "0959040184901",
            name = "John Doe",
            bankName = "Emirates NBD Bank PJSC",
            swiftCode = "EBILAEAD",
            iban = "AE140260000959040184901",
            country = "AE",
            branchAddress = "Baniyas Road Deira PO Box 777 Dubai UAE",
            branchName = "Emirates NBD Bank PJSC",
            phoneNumber = "+0585859206"
        )
    }

    private fun getWireBeneficiary(): DapiWireBeneficiary {
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

    private fun getParameters(connection: DapiConnection) {
        connection.getParameters({

        }, {

        })
    }


    private fun createConnection(parameters: String, onCreated: (DapiConnection) -> Unit) {
        DapiConnection.create(parameters, {
            onCreated(it)
        }, {
            Log.e("DapiError", it.toString())
        })
    }

    private fun getLastConnection(lastConnection: (DapiConnection) -> Unit) {
        if (Dapi.isStarted) {
            Dapi.getConnections({ connections ->
                if (connections.isNotEmpty()) {
                    lastConnection(connections.last())
                } else {
                    toast(NO_CONNECTED_ACCOUNTS)
                }
            }, {
                toast(it.message!!)
            })
        } else {
            toast(DAPI_NOT_STARTED)
        }
    }

    //Connect callbacks
    override fun onBankRequest(bankName: String, iban: String) {
        toast("bankName: $bankName, iban: $iban")
    }

    override fun onConnectionFailure(error: DapiError, bankID: String?) {
        toast(error.message!!)
    }

    override fun onConnectionSuccessful(connection: DapiConnection) {
        toast("Successful connection, name:${connection.name}")
    }

    override fun onDismissed() {
        toast("Connect is dismissed")
    }

    //Transfer callbacks
    override fun onTransferFailure(account: DapiAccountsResponse.DapiAccount?, error: DapiError) {
        toast(error.message!!)
        if (error.type == DapiError.INVALID_CONNECTION) {
            Dapi.presentConnect()
        }
    }

    override fun onTransferSuccess(
        account: DapiAccountsResponse.DapiAccount,
        amount: Double,
        reference: String?,
        operationID: String?
    ) {
        toast("Successful transfer, reference: $reference")
    }

    override fun onUiDismissed() {
        toast("Transfer UI Dismissed")
    }

    override fun willTransferAmount(
        amount: Double,
        senderAccount: DapiAccountsResponse.DapiAccount
    ) {
        toast("UI will send $amount")
    }

    private fun toast(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG
        ).show()
    }

}