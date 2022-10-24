package com.dapi.dapiconnect.presentation.home

import co.dapi.connect.data.models.DapiConnection
import co.dapi.connect.data.models.DapiError

data class HomeState(
    val connections: List<DapiConnection> = emptyList(),
    val error: String? = null
)