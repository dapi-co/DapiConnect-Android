package com.dapi.dapiconnect.presentation.api.wire.beneficiaries

import co.dapi.connect.data.endpoint_models.DapiBeneficiariesResponse
import co.dapi.connect.data.endpoint_models.DapiWireBeneficiariesResponse


data class WireBeneficiariesState(
    val loading: Boolean = false,
    val beneficiaries: List<DapiWireBeneficiariesResponse.WireBeneficiary>? = null,
    val error: String? = null
)