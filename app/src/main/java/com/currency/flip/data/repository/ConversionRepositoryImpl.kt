package com.currency.flip.data.repository

import arrow.core.Either
import com.currency.flip.data.maper.toGeneralError
import com.currency.flip.data.remote.ApiInterface
import com.currency.flip.domain.model.Conversion
import com.currency.flip.domain.model.NetworkError
import com.currency.flip.domain.repository.ConversionRepository
import com.currency.flip.utils.Constants
import javax.inject.Inject


class ConversionRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : ConversionRepository {
    override suspend fun getConversion(base:String): Either<NetworkError, Conversion> {
        return Either.catch {
            apiInterface.getConversion(Constants.API_Key,base)
        }.mapLeft { it.toGeneralError() }
    }

}