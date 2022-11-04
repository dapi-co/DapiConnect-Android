package com.dapi.dapiconnect.api

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dapi.dapiconnect.MainViewModel
import com.dapi.dapiconnect.navigation.Screen
import com.dapi.dapiconnect.api.common.MainActionButton

@Composable
fun ApiSelectionScreen(viewModel: MainViewModel, navController: NavHostController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        MainActionButton(
            text = "Data",
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 24.dp),
            onClick = {
                navController.navigate(Screen.Data.route)
            }
        )

        MainActionButton(
            text = "Metadata",
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 24.dp),
            onClick = {
                navController.navigate(Screen.Metadata.route)
                viewModel.getAccountsMetadata()
            }
        )

        MainActionButton(
            text = "Payment",
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 24.dp),
            onClick = {
                navController.navigate(Screen.Payment.route)
            }
        )

        MainActionButton(
            text = "Wire (US Only)",
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 24.dp),
            onClick = {
                navController.navigate(Screen.Wire.route)
            }
        )
    }
}