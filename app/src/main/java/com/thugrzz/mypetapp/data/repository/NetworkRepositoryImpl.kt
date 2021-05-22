package com.thugrzz.mypetapp.data.repository

import com.thugrzz.mypetapp.data.model.remote.FoodReference
import com.thugrzz.mypetapp.data.model.remote.PetBreed
import com.thugrzz.mypetapp.data.model.remote.PetType
import com.thugrzz.mypetapp.data.request.AuthRequest
import com.thugrzz.mypetapp.data.request.RegisterRequest
import com.thugrzz.mypetapp.data.response.AuthResponse
import com.thugrzz.mypetapp.data.response.ImgResponse
import com.thugrzz.mypetapp.data.source.network.NetworkDataSource

class NetworkRepositoryImpl(
    private val networkDataSource: NetworkDataSource,
) : NetworkRepository {

    override suspend fun register(
        email: String,
        password: String,
        phone: String,
        ownerName: String,
        petName: String,
        petAge: String,
        petType: Int,
        breed: Int
    ) {
        val request = RegisterRequest(
            email = email,
            password = password,
            phone = phone,
            ownerName = ownerName,
            petName = petName,
            petAge = petAge,
            petType = petType,
            breed = breed
        )
        networkDataSource.register(request)
    }

    override suspend fun login(email: String, password: String): AuthResponse {
        val request = AuthRequest(email, password)
        return networkDataSource.login(request).data
    }

    override suspend fun img(): ImgResponse {
        return networkDataSource.img("care.png")
    }

    override suspend fun getFoodReferences(): List<FoodReference> {
        return networkDataSource.getFoods().data
    }

    override suspend fun getPetBreeds(): List<PetBreed> {
        return networkDataSource.getPetBreeds().data
    }

    override suspend fun getPetTypes(): List<PetType> {
        return networkDataSource.getPetTypes().data
    }
}