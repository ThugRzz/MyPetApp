package com.thugrzz.mypetapp.data.source.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferencesDataSourceImpl(
    context: Context,
) : PreferencesDataSource {

    private val dataPreferences: SharedPreferences =
        context.getSharedPreferences(DATA_PREF_FILE_NAME, Context.MODE_PRIVATE)

    override val userId: Long
        get() = dataPreferences.getLong(KEY_USER_ID, 0L)

    override val token: String
        get() = dataPreferences.getString(KEY_BEARER_TOKEN, "")!!

    override fun setToken(token: String) =
        dataPreferences.edit().putString(KEY_BEARER_TOKEN, token).apply()

    override fun setUserId(id: Long) =
        dataPreferences.edit().putLong(KEY_USER_ID, id).apply()

    companion object {
        private const val DATA_PREF_FILE_NAME = "data_preferences.txt"

        private const val KEY_BEARER_TOKEN = "bearer_token"
        private const val KEY_USER_ID = "user_id"
    }
}