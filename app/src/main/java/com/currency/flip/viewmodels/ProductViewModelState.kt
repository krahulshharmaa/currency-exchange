package com.currency.flip.viewmodels

import com.currency.flip.domain.model.Product

data class ProductViewModelState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String? = null
)