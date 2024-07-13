package com.currency.flip.domain.repository

import arrow.core.Either
import com.currency.flip.domain.model.NetworkError
import com.currency.flip.domain.model.Product

interface ProductsRepository {

    suspend fun getProducts(): Either<NetworkError, List<Product>>

}
