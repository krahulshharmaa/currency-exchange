package com.currency.flip.data.remote

import com.currency.flip.domain.model.Conversion
import com.currency.flip.domain.model.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("{key}/latest/{base}")
    suspend fun getConversion(@Path("key") key:String, @Path("base") baseCurrency:String): Conversion

}