package com.thugrzz.mypetapp.di


import com.thugrzz.mypetapp.features.auth.authorization.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { AuthViewModel(get(), get()) }
}