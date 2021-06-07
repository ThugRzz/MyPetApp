package com.thugrzz.mypetapp.data.repository

interface PreferencesRepository {

    fun getToken(): String

    fun setToken(token: String)

    fun getUserId(): Long

    fun setUserId(id: Long)
}