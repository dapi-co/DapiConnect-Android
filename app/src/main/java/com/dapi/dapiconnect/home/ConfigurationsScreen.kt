package com.dapi.dapiconnect.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import co.dapi.connect.core.base.Dapi
import co.dapi.connect.data.models.DapiEnvironment
import co.dapi.connect.data.models.DapiLanguage
import co.dapi.connect.data.models.DapiTheme
import com.dapi.dapiconnect.home.components.ConfigurationCheckbox

@Composable
fun ConfigurationsScreen() {
    val isSandboxChecked =
        remember { mutableStateOf(Dapi.configurations.environment == DapiEnvironment.SANDBOX) }
    val isShowLogosChecked = remember { mutableStateOf(Dapi.configurations.showLogos) }
    val isShowExperimentalBanksChecked =
        remember { mutableStateOf(Dapi.configurations.showExperimentalBanks) }
    val isShowCloseButtonChecked =
        remember { mutableStateOf(Dapi.configurations.showCloseButton) }
    val isShowAddButtonChecked = remember { mutableStateOf(Dapi.configurations.showAddButton) }
    val isShowTransferSuccessfulResultChecked =
        remember { mutableStateOf(Dapi.configurations.showTransferSuccessfulResult) }
    val isShowTransferErrorResultChecked =
        remember { mutableStateOf(Dapi.configurations.showTransferErrorResult) }
    var theme by remember { mutableStateOf(TextFieldValue(Dapi.configurations.theme.enforceTheme.name)) }
    var lightPrimaryColor by remember {
        mutableStateOf(
            TextFieldValue(
                Dapi.configurations.theme.primaryColor.lightMode ?: ""
            )
        )
    }
    var darkPrimaryColor by remember {
        mutableStateOf(
            TextFieldValue(
                Dapi.configurations.theme.primaryColor.darkMode ?: ""
            )
        )
    }
    var language by remember { mutableStateOf(TextFieldValue(Dapi.configurations.language.name)) }


    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        ConfigurationCheckbox(isSandboxChecked, "Sandbox") {
            Dapi.configurations.environment =
                if (it) DapiEnvironment.SANDBOX else DapiEnvironment.PRODUCTION
        }

        ConfigurationCheckbox(isShowLogosChecked, "Show Logos") {
            Dapi.configurations.showLogos = it
        }

        ConfigurationCheckbox(isShowExperimentalBanksChecked, "Show Experimental Banks") {
            Dapi.configurations.showExperimentalBanks = it
        }

        ConfigurationCheckbox(isShowCloseButtonChecked, "Show Close Button") {
            Dapi.configurations.showCloseButton = it
        }

        ConfigurationCheckbox(isShowAddButtonChecked, "Show Add Button") {
            Dapi.configurations.showAddButton = it
        }

        ConfigurationCheckbox(
            isShowTransferSuccessfulResultChecked,
            "Show Transfer Successful Result"
        ) {
            Dapi.configurations.showTransferSuccessfulResult = it
        }

        ConfigurationCheckbox(isShowTransferErrorResultChecked, "Show Transfer Error Result") {
            Dapi.configurations.showTransferErrorResult = it
        }

        TextField(
            modifier = Modifier.padding(all = 16.dp).fillMaxWidth(),
            value = theme,
            label = { Text(text = "Theme: LIGHT, DARK, DYNAMIC") },
            onValueChange = {
                theme = it
                when (theme.text.lowercase()) {
                    "light" -> {
                        Dapi.configurations.theme.enforceTheme = DapiTheme.LIGHT
                    }
                    "dark" -> {
                        Dapi.configurations.theme.enforceTheme = DapiTheme.DARK
                    }
                    else -> {
                        Dapi.configurations.theme.enforceTheme = DapiTheme.DYNAMIC
                    }
                }
            }
        )

        TextField(
            modifier = Modifier.padding(all = 16.dp).fillMaxWidth(),
            value = lightPrimaryColor,
            label = { Text(text = "Light Primary Color #xxxxxx") },
            onValueChange = {
                lightPrimaryColor = it
                if (lightPrimaryColor.text.matches(Regex("^#(?:[0-9a-fA-F]{3}){1,2}\$")))
                    Dapi.configurations.theme.primaryColor.lightMode = lightPrimaryColor.text
            }
        )

        TextField(
            modifier = Modifier.padding(all = 16.dp).fillMaxWidth(),
            value = darkPrimaryColor,
            label = { Text(text = "Dark Primary Color #xxxxxx") },
            onValueChange = {
                darkPrimaryColor = it
                if (darkPrimaryColor.text.matches(Regex("^#(?:[0-9a-fA-F]{3}){1,2}\$")))
                    Dapi.configurations.theme.primaryColor.darkMode = darkPrimaryColor.text
            }
        )

        TextField(
            modifier = Modifier.padding(all = 16.dp).fillMaxWidth(),
            value = language,
            label = { Text(text = "Language: AR, EN") },
            onValueChange = {
                language = it
                when (language.text.lowercase()) {
                    "ar" -> {
                        Dapi.configurations.language = DapiLanguage.AR
                    }
                    else -> {
                        Dapi.configurations.language = DapiLanguage.EN
                    }
                }
            }
        )
    }


}