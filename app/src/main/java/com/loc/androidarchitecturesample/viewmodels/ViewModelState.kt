package com.loc.androidarchitecturesample.viewmodels

import com.loc.androidarchitecturesample.domain.model.Product

data class ViewModelState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String? = null
)