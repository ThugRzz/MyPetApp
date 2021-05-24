package com.thugrzz.mypetapp.data.source.network

import com.thugrzz.mypetapp.data.model.remote.FoodReference
import com.thugrzz.mypetapp.data.model.remote.PetBreed
import com.thugrzz.mypetapp.data.model.remote.PetType
import com.thugrzz.mypetapp.data.request.*
import com.thugrzz.mypetapp.data.response.*
import okhttp3.MultipartBody
import retrofit2.http.*

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

    @GET("user/pet")
    suspend fun getPetProfile(): BaseResponse<PetProfileResponse>

    @POST("user/pet/edit")
    suspend fun savePetProfile(
        @Body request: PetProfileRequest
    ): BaseResponse<PetProfileResponse>

    @GET("user/profile")
    suspend fun getUserProfile(): BaseResponse<UserProfileResponse>

    @POST("user/profile/edit")
    suspend fun saveUserProfile(
        @Body request: UserProfileRequest
    ): BaseResponse<UserProfileResponse>

    @POST("user/profile/password")
    suspend fun savePassword(
        @Body request: PasswordRequest
    ): BaseResponse<PasswordRequest>

    @GET("user/avatar")
    suspend fun getAvatar(): BaseResponse<AvatarResponse>

    @Multipart
    @POST("user/avatar/upload")
    suspend fun saveAvatar(@Part body: MultipartBody.Part): BaseResponse<AvatarResponse>
}