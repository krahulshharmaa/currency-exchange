package com.currency.flip.di

import com.currency.flip.data.repository.ConversionRepositoryImpl
import com.currency.flip.data.repository.ProductsRepositoryImpl
import com.currency.flip.domain.repository.ConversionRepository
import com.currency.flip.domain.repository.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideProductsRepository(impl: ProductsRepositoryImpl): ProductsRepository

    @Binds
    @Singleton
    abstract fun provideConversionRepository(impl: ConversionRepositoryImpl): ConversionRepository

}