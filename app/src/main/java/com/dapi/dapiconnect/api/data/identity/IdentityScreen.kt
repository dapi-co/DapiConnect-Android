package com.dapi.dapiconnect.api.data.identity

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
import com.dapi.dapiconnect.theme.appColors

@Composable
fun IdentityScreen(
    viewModel: MainViewModel
) {
    val state = viewModel.identityState.value

    if (state.loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

    if (state.identity != null) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            ResponseItem(text = "Name: ${state.identity.name}")
            ResponseItem(text = "Address Area: ${state.identity.address?.area}")
            ResponseItem(text = "Address Building: ${state.identity.address?.building}")
            ResponseItem(text = "Address City: ${state.identity.address?.city}")
            ResponseItem(text = "Address Country: ${state.identity.address?.country}")
            ResponseItem(text = "Address Flat: ${state.identity.address?.flat}")
            ResponseItem(text = "Address Full: ${state.identity.address?.full}")
            ResponseItem(text = "Address PoBox: ${state.identity.address?.poBox}")
            ResponseItem(text = "Address State: ${state.identity.address?.state}")
            ResponseItem(text = "Address ZipCode: ${state.identity.address?.zipCode}")
            ResponseItem(text = "DateOfBirth: ${state.identity.dateOfBirth}")
            ResponseItem(text = "EmailAddress: ${state.identity.emailAddress}")
            ResponseItem(text = "Nationality: ${state.identity.nationality}")
            ResponseItem(text = "More in the console...")
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