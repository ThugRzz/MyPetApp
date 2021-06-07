package com.thugrzz.mypetapp.di

import com.thugrzz.mypetapp.data.repository.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    single<DatabaseRepository> { DatabaseRepositoryImpl(androidContext()) }

    single<NetworkRepository> {
        NetworkRepositoryImpl(get(), get())
    }
    single<PreferencesRepository> {
        PreferencesRepositoryImpl(get())
    }
}