package com.dapi.dapiconnect.navigation


sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object Configurations : Screen("configurations_screen")
    object APIs : Screen("apis_screen")
    object Data : Screen("data_screen")
    object Metadata : Screen("metadata_screen")
    object Payment : Screen("payment_screen")
    object Wire : Screen("wire_screen")
    object Identity : Screen("identity_screen")
    object Accounts : Screen("accounts_screen")
    object Cards : Screen("cards_screen")
    object Transactions : Screen("transaction_screen")
    object CreateBeneficiary : Screen("create_beneficiary_screen")
    object Beneficiaries : Screen("beneficiaries_screen")
    object CreateWireBeneficiary : Screen("create_wire_beneficiary_screen")
    object WireBeneficiaries : Screen("wire_beneficiaries_screen")


    fun withArgs(vararg args: Any): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("$arg")
            }
        }
    }
}