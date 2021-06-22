package com.dapi.dapiconnect.kotlin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.dapi.connect.core.base.Dapi
import co.dapi.connect.core.callbacks.OnDapiConnectListener
import co.dapi.connect.core.callbacks.OnDapiTransferListener
import co.dapi.connect.data.endpoint_models.DapiAccountsResponse
import co.dapi.connect.data.models.DapiBeneficiary
import co.dapi.connect.data.models.DapiConnection
import co.dapi.connect.data.models.DapiError
import co.dapi.connect.data.models.LinesAddress
import com.dapi.dapiconnect.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), OnDapiConnectListener, OnDapiTransferListener {

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
        const val MONTH_MILLIS = 2629800000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnConnect = findViewById<Button>(R.id.btnConnect)
        val btnCreateTransfer = findViewById<Button>(R.id.btnCreateTransfer)
        val btnGetAccountsMetaData = findViewById<Button>(R.id.btnGetAccountsMetaData)
        val btnGetAccounts = findViewById<Button>(R.id.btnGetAccounts)
        val btnGetTransactions = findViewById<Button>(R.id.btnGetTransactions)
        val btnGetIdentity = findViewById<Button>(R.id.btnGetIdentity)
        val btnGetBeneficiaries = findViewById<Button>(R.id.btnGetBeneficiaries)
        val btnCreateBeneficiary = findViewById<Button>(R.id.btnCreateBeneficiary)


        Dapi.connectListener = this
        Dapi.transferListener = this

        btnConnect.setOnClickListener { view: View? ->
            if (Dapi.isStarted) {
                Dapi.presentConnect()
            }
        }

        btnCreateTransfer.setOnClickListener { view: View? ->
            getFirstConnection {
                it.createTransfer(
                    toBeneficiary = getBeneficiary()
                )
            }
        }

        btnGetAccountsMetaData.setOnClickListener { view: View? ->
            getFirstConnection {
                it.getAccountsMetaData({
                    Log.i("DapiResponse", it.toString())
                    toast(RESULT_PRINTED)
                }, {
                    toast(it.message!!)
                })
            }
        }

        btnGetAccounts.setOnClickListener { view: View? ->
            getFirstConnection {
                it.getAccounts({
                    Log.i("DapiResponse", it.toString())
                    toast(RESULT_PRINTED)
                }, {
                    toast(it.message!!)
                })
            }
        }

        btnGetTransactions.setOnClickListener { view: View? ->
            getFirstConnection {
                if (it.accounts.isNullOrEmpty()) {
                    toast(GET_ACCOUNTS_REQUIRED)
                } else {
                    it.getTransactions(it.accounts!!.first(), Date(System.currentTimeMillis() - MONTH_MILLIS), Date(System.currentTimeMillis()), {
                        Log.i("DapiResponse", it.toString())
                        toast(RESULT_PRINTED)
                    }, {
                        toast(it.message!!)
                    })
                }
            }
        }

        btnGetIdentity.setOnClickListener { view: View? ->
            getFirstConnection {
                it.getIdentity({
                    Log.i("DapiResponse", it.toString())
                    toast(RESULT_PRINTED)
                }, {
                    toast(it.message!!)
                })
            }
        }

        btnGetBeneficiaries.setOnClickListener { view: View? ->
            getFirstConnection {
                it.getBeneficiaries({
                    Log.i("DapiResponse", it.toString())
                    toast(RESULT_PRINTED)
                }, {
                    toast(it.message!!)
                })
            }
        }

        btnCreateBeneficiary.setOnClickListener { view: View? ->
            getFirstConnection {
                it.createBeneficiary(getBeneficiary(), {
                    Log.i("DapiResponse", it.toString())
                    toast(RESULT_PRINTED)
                }, {
                    toast(it.message!!)
                })
            }
        }

        btnGetCards.setOnClickListener { view: View? ->
            getFirstConnection {
                it.getCards({
                    Log.i("DapiResponse", it.toString())
                    toast(RESULT_PRINTED)
                }, {
                    toast(it.message!!)
                })
            }
        }

        btnGetTransactionsForCard.setOnClickListener { view: View? ->
            getFirstConnection {
                if (it.cards.isNullOrEmpty()) {
                    toast(GET_CARDS_REQUIRED)
                } else {
                    it.getTransactions(it.cards!!.first(), Date(System.currentTimeMillis() - MONTH_MILLIS), Date(System.currentTimeMillis()), {
                        Log.i("DapiResponse", it.toString())
                        toast(RESULT_PRINTED)
                    }, {
                        toast(it.message!!)
                    })
                }
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
            country = "UNITED ARAB EMIRATES",
            branchAddress = "Baniyas Road Deira PO Box 777 Dubai UAE",
            branchName = "Emirates NBD Bank PJSC",
            phoneNumber = "+0585859206"
        )

    }

    private fun getParameters(connection: DapiConnection): String =
        connection.getParameters()

    private fun createConnection(parameters: String, onCreated: (DapiConnection) -> Unit) {
        DapiConnection.create(parameters, {
            onCreated(it)
        }, {
            Log.e("DapiError", it.toString())
        })
    }

    private fun getFirstConnection(firstConnection: (DapiConnection) -> Unit) {
        if (Dapi.isStarted) {
            Dapi.getConnections({ connections ->
                if (connections.isNotEmpty()) {
                    firstConnection(connections.first())
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

    override fun onConnectionFailure(error: DapiError, bankID: String) {
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
    }

    override fun onTransferSuccess(
        account: DapiAccountsResponse.DapiAccount,
        amount: Double,
        reference: String?
    ) {
        toast("Successful transfer, reference: $reference")
    }

    override fun onUiDismissed() {
        toast("Transfer UI Dismissed")
    }

    override fun willTransferAmount(amount: Double, senderAccount: DapiAccountsResponse.DapiAccount) {
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