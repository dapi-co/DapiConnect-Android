package com.dapi.dapiconnect.api.payment

import androidx.compose.foundation.layout.*
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dapi.dapiconnect.MainViewModel
import com.dapi.dapiconnect.navigation.Screen
import com.dapi.dapiconnect.api.common.MainActionButton

@Composable
fun PaymentScreen(
    viewModel: MainViewModel,
    navController: NavHostController
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state = viewModel.paymentState.value

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
                message = "Money transfer was successful 🎉",
                duration = SnackbarDuration.Long
            )
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainActionButton(
                text = "Payment Using Dapi UI",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp),
                onClick = {
                    viewModel.clearState(MainViewModel.AppState.PAYMENT)
                    viewModel.createTransfer()
                }
            )

            MainActionButton(
                text = "Create Beneficiary",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp),
                onClick = {
                    viewModel.clearState(MainViewModel.AppState.CREATE_BENEFICIARY)
                    navController.navigate(Screen.CreateBeneficiary.route)
                }
            )

            MainActionButton(
                text = "Beneficiaries",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp),
                onClick = {
                    viewModel.clearState(MainViewModel.AppState.BENEFICIARIES)
                    navController.navigate(Screen.Beneficiaries.route)
                    viewModel.getBeneficiaries()
                }
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
        )
    }

}