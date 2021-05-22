package com.thugrzz.mypetapp.data.source.network

import com.thugrzz.mypetapp.data.model.remote.FoodReference
import com.thugrzz.mypetapp.data.model.remote.PetBreed
import com.thugrzz.mypetapp.data.model.remote.PetType
import com.thugrzz.mypetapp.data.request.AuthRequest
import com.thugrzz.mypetapp.data.request.RegisterRequest
import com.thugrzz.mypetapp.data.response.AuthResponse
import com.thugrzz.mypetapp.data.response.BaseResponse
import com.thugrzz.mypetapp.data.response.ImgResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NetworkDataSource {

    @POST("user/new")
    suspend fun register(@Body request: RegisterRequest)

    @POST("user/login")
    suspend fun login(@Body request: AuthRequest): BaseResponse<AuthResponse>

    @GET("load/{id}")
    suspend fun img(
        @Path("id") id: String
    ): ImgResponse

    @GET("reference/foods")
    suspend fun getFoods(): BaseResponse<List<FoodReference>>

    @GET("pet/types")
    suspend fun getPetTypes(): BaseResponse<List<PetType>>

    @GET("pet/breeds")
    suspend fun getPetBreeds(): BaseResponse<List<PetBreed>>
}