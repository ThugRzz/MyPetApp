package com.thugrzz.mypetapp.di

import com.thugrzz.mypetapp.features.profile.dialog.ChangeParamViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { ChangeParamViewModel(get()) }
}