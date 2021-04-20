package com.thugrzz.mypetapp

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.thugrzz.mypetapp.di.dataSourceModule
import com.thugrzz.mypetapp.di.networkModule
import com.thugrzz.mypetapp.di.repositoryModule
import com.thugrzz.mypetapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application(), LifecycleObserver {
    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        startKoin {
            androidContext(this@App)
            modules(networkModule, viewModelModule, repositoryModule, dataSourceModule)
        }
    }
}