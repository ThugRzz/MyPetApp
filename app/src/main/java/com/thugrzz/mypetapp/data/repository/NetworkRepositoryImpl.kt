package com.thugrzz.mypetapp.data.repository

import com.google.gson.Gson
import com.thugrzz.mypetapp.data.model.remote.User
import com.thugrzz.mypetapp.data.source.network.NetworkDataSource

class NetworkRepositoryImpl(
    private val networkDataSource: NetworkDataSource,
    private val gson: Gson
) : NetworkRepository {

    override fun createUser() = networkDataSource.login(User(
        email = "test4@mail.ru",
        password = "secret",
        phone = "89101036629"
    ))
}