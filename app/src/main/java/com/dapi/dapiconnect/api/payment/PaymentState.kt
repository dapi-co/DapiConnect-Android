package com.dapi.dapiconnect.api.payment

import co.dapi.connect.core.callbacks.DapiTransferResult

data class PaymentState(
    val loading: Boolean = false,
    val result: DapiTransferResult.Success? = null,
    val error: String? = null
)