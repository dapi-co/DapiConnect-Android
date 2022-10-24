package com.dapi.dapiconnect.api.payment.create_beneficiary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import co.dapi.connect.data.models.DapiBeneficiary
import co.dapi.connect.data.models.LinesAddress
import com.dapi.dapiconnect.MainViewModel
import com.dapi.dapiconnect.home.components.MainActionButton

@Composable
fun CreateBeneficiaryScreen(
    viewModel: MainViewModel
) {
    val state = viewModel.createBeneficiaryState.value
    val snackbarHostState = remember { SnackbarHostState() }
    var accountNumber by remember { mutableStateOf(TextFieldValue("")) }
    var iban by remember { mutableStateOf(TextFieldValue("")) }
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var bankName by remember { mutableStateOf(TextFieldValue("")) }
    var swiftCode by remember { mutableStateOf(TextFieldValue("")) }
    var country by remember { mutableStateOf(TextFieldValue("")) }
    var branchAddress by remember { mutableStateOf(TextFieldValue("")) }
    var branchName by remember { mutableStateOf(TextFieldValue("")) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }
    var nickname by remember { mutableStateOf(TextFieldValue("")) }
    var addressLine1 by remember { mutableStateOf(TextFieldValue("")) }
    var addressLine2 by remember { mutableStateOf(TextFieldValue("")) }
    var addressLine3 by remember { mutableStateOf(TextFieldValue("")) }

    if (state.error != null) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                message = state.error,
                duration = SnackbarDuration.Long
            )
        }
    }

    if (state.result != null) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                message = "Added the beneficiary successfully.",
                duration = SnackbarDuration.Long
            )
        }
    }


    Box {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TextField(
                value = accountNumber,
                label = { Text(text = "Account Number") },
                onValueChange = {
                    accountNumber = it
                }
            )

            TextField(
                value = iban,
                label = { Text(text = "IBAN") },
                onValueChange = {
                    iban = it
                }
            )

            TextField(
                value = name,
                label = { Text(text = "Name") },
                onValueChange = {
                    name = it
                }
            )

            TextField(
                value = bankName,
                label = { Text(text = "Bank Name") },
                onValueChange = {
                    bankName = it
                }
            )

            TextField(
                value = swiftCode,
                label = { Text(text = "Swift Code") },
                onValueChange = {
                    swiftCode = it
                }
            )

            TextField(
                value = country,
                label = { Text(text = "Country") },
                onValueChange = {
                    country = it
                }
            )

            TextField(
                value = branchAddress,
                label = { Text(text = "Branch Address") },
                onValueChange = {
                    branchAddress = it
                }
            )

            TextField(
                value = branchName,
                label = { Text(text = "Branch Name") },
                onValueChange = {
                    branchName = it
                }
            )

            TextField(
                value = phoneNumber,
                label = { Text(text = "Phone Number") },
                onValueChange = {
                    phoneNumber = it
                }
            )

            TextField(
                value = nickname,
                label = { Text(text = "Nickname") },
                onValueChange = {
                    nickname = it
                }
            )

            TextField(
                value = addressLine1,
                label = { Text(text = "Address Line 1") },
                onValueChange = {
                    addressLine1 = it
                }
            )

            TextField(
                value = addressLine2,
                label = { Text(text = "Address Line 2") },
                onValueChange = {
                    addressLine2 = it
                }
            )

            TextField(
                value = addressLine3,
                label = { Text(text = "Address Line 3") },
                onValueChange = {
                    addressLine3 = it
                }
            )

            MainActionButton(
                text = "Confirm",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp),
                onClick = {
                    viewModel.createBeneficiary(
                        DapiBeneficiary(
                            accountNumber = accountNumber.text,
                            iban = iban.text,
                            name = name.text,
                            bankName = bankName.text,
                            swiftCode = swiftCode.text,
                            country = country.text,
                            branchAddress = branchAddress.text,
                            branchName = branchName.text,
                            phoneNumber = phoneNumber.text,
                            nickname = nickname.text,
                            address = LinesAddress(
                                line1 = addressLine1.text,
                                line2 = addressLine2.text,
                                line3 = addressLine3.text,
                            )
                        )
                    )
                }
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }


}
