package com.currency.flip.domain.repository

import arrow.core.Either
import com.currency.flip.domain.model.Conversion
import com.currency.flip.domain.model.NetworkError

interface ConversionRepository {

    suspend fun getConversion(base:String): Either<NetworkError, Conversion>

}
