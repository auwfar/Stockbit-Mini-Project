package com.auwfar.stockbitminiproject.modules

import com.auwfar.stockbitminiproject.api.CryptoApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    fun provideCountriesApi(retrofit: Retrofit): CryptoApi {
        return retrofit.create(CryptoApi::class.java)
    }
    single { provideCountriesApi(get()) }
}