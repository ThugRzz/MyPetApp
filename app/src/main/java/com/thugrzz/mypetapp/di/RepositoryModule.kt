package com.thugrzz.mypetapp.di

import com.thugrzz.mypetapp.data.repository.NetworkRepository
import com.thugrzz.mypetapp.data.repository.NetworkRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<NetworkRepository> {
        NetworkRepositoryImpl(get(), get())
    }
}