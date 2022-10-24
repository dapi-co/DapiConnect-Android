package com.dapi.dapiconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dapi.dapiconnect.MainViewModel
import com.dapi.dapiconnect.navigation.AppNavigation
import com.dapi.dapiconnect.theme.AppTheme
import com.dapi.dapiconnect.theme.appColors


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.appColors.background
                ) {
                    AppNavigation(viewModel = viewModel())
                }
            }
        }
    }

}