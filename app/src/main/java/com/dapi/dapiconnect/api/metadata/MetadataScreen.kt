package com.dapi.dapiconnect.api.metadata

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dapi.dapiconnect.MainViewModel
import com.dapi.dapiconnect.api.common.ResponseItem

@Composable
fun MetadataScreen(viewModel: MainViewModel) {
    val state = viewModel.metadataState.value

    if (state.loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

    if (state.metadata != null) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            ResponseItem(text = "BankId: ${state.metadata.bankId}")
            ResponseItem(text = "BankName: ${state.metadata.bankName}")
            ResponseItem(text = "Address Line 1: ${state.metadata.address?.line1}")
            ResponseItem(text = "Address Line 2: ${state.metadata.address?.line2}")
            ResponseItem(text = "Address Line 3: ${state.metadata.address?.line3}")
            ResponseItem(text = "BeneficiaryCoolDownPeriod Unit: ${state.metadata.beneficiaryCoolDownPeriod?.unit}")
            ResponseItem(text = "BeneficiaryCoolDownPeriod Value: ${state.metadata.beneficiaryCoolDownPeriod?.value}")
            ResponseItem(text = "BranchAddress: ${state.metadata.branchAddress}")
            ResponseItem(text = "BranchName: ${state.metadata.branchName}")
            ResponseItem(text = "Country Name: ${state.metadata.country?.name}")
            ResponseItem(text = "Country Code: ${state.metadata.country?.code}")
            ResponseItem(text = "IsCreateBeneficiaryEndpointRequired: ${state.metadata.isCreateBeneficiaryEndpointRequired}")
            ResponseItem(text = "SortCode: ${state.metadata.sortCode}")
            ResponseItem(text = "SwiftCode: ${state.metadata.swiftCode}")
            ResponseItem(text = "TransactionRange Unit: ${state.metadata.transactionRange?.unit}")
            ResponseItem(text = "TransactionRange Value: ${state.metadata.transactionRange?.value}")
            ResponseItem(text = "WillNewlyAddedBeneficiaryExistBeforeCoolDownPeriod: ${state.metadata.willNewlyAddedBeneficiaryExistBeforeCoolDownPeriod}")
            ResponseItem(text = "More in the console...")
        }

    }

    if (state.error != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(all = 24.dp)
            )
        }
    }
}