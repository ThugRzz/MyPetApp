package com.thugrzz.mypetapp.data.repository

import com.thugrzz.mypetapp.data.model.remote.FoodReference
import com.thugrzz.mypetapp.data.model.remote.PetBreed
import com.thugrzz.mypetapp.data.model.remote.PetType
import com.thugrzz.mypetapp.data.response.AuthResponse
import com.thugrzz.mypetapp.data.response.ImgResponse

interface NetworkRepository {

    suspend fun register(
        email: String,
        password: String,
        phone:String,
        ownerName:String,
        petName:String,
        petAge:String,
        petType:Int,
        breed:Int
    )

    suspend fun login(email: String, password: String): AuthResponse

    suspend fun img(): ImgResponse

    suspend fun getFoodReferences(): List<FoodReference>

    suspend fun getPetTypes(): List<PetType>

    suspend fun getPetBreeds(): List<PetBreed>
}