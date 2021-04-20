package com.thugrzz.mypetapp.data.source.network

import com.thugrzz.mypetapp.data.model.remote.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NetworkDataSource {

    @POST("api/user/new")
    fun login(@Body request: User): Call<Unit>
}