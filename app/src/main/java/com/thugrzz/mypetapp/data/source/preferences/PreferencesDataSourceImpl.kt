package com.thugrzz.mypetapp.data.source.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferencesDataSourceImpl(
    context: Context,
) : PreferencesDataSource {

    private val dataPreferences: SharedPreferences =
        context.getSharedPreferences(DATA_PREF_FILE_NAME, Context.MODE_PRIVATE)

    override val token: String
        get() = dataPreferences.getString(KEY_BEARER_TOKEN, "")!!

    override fun setToken(token: String) =
        dataPreferences.edit().putString(KEY_BEARER_TOKEN, token).apply()

    companion object {
        private const val DATA_PREF_FILE_NAME = "data_preferences.txt"

        private const val KEY_BEARER_TOKEN = "bearer_token"
    }
}