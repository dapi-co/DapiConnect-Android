package com.dapi.dapiconnect.presentation.api.wire

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
import com.dapi.dapiconnect.presentation.Screen
import com.dapi.dapiconnect.presentation.home.components.MainActionButton

@Composable
fun WireScreen(
    viewModel: MainViewModel,
    navController: NavHostController,
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
                text = "Wire Payment Using Dapi UI",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp),
                onClick = {
                    viewModel.clearState(MainViewModel.AppState.PAYMENT)
                    viewModel.createWireTransfer()
                }
            )

            MainActionButton(
                text = "Create Wire Beneficiary",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp),
                onClick = {
                    viewModel.clearState(MainViewModel.AppState.CREATE_BENEFICIARY)
                    navController.navigate(Screen.CreateWireBeneficiary.route)
                }
            )

            MainActionButton(
                text = "Get Wire Beneficiaries",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp),
                onClick = {
                    viewModel.clearState(MainViewModel.AppState.WIRE_BENEFICIARIES)
                    navController.navigate(Screen.WireBeneficiaries.route)
                    viewModel.getWireBeneficiaries()
                }
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
        )
    }

}