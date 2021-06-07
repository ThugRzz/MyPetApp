package com.thugrzz.mypetapp.data.source.preferences

interface PreferencesDataSource {

    val userId:Long

    val token: String

    fun setToken(token: String)

    fun setUserId(id:Long)
}