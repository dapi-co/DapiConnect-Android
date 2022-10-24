package com.dapi.dapiconnect.api.payment.beneficiaries

import co.dapi.connect.data.endpoint_models.DapiBeneficiariesResponse


data class BeneficiariesState(
    val loading: Boolean = false,
    val beneficiaries: List<DapiBeneficiariesResponse.Beneficiary>? = null,
    val error: String? = null
)