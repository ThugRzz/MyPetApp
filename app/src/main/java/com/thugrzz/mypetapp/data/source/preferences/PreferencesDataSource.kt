package com.thugrzz.mypetapp.data.source.preferences

interface PreferencesDataSource {

    val token: String

    fun setToken(token: String)
}