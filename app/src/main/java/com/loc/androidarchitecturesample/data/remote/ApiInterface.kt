package com.loc.androidarchitecturesample.data.remote

import com.loc.androidarchitecturesample.domain.model.Product
import retrofit2.http.GET

interface ApiInterface {

    @GET("products")
    suspend fun getProducts(): List<Product>

}