package com.dapi.dapiconnect.api.data.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dapi.dapiconnect.MainViewModel
import com.dapi.dapiconnect.api.common.ResponseListItem

@Composable
fun CardsScreen(
    viewModel: MainViewModel
) {
    val state = viewModel.cardsState.value

    if (state.loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

    if (state.cards != null) {
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            items(state.cards) { card ->
                ResponseListItem(
                    topLeft = card.name.toString(),
                    topRight = "${card.currency?.code} ${card.balance?.availableBalance}",
                    bottomLeft = card.status.toString(),
                    bottomRight = "***${card.cardNumber.toString().takeLast(4)}"
                )
            }
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