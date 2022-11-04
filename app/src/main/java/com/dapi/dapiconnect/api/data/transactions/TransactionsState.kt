package com.dapi.dapiconnect.api.data.transactions

import co.dapi.connect.data.endpoint_models.DapiTransactionsResponse

data class TransactionsState(
    val loading: Boolean = false,
    val transactions: List<DapiTransactionsResponse.DapiTransaction>? = null,
    val error: String? = null
)