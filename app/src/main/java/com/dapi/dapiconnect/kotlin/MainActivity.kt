package com.dapi.dapiconnect.kotlin

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.dapi.connect.core.base.Dapi
import com.dapi.connect.core.callbacks.OnDapiConnectListener
import com.dapi.connect.core.callbacks.OnDapiTransferListener
import com.dapi.connect.data.endpoint_models.Accounts
import com.dapi.connect.data.models.DapiBeneficiary
import com.dapi.connect.data.models.DapiConnection
import com.dapi.connect.data.models.DapiError
import com.dapi.connect.data.models.LinesAddress
import com.dapi.dapiconnect.R
import java.util.*


class MainActivity : AppCompatActivity(), OnDapiConnectListener, OnDapiTransferListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val connectBtn = findViewById<Button>(R.id.connect)
        connectBtn.setOnClickListener { view: View? ->
            if (Dapi.isStarted){
                Dapi.presentConnect()
            }
        }
        Dapi.connectListener = this

        val payBtn = findViewById<Button>(R.id.pay)
        payBtn.setOnClickListener { view: View? ->
            Dapi.getConnections({
                if (it.isNotEmpty()){
                    it.first().createTransfer(
                        toBeneficiary = getBeneficiary()
                    )
                }
            }, {

            })
        }

        Dapi.transferListener = this
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

    private fun connectionFunctionsExamples(connection: DapiConnection){
        connection.getIdentity({

        }, {

        })

        connection.getAccounts({

        }, {

        })

        connection.getTransactions(
            account = connection.accounts.first(),
            fromDate = Date(),
            toDate = Date(), {

        }, {

        })

        connection.getAccountsMetaData({

        }, {

        })
    }

    //Connect callbacks
    override fun onConnectionFailure(error: DapiError, bankID: String) {

    }

    override fun onConnectionSuccessful(connection: DapiConnection) {

    }

    override fun onDismissed() {

    }

    //Transfer callbacks
    override fun onTransferFailure(account: Accounts.DapiAccount?, error: DapiError) {

    }

    override fun onTransferSuccess(account: Accounts.DapiAccount, amount: Int, reference: String?) {

    }

    override fun willTransferAmount(amount: Int, senderAccount: Accounts.DapiAccount) {

    }
}