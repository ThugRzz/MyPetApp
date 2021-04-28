package com.thugrzz.mypetapp.data.repository

import com.thugrzz.mypetapp.data.source.preferences.PreferencesDataSource

class PreferencesRepositoryImpl(
    private val preferencesDataSource: PreferencesDataSource,
) : PreferencesRepository {

    override fun getToken() = preferencesDataSource.token

    override fun setToken(token: String) {
        preferencesDataSource.setToken(token)
    }
}