package com.dapi.dapiconnect.presentation.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import co.dapi.connect.core.base.Dapi
import co.dapi.connect.core.callbacks.DapiConnectCallback
import co.dapi.connect.core.callbacks.DapiConnectResult
import co.dapi.connect.data.models.DapiBeneficiary
import co.dapi.connect.data.models.DapiWireBeneficiary
import co.dapi.connect.data.models.LinesAddress
import com.dapi.dapiconnect.MainViewModel
import com.dapi.dapiconnect.presentation.AppNavigation
import com.dapi.dapiconnect.presentation.theme.AppTheme
import com.dapi.dapiconnect.presentation.theme.appColors


class MainActivity : ComponentActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.appColors.background
                ) {
                    viewModel = viewModel()
                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }

}