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
import co.dapi.connect.data.models.DapiWireBeneficiary;
import co.dapi.connect.data.models.LinesAddress;

import com.dapi.dapiconnect.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

import static com.dapi.dapiconnect.kotlin.MainActivity.DAPI_NOT_STARTED;
import static com.dapi.dapiconnect.kotlin.MainActivity.GET_ACCOUNTS_REQUIRED;
import static com.dapi.dapiconnect.kotlin.MainActivity.GET_BENEFICIARIES_REQUIRED;
import static com.dapi.dapiconnect.kotlin.MainActivity.GET_CARDS_REQUIRED;
import static com.dapi.dapiconnect.kotlin.MainActivity.MONTH_MILLIS;
import static com.dapi.dapiconnect.kotlin.MainActivity.RESULT_PRINTED;

public class MainActivity extends AppCompatActivity implements OnDapiConnectListener, OnDapiTransferListener {

    private String beneficiaryID;
    private String wireBeneficiaryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnConnect = findViewById(R.id.btnConnect);
        Button btnGetIdentity = findViewById(R.id.btnGetIdentity);
        Button btnGetAccounts = findViewById(R.id.btnGetAccounts);
        Button btnGetCards = findViewById(R.id.btnGetCards);
        Button btnGetTransactions = findViewById(R.id.btnGetTransactions);
        Button btnGetTransactionsForCard = findViewById(R.id.btnGetTransactionsForCard);
        Button btnGetAccountsMetaData = findViewById(R.id.btnGetAccountsMetaData);
        Button btnCreateTransfer = findViewById(R.id.btnCreateTransfer);
        Button btnCreateTransferToExistingBeneficiary = findViewById(R.id.btnCreateTransferToExistingBeneficiary);
        Button btnGetBeneficiaries = findViewById(R.id.btnGetBeneficiaries);
        Button btnCreateBeneficiary = findViewById(R.id.btnCreateBeneficiary);
        Button btnCreateWireTransfer = findViewById(R.id.btnCreateWireTransfer);
        Button btnCreateWireTransferToExistingBeneficiary = findViewById(R.id.btnCreateWireTransferToExistingBeneficiary);
        Button btnGetWireBeneficiaries = findViewById(R.id.btnGetWireBeneficiaries);
        Button btnCreateWireBeneficiary = findViewById(R.id.btnCreateWireBeneficiary);


        Dapi.setConnectListener(this);
        Dapi.setTransferListener(this);

        btnConnect.setOnClickListener((view) -> {
            if (Dapi.isStarted()) {
                Dapi.presentConnect();
            }
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

        btnGetCards.setOnClickListener((view) -> {
            getFirstConnection((connection) -> {
                connection.getCards((cards) -> {
                    Log.i("DapiResponse", cards.toString());
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

        btnGetTransactionsForCard.setOnClickListener((view) -> {
            getFirstConnection((connection) -> {
                if (connection.getCards() == null || connection.getCards().isEmpty()) {
                    toast(GET_CARDS_REQUIRED);
                } else {
                    connection.getTransactions(connection.getCards().get(0), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() - MONTH_MILLIS), (transactions) -> {
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

        btnCreateTransfer.setOnClickListener((view) -> {
            getFirstConnection((connection -> {
                connection.createTransfer(null, getBeneficiary());
            }));
        });

        btnCreateTransferToExistingBeneficiary.setOnClickListener((view) -> {
            getFirstConnection((connection -> {
                if (connection.getAccounts() == null || connection.getAccounts().isEmpty()) {
                    toast(GET_ACCOUNTS_REQUIRED);
                } else if (beneficiaryID == null) {
                    toast(GET_BENEFICIARIES_REQUIRED);
                } else {
                    connection.createTransferToExistingBeneficiary(
                            connection.getAccounts().get(0),
                            beneficiaryID,
                            99.0,
                            "Test"
                    );
                }
            }));
        });

        btnGetBeneficiaries.setOnClickListener((view) -> {
            getFirstConnection((connection) -> {
                connection.getBeneficiaries((response) -> {
                    Log.i("DapiResponse", response.toString());
                    toast(RESULT_PRINTED);
                    beneficiaryID = response.getBeneficiaries().get(0).getId();
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

        btnCreateWireTransfer.setOnClickListener((view) -> {
            getFirstConnection((connection -> {
                connection.createWireTransfer(getWireBeneficiary());
            }));
        });

        btnCreateWireTransferToExistingBeneficiary.setOnClickListener((view) -> {
            getFirstConnection((connection -> {
                if (connection.getAccounts() == null || connection.getAccounts().isEmpty()) {
                    toast(GET_ACCOUNTS_REQUIRED);
                } else if (wireBeneficiaryID == null) {
                    toast(GET_BENEFICIARIES_REQUIRED);
                } else {
                    connection.createWireTransferToExistingBeneficiary(
                            connection.getAccounts().get(0),
                            wireBeneficiaryID,
                            99.0,
                            "Test"
                    );
                }
            }));
        });

        btnGetWireBeneficiaries.setOnClickListener((view) -> {
            getFirstConnection((connection) -> {
                connection.getWireBeneficiaries((response) -> {
                    Log.i("DapiResponse", response.toString());
                    toast(RESULT_PRINTED);
                    wireBeneficiaryID = response.getBeneficiaries().get(0).getId();
                    return null;
                }, (error) -> {
                    toast(error.getMessage());
                    return null;
                });
            });
        });

        btnCreateWireBeneficiary.setOnClickListener((view) -> {
            getFirstConnection((connection) -> {
                connection.createWireBeneficiary(getWireBeneficiary(), (response) -> {
                    Log.i("DapiResponse", response.toString());
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

    private DapiWireBeneficiary getWireBeneficiary() {
        LinesAddress address = new LinesAddress();
        address.setLine1("baniyas road");
        address.setLine2("dubai");
        address.setLine3("united arab emirates");

        return new DapiWireBeneficiary(
                "John Doe",
                "John",
                "Doe",
                "JohnDoe",
                address,
                "Conway",
                "Arkansas",
                "US",
                "92335",
                "retail",
                "checking",
                "953349354",
                "1234567654321"
        );
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
    public void onConnectionSuccessful(@NotNull DapiConnection connection) {

    }

    @Override
    public void onConnectionFailure(@NotNull DapiError error, @NotNull String bankID) {

    }

    @Override
    public void onDismissed() {

    }

    @Override
    public void onBankRequest(@NotNull String bankName, @NotNull String iban) {

    }

    //Transfer callbacks
    @Override
    public void onTransferFailure(@Nullable DapiAccountsResponse.DapiAccount account, @NotNull DapiError error) {

    }

    @Override
    public void onTransferSuccess(@NotNull DapiAccountsResponse.DapiAccount account, double amount, @Nullable String reference, String operationID) {

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