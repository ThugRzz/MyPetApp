package com.thugrzz.mypetapp.di

import com.thugrzz.mypetapp.data.repository.NetworkRepository
import com.thugrzz.mypetapp.data.repository.NetworkRepositoryImpl
import com.thugrzz.mypetapp.data.repository.PreferencesRepository
import com.thugrzz.mypetapp.data.repository.PreferencesRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<NetworkRepository> {
        NetworkRepositoryImpl(get())
    }
    single<PreferencesRepository> {
        PreferencesRepositoryImpl(get())
    }
}