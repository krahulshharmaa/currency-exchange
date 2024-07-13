package com.currency.flip.data.repository

import arrow.core.Either
import com.currency.flip.data.maper.toGeneralError
import com.currency.flip.data.remote.ApiInterface
import com.currency.flip.domain.model.NetworkError
import com.currency.flip.domain.model.Product
import com.currency.flip.domain.repository.ProductsRepository
import javax.inject.Inject


class ProductsRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : ProductsRepository {
    override suspend fun getProducts(): Either<NetworkError, List<Product>> {
        return Either.catch {
            apiInterface.getProducts()
        }.mapLeft { it.toGeneralError() }
    }
}