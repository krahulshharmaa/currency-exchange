package com.loc.androidarchitecturesample.domain.repository

import arrow.core.Either
import com.loc.androidarchitecturesample.domain.model.NetworkError
import com.loc.androidarchitecturesample.domain.model.Product

interface ProductsRepository {

    suspend fun getProducts(): Either<NetworkError, List<Product>>

}
