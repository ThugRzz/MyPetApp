package com.thugrzz.mypetapp.di

import com.thugrzz.mypetapp.data.source.network.NetworkDataSource
import org.koin.dsl.module
import retrofit2.Retrofit

val dataSourceModule = module {

    factory<NetworkDataSource> {
        get<Retrofit>().create(NetworkDataSource::class.java)
    }
}