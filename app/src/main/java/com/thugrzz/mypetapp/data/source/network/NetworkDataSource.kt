package com.thugrzz.mypetapp.data.source.network

import com.thugrzz.mypetapp.data.request.AuthRequest
import com.thugrzz.mypetapp.data.response.AuthResponse
import com.thugrzz.mypetapp.data.response.BaseResponse
import com.thugrzz.mypetapp.data.response.ImgResponse
import retrofit2.http.*

interface NetworkDataSource {

    @POST("user/login")
    suspend fun login(@Body request: AuthRequest): BaseResponse<AuthResponse>

    @GET("load/{id}")
    suspend fun img(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: String
    ): ImgResponse
}