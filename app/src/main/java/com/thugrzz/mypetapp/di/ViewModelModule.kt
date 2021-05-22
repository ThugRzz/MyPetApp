package com.thugrzz.mypetapp.di

import com.thugrzz.mypetapp.features.auth.authorization.AuthViewModel
import com.thugrzz.mypetapp.features.auth.register.RegisterViewModel
import com.thugrzz.mypetapp.features.schedule.ScheduleViewModel
import com.thugrzz.mypetapp.features.schedule.add_schedule.AddScheduleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { AuthViewModel(get(), get()) }
    viewModel { ScheduleViewModel(get()) }
    viewModel { AddScheduleViewModel(get(), get(), get()) }
    viewModel { RegisterViewModel(get(), get(), get()) }
}