package com.dapi.dapiconnect.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dapi.dapiconnect.MainViewModel
import com.dapi.dapiconnect.api.ApiSelectionScreen
import com.dapi.dapiconnect.api.data.DataScreen
import com.dapi.dapiconnect.api.data.accounts.AccountsScreen
import com.dapi.dapiconnect.api.data.cards.CardsScreen
import com.dapi.dapiconnect.api.data.identity.IdentityScreen
import com.dapi.dapiconnect.api.data.transactions.TransactionsScreen
import com.dapi.dapiconnect.api.metadata.MetadataScreen
import com.dapi.dapiconnect.api.payment.PaymentScreen
import com.dapi.dapiconnect.api.payment.beneficiaries.BeneficiariesScreen
import com.dapi.dapiconnect.api.payment.create_beneficiary.CreateBeneficiaryScreen
import com.dapi.dapiconnect.api.wire.WireScreen
import com.dapi.dapiconnect.api.wire.beneficiaries.WireBeneficiariesScreen
import com.dapi.dapiconnect.api.wire.create_beneficiary.CreateWireBeneficiaryScreen
import com.dapi.dapiconnect.home.HomeScreen

@Composable
fun AppNavigation(
    viewModel: MainViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(
                viewModel = viewModel,
                navController = navController
            ) {
                viewModel.presentConnect()
            }
        }

        composable(
            route = Screen.APIs.route
        ) {
            ApiSelectionScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(
            route = Screen.Metadata.route
        ) {
            MetadataScreen(viewModel = viewModel)
        }

        composable(
            route = Screen.Data.route
        ) {
            DataScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(
            route = Screen.Identity.route
        ) {
            IdentityScreen(
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.Accounts.route
        ) {
            AccountsScreen(
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.Cards.route
        ) {
            CardsScreen(
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.Transactions.route
        ) {
            TransactionsScreen(
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.Payment.route
        ) {
            PaymentScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(
            route = Screen.Wire.route
        ) {
            WireScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(
            route = Screen.Beneficiaries.route
        ) {
            BeneficiariesScreen(viewModel = viewModel)
        }

        composable(
            route = Screen.CreateBeneficiary.route
        ) {
            CreateBeneficiaryScreen(
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.WireBeneficiaries.route
        ) {
            WireBeneficiariesScreen(viewModel = viewModel)
        }

        composable(
            route = Screen.CreateWireBeneficiary.route
        ) {
            CreateWireBeneficiaryScreen(viewModel = viewModel)
        }

    }
}