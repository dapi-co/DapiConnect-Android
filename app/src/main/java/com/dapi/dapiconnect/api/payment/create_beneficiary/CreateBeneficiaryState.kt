package com.dapi.dapiconnect.api.payment.create_beneficiary

import co.dapi.connect.data.endpoint_models.DapiCreateBeneficiaryResponse

data class CreateBeneficiaryState(
    val loading: Boolean = false,
    val result: DapiCreateBeneficiaryResponse? = null,
    val error: String? = null
)