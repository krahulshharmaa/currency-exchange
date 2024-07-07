package com.loc.androidarchitecturesample.data.repository

import arrow.core.Either
import com.loc.androidarchitecturesample.data.maper.toGeneralError
import com.loc.androidarchitecturesample.data.remote.ApiInterface
import com.loc.androidarchitecturesample.domain.model.NetworkError
import com.loc.androidarchitecturesample.domain.model.Product
import com.loc.androidarchitecturesample.domain.repository.ProductsRepository
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