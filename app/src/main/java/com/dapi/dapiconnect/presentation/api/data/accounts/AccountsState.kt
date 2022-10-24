package com.dapi.dapiconnect.presentation.api.data.accounts

import co.dapi.connect.data.endpoint_models.DapiAccountsResponse

data class AccountsState(
    val loading: Boolean = false,
    val accounts: List<DapiAccountsResponse.DapiAccount>? = null,
    val error: String? = null
)