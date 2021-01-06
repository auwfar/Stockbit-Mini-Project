package com.auwfar.stockbitminiproject.modules

import com.auwfar.stockbitminiproject.api.CryptoApi
import com.auwfar.stockbitminiproject.local.dao.UsersDao
import com.auwfar.stockbitminiproject.repositories.CryptoRepository
import com.auwfar.stockbitminiproject.repositories.UsersRepository
import org.koin.dsl.module

val repositoryModule = module {
    fun provideUsersRepository(dao : UsersDao): UsersRepository {
        return UsersRepository(dao)
    }

    fun provideCryptoRepository(api: CryptoApi): CryptoRepository {
        return CryptoRepository(api)
    }

    single { provideUsersRepository(get()) }
    single { provideCryptoRepository(get()) }
}