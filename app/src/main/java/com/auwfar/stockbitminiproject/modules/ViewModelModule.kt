package com.auwfar.stockbitminiproject.modules

import com.auwfar.stockbitminiproject.viewmodels.CryptoViewModel
import com.auwfar.stockbitminiproject.viewmodels.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { UsersViewModel(get()) }
    viewModel { CryptoViewModel(get()) }
}