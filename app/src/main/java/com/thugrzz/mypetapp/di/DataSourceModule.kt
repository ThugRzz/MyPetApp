package com.thugrzz.mypetapp.di

import com.thugrzz.mypetapp.data.source.ContentHelper
import com.thugrzz.mypetapp.data.source.network.NetworkDataSource
import com.thugrzz.mypetapp.data.source.preferences.PreferencesDataSource
import com.thugrzz.mypetapp.data.source.preferences.PreferencesDataSourceImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val dataSourceModule = module {

    factory<NetworkDataSource> {
        get<Retrofit>().create(NetworkDataSource::class.java)
    }

    factory<PreferencesDataSource> {
        PreferencesDataSourceImpl(androidApplication())
    }

    factory {
        ContentHelper(androidContext())
    }
}