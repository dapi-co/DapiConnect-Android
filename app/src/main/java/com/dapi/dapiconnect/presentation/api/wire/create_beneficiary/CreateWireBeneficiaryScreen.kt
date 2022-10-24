package com.dapi.dapiconnect.presentation.api.wire.create_beneficiary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import co.dapi.connect.data.models.DapiWireBeneficiary
import co.dapi.connect.data.models.LinesAddress
import com.dapi.dapiconnect.MainViewModel
import com.dapi.dapiconnect.presentation.home.components.MainActionButton

@Composable
fun CreateWireBeneficiaryScreen(
    viewModel: MainViewModel
) {
    val createBenefState = viewModel.createBeneficiaryState.value
    val snackbarHostState = remember { SnackbarHostState() }

    var name by remember { mutableStateOf(TextFieldValue("")) }
    var firstName by remember { mutableStateOf(TextFieldValue("")) }
    var lastName by remember { mutableStateOf(TextFieldValue("")) }
    var nickname by remember { mutableStateOf(TextFieldValue("")) }
    var city by remember { mutableStateOf(TextFieldValue("")) }
    var state by remember { mutableStateOf(TextFieldValue("")) }
    var country by remember { mutableStateOf(TextFieldValue("")) }
    var zipCode by remember { mutableStateOf(TextFieldValue("")) }
    var receiverType by remember { mutableStateOf(TextFieldValue("")) }
    var receiverAccountType by remember { mutableStateOf(TextFieldValue("")) }
    var routingNumber by remember { mutableStateOf(TextFieldValue("")) }
    var accountNumber by remember { mutableStateOf(TextFieldValue("")) }
    var addressLine1 by remember { mutableStateOf(TextFieldValue("")) }
    var addressLine2 by remember { mutableStateOf(TextFieldValue("")) }
    var addressLine3 by remember { mutableStateOf(TextFieldValue("")) }

    if (createBenefState.error != null) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                message = createBenefState.error,
                duration = SnackbarDuration.Long
            )
        }
    }

    if (createBenefState.result != null) {
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
                value = name,
                label = { Text(text = "Name") },
                onValueChange = {
                    name = it
                }
            )

            TextField(
                value = firstName,
                label = { Text(text = "First Name") },
                onValueChange = {
                    firstName = it
                }
            )

            TextField(
                value = lastName,
                label = { Text(text = "Last Name") },
                onValueChange = {
                    lastName = it
                }
            )

            TextField(
                value = nickname,
                label = { Text(text = "Nick Name") },
                onValueChange = {
                    nickname = it
                }
            )

            TextField(
                value = city,
                label = { Text(text = "City") },
                onValueChange = {
                    city = it
                }
            )

            TextField(
                value = state,
                label = { Text(text = "State") },
                onValueChange = {
                    state = it
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
                value = zipCode,
                label = { Text(text = "Zip Code") },
                onValueChange = {
                    zipCode = it
                }
            )

            TextField(
                value = receiverType,
                label = { Text(text = "Receiver Type") },
                onValueChange = {
                    receiverType = it
                }
            )

            TextField(
                value = receiverAccountType,
                label = { Text(text = "Receiver Account Type") },
                onValueChange = {
                    receiverAccountType = it
                }
            )

            TextField(
                value = routingNumber,
                label = { Text(text = "Routing Number") },
                onValueChange = {
                    routingNumber = it
                }
            )

            TextField(
                value = accountNumber,
                label = { Text(text = "Account Number") },
                onValueChange = {
                    accountNumber = it
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
                    viewModel.createWireBeneficiary(
                        DapiWireBeneficiary(
                            name = name.text,
                            firstName = firstName.text,
                            lastName = lastName.text,
                            nickname = nickname.text,
                            city = city.text,
                            state = state.text,
                            country = country.text,
                            zipCode = zipCode.text,
                            receiverType = receiverType.text,
                            receiverAccountType = receiverAccountType.text,
                            routingNumber = routingNumber.text,
                            accountNumber = accountNumber.text,
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
