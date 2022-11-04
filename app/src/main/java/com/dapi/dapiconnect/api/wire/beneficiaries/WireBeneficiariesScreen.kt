package com.dapi.dapiconnect.api.wire.beneficiaries

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
import com.dapi.dapiconnect.api.common.ResponseListItem
import com.dapi.dapiconnect.theme.Grey1
import com.dapi.dapiconnect.theme.appColors

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
                ResponseListItem(
                    topLeft = beneficiary.name.toString(),
                    topRight = beneficiary.type.toString(),
                    bottomLeft = beneficiary.routingNumber.toString(),
                    bottomRight = "***${beneficiary.accountNumber.toString().takeLast(4)}"
                )
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