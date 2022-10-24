package com.dapi.dapiconnect.api.payment.beneficiaries

import co.dapi.connect.data.endpoint_models.DapiWireBeneficiariesResponse


data class WireBeneficiariesState(
    val loading: Boolean = false,
    val beneficiaries: List<DapiWireBeneficiariesResponse.WireBeneficiary>? = null,
    val error: String? = null
)