package com.dapi.dapiconnect.java;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import co.dapi.connect.core.base.Dapi;
import co.dapi.connect.core.callbacks.OnDapiConnectListener;
import co.dapi.connect.core.callbacks.OnDapiTransferListener;
import co.dapi.connect.data.endpoint_models.Accounts;
import co.dapi.connect.data.models.DapiBeneficiary;
import co.dapi.connect.data.models.DapiConnection;
import co.dapi.connect.data.models.DapiError;
import co.dapi.connect.data.models.LinesAddress;
import com.dapi.dapiconnect.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnDapiConnectListener, OnDapiTransferListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button connectBtn = findViewById(R.id.connect);
        connectBtn.setOnClickListener(view -> {
            if (Dapi.isStarted()) {
                Dapi.presentConnect();
            }
        });
        Dapi.setConnectListener(this);

        Button payBtn = findViewById(R.id.pay);
        payBtn.setOnClickListener(view -> {
            Dapi.getConnections(connections -> {
                if (connections.size() > 0) {
                    connections.get(0).createTransfer(null, getBeneficiary());
                }
                return null;
            }, error -> {
                return null;
            });
        });

        Dapi.setTransferListener(this);
    }

    private DapiBeneficiary getBeneficiary() {
        LinesAddress lineAddress = new LinesAddress();
        lineAddress.setLine1("baniyas road");
        lineAddress.setLine2("dubai");
        lineAddress.setLine3("united arab emirates");

        return new DapiBeneficiary(
                lineAddress,
                "0959040184901",
                "John Doe",
                "Emirates NBD Bank PJSC",
                "EBILAEAD",
                "AE140260000959040184901",
                "UNITED ARAB EMIRATES",
                "Baniyas Road Deira PO Box 777 Dubai UAE",
                "Emirates NBD Bank PJSC",
                "+0585859206"
        );
    }

    private void connectionFunctionsExamples(DapiConnection connection) {
        connection.getIdentity(identity -> {
            return null;
        }, error -> {
            return null;
        });

        connection.getAccounts(accounts -> {
            return null;
        }, error -> {
            return null;
        });

        connection.getTransactions(
                connection.getAccounts().get(0),
                new Date(),
                new Date(), transactions -> {
                    return null;
                }, error -> {
                    return null;
                });

        connection.getAccountsMetaData(accountsMetaData -> {
            return null;
        }, error -> {
            return null;
        });
    }


    //Connect callbacks
    @Override
    public void onConnectionSuccessful(@NotNull DapiConnection dapiConnection) {

    }

    @Override
    public void onConnectionFailure(@NotNull DapiError dapiError, @NotNull String bankID) {

    }

    @Override
    public void onDismissed() {

    }

    @Override
    public void onBankRequest(@NotNull String s, @NotNull String s1) {

    }

    //Transfer callbacks
    @Override
    public void onTransferFailure(@Nullable Accounts.DapiAccount dapiAccount, @NotNull DapiError dapiError) {

    }

    @Override
    public void onTransferSuccess(@NotNull Accounts.DapiAccount dapiAccount, double v, @Nullable String s) {

    }

    @Override
    public void onUiDismissed() {

    }

    @Override
    public void willTransferAmount(double v, @NotNull Accounts.DapiAccount dapiAccount) {

    }

}