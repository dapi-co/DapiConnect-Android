package com.dapi.dapiconnect.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dapi.dapiconnect.MainViewModel
import com.dapi.dapiconnect.navigation.Screen
import com.dapi.dapiconnect.home.components.DapiConnectionItem
import com.dapi.dapiconnect.api.common.MainActionButton
import com.dapi.dapiconnect.theme.appColors

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    navController: NavHostController,
    onConnect: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state = viewModel.homeState.value

    if (state.error != null) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                message = state.error,
                duration = SnackbarDuration.Long
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Your current connections",
            color = MaterialTheme.appColors.primaryText,
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(all = 24.dp)
                .align(Alignment.Start)
        )

        if (state.connections.isEmpty() && state.error == null) {
            Text(
                text = "Connect your bank account to see it here.",
                color = MaterialTheme.appColors.secondaryText,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp)
            )
        } else {
            LazyColumn {
                items(state.connections) { connection ->
                    DapiConnectionItem(
                        connection = connection,
                        onClick = {
                            viewModel.setOperatingConnection(connection)
                            navController.navigate(Screen.APIs.route)
                        }
                    )
                }
            }
        }

        Column {
            MainActionButton(
                text = "Connect With Dapi",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp),
                onClick = {
                    onConnect()
                }
            )

            SnackbarHost(
                hostState = snackbarHostState,
            )
        }

    }

}
