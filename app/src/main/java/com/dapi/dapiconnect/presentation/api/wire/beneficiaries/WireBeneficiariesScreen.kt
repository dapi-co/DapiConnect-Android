package com.dapi.dapiconnect.presentation.api.wire.beneficiaries

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.dapi.connect.data.endpoint_models.DapiWireBeneficiariesResponse
import com.dapi.dapiconnect.MainViewModel
import com.dapi.dapiconnect.presentation.theme.Grey1
import com.dapi.dapiconnect.presentation.theme.appColors

@Composable
fun WireBeneficiariesScreen(
    viewModel: MainViewModel
) {
    val state = viewModel.wireBeneficiariesState.value

    if (state.loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

    if (state.beneficiaries != null) {
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            items(state.beneficiaries) { beneficiary ->
                WireBeneficiaryItem(beneficiary = beneficiary)
            }
        }

    }

    if (state.error != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = state.error,
                color = MaterialTheme.appColors.error,
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

@Composable
private fun WireBeneficiaryItem(beneficiary: DapiWireBeneficiariesResponse.WireBeneficiary) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .background(
                color = Grey1,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 10.dp, end = 16.dp, bottom = 10.dp)
        ) {
            Text(
                text = beneficiary.name.toString(),
                color = MaterialTheme.appColors.secondaryText,
                fontSize = 14.sp,
                style = MaterialTheme.typography.body1,
            )
            Text(
                text = beneficiary.type.toString(),
                color = MaterialTheme.appColors.primaryText,
                fontSize = 14.sp,
                style = MaterialTheme.typography.subtitle1,
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 16.dp, bottom = 10.dp)
        ) {
            Text(
                text = beneficiary.routingNumber.toString(),
                color = MaterialTheme.appColors.secondaryText,
                fontSize = 12.sp,
                style = MaterialTheme.typography.body1,
            )
            Text(
                text = "***${beneficiary.accountNumber.toString().takeLast(4)}",
                color = MaterialTheme.appColors.secondaryText,
                fontSize = 12.sp,
                style = MaterialTheme.typography.body1,
            )
        }
    }
}