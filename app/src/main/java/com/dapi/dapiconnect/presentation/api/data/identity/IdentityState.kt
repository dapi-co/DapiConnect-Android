package com.dapi.dapiconnect.presentation.api.data.identity

import co.dapi.connect.data.endpoint_models.DapiIdentityResponse

data class IdentityState(
    val loading: Boolean = false,
    val identity: DapiIdentityResponse.DapiIdentity? = null,
    val error: String? = null
)