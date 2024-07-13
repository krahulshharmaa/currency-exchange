package com.currency.flip.viewmodels

import com.currency.flip.domain.model.Conversions

data class ConversionViewModelState(
    val isLoading: Boolean = false,
    val convertedRate: String?=null,
    val conversions: ArrayList<Conversions>? = null,
    val error: String? = null
)

