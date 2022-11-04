package com.dapi.dapiconnect.api.metadata

import co.dapi.connect.data.endpoint_models.DapiAccountsMetaDataResponse

data class MetadataState(
    val loading: Boolean = false,
    val metadata: DapiAccountsMetaDataResponse.DapiAccountsMetadata? = null,
    val error: String? = null
)