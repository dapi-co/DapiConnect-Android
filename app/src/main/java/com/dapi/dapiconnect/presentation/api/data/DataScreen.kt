package com.dapi.dapiconnect.presentation.api.data

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import co.dapi.connect.data.models.DapiTransactionsType
import com.dapi.dapiconnect.MainViewModel
import com.dapi.dapiconnect.presentation.Screen
import com.dapi.dapiconnect.presentation.home.components.MainActionButton
import java.util.*

@Composable
fun DataScreen(
    viewModel: MainViewModel,
    navController: NavHostController
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        MainActionButton(
            text = "Identity",
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 24.dp),
            onClick = {
                viewModel.clearState(MainViewModel.AppState.IDENTITY)
                navController.navigate(Screen.Identity.route)
                viewModel.getIdentity()
            }
        )

        MainActionButton(
            text = "Accounts",
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 24.dp),
            onClick = {
                viewModel.clearState(MainViewModel.AppState.ACCOUNTS)
                navController.navigate(Screen.Accounts.route)
                viewModel.getAccounts()
            }
        )

        MainActionButton(
            text = "Cards",
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 24.dp),
            onClick = {
                viewModel.clearState(MainViewModel.AppState.CARDS)
                navController.navigate(Screen.Cards.route)
                viewModel.getCards()
            }
        )

        MainActionButton(
            text = "Account Transactions",
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 24.dp),
            onClick = {
                viewModel.clearState(MainViewModel.AppState.TRANSACTIONS)
                navController.navigate(Screen.Transactions.route)
                viewModel.getTransactionsForAccount(
                    fromDate = Date(1661301064000),
                    toDate = Date(),
                    type = DapiTransactionsType.DEFAULT
                )
            }
        )

        MainActionButton(
            text = "Card Transactions",
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 24.dp),
            onClick = {
                viewModel.clearState(MainViewModel.AppState.TRANSACTIONS)
                navController.navigate(Screen.Transactions.route)
                viewModel.getTransactionsForCard(
                    fromDate = Date(1661301064000),
                    toDate = Date(),
                    type = DapiTransactionsType.DEFAULT
                )
            }
        )
    }
}