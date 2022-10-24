package com.dapi.dapiconnect.presentation.api.data.cards

import co.dapi.connect.data.endpoint_models.DapiCardsResponse

data class CardsState(
    val loading: Boolean = false,
    val cards: List<DapiCardsResponse.DapiCard>? = null,
    val error: String? = null
)