package com.dapi.dapiconnect.java;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import co.dapi.connect.core.base.Dapi;
import co.dapi.connect.core.callbacks.OnDapiConnectListener;
import co.dapi.connect.core.callbacks.OnDapiTransferListener;
import co.dapi.connect.data.endpoint_models.DapiAccountsResponse;
import co.dapi.connect.data.models.DapiBeneficiary;
import co.dapi.connect.data.models.DapiConnection;
import co.dapi.connect.data.models.DapiError;
import co.dapi.connect.data.models.LinesAddress;

import com.dapi.dapiconnect.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

import static com.dapi.dapiconnect.kotlin.MainActivity.DAPI_NOT_STARTED;
import static com.dapi.dapiconnect.kotlin.MainActivity.GET_ACCOUNTS_REQUIRED;
import static com.dapi.dapiconnect.kotlin.MainActivity.MONTH_MILLIS;
import static com.dapi.dapiconnect.kotlin.MainActivity.RESULT_PRINTED;

public class MainActivity extends AppCompatActivity implements OnDapiConnectListener, OnDapiTransferListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnConnect = findViewById(R.id.btnConnect);
        Button btnCreateTransfer = findViewById(R.id.btnCreateTransfer);
        Button btnGetAccountsMetaData = findViewById(R.id.btnGetAccountsMetaData);
        Button btnGetAccounts = findViewById(R.id.btnGetAccounts);
        Button btnGetTransactions = findViewById(R.id.btnGetTransactions);
        Button btnGetIdentity = findViewById(R.id.btnGetIdentity);
        Button btnGetBeneficiaries = findViewById(R.id.btnGetBeneficiaries);
        Button btnCreateBeneficiary = findViewById(R.id.btnCreateBeneficiary);


        Dapi.setConnectListener(this);
        Dapi.setTransferListener(this);

        btnConnect.setOnClickListener((view) -> {
            if (Dapi.isStarted()) {
                Dapi.presentConnect();
            }
        });

        btnCreateTransfer.setOnClickListener((view) -> {
            getFirstConnection((connection -> {
                connection.createTransfer(null, getBeneficiary());
            }));
        });

        btnGetAccountsMetaData.setOnClickListener((view) -> {
            getFirstConnection((connection) -> {
                connection.getAccountsMetaData((accountsMetaData) -> {
                    Log.i("DapiResponse", accountsMetaData.toString());
                    toast(RESULT_PRINTED);
                    return null;
                }, (error) -> {
                    toast(error.getMessage());
                    return null;
                });
            });
        });

        btnGetAccounts.setOnClickListener((view) -> {
            getFirstConnection((connection) -> {
                connection.getAccounts((accounts) -> {
                    Log.i("DapiResponse", accounts.toString());
                    toast(RESULT_PRINTED);
                    return null;
                }, (error) -> {
                    toast(error.getMessage());
                    return null;
                });
            });
        });

        btnGetTransactions.setOnClickListener((view) -> {
            getFirstConnection((connection) -> {
                if (connection.getAccounts() == null || connection.getAccounts().isEmpty()) {
                    toast(GET_ACCOUNTS_REQUIRED);
                } else {
                    connection.getTransactions(connection.getAccounts().get(0), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() - MONTH_MILLIS), (transactions) -> {
                        Log.i("DapiResponse", transactions.toString());
                        toast(RESULT_PRINTED);
                        return null;
                    }, (error) -> {
                        toast(error.getMessage());
                        return null;
                    });
                }

            });
        });

        btnGetIdentity.setOnClickListener((view) -> {
            getFirstConnection((connection) -> {
                connection.getIdentity((identity) -> {
                    Log.i("DapiResponse", identity.toString());
                    toast(RESULT_PRINTED);
                    return null;
                }, (error) -> {
                    toast(error.getMessage());
                    return null;
                });
            });
        });

        btnGetBeneficiaries.setOnClickListener((view) -> {
            getFirstConnection((connection) -> {
                connection.getBeneficiaries((beneficiaries) -> {
                    Log.i("DapiResponse", beneficiaries.toString());
                    toast(RESULT_PRINTED);
                    return null;
                }, (error) -> {
                    toast(error.getMessage());
                    return null;
                });
            });
        });

        btnCreateBeneficiary.setOnClickListener((view) -> {
            getFirstConnection((connection) -> {
                connection.createBeneficiary(getBeneficiary(), (createBeneficiary) -> {
                    Log.i("DapiResponse", createBeneficiary.toString());
                    toast(RESULT_PRINTED);
                    return null;
                }, (error) -> {
                    toast(error.getMessage());
                    return null;
                });
            });
        });

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

    private void getFirstConnection(ConnectionCallback connectionCallback) {
        if (Dapi.isStarted()) {
            Dapi.getConnections((connections) -> {
                connectionCallback.call(connections.get(0));
                return null;
            }, (error) -> {
                toast(error.getMessage());
                return null;
            });
        } else {
            toast(DAPI_NOT_STARTED);
        }
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
    public void onTransferFailure(@Nullable DapiAccountsResponse.DapiAccount dapiAccount, @NotNull DapiError dapiError) {

    }

    @Override
    public void onTransferSuccess(@NotNull DapiAccountsResponse.DapiAccount dapiAccount, double v, @Nullable String s) {

    }

    @Override
    public void onUiDismissed() {

    }

    @Override
    public void willTransferAmount(double v, @NotNull DapiAccountsResponse.DapiAccount dapiAccount) {

    }

    private void toast(String message) {
        Toast.makeText(
                this,
                message,
                Toast.LENGTH_LONG
        ).show();
    }

    private interface ConnectionCallback {
        void call(DapiConnection connection);
    }

}