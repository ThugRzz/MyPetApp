package com.thugrzz.mypetapp.data.repository

import android.net.Uri
import com.thugrzz.mypetapp.data.model.local.PetStatus
import com.thugrzz.mypetapp.data.model.local.Sex
import com.thugrzz.mypetapp.data.model.remote.PetBreed
import com.thugrzz.mypetapp.data.model.remote.PetType
import com.thugrzz.mypetapp.data.model.remote.Reference
import com.thugrzz.mypetapp.data.response.*

interface NetworkRepository {

    suspend fun register(
        email: String,
        password: String,
        phone: String,
        ownerName: String,
        petName: String,
        petAge: String,
        petType: Long,
        breed: Long
    )

    suspend fun login(email: String, password: String): AuthResponse

    suspend fun img(): ImgResponse

    suspend fun getFoodReferences(): List<Reference>

    suspend fun getCareReferences(): List<Reference>

    suspend fun getDiseaseReferences(): List<Reference>

    suspend fun getTrainingReferences(): List<Reference>

    suspend fun getPetTypes(): List<PetType>

    suspend fun getPetBreeds(): List<PetBreed>

    suspend fun getPetProfile(): PetProfileResponse

    suspend fun savePetProfile(
        petName: String,
        petTypeId: Long,
        breedId: Long,
        sex: Sex,
        status: PetStatus,
        height: Double,
        weight: Double
    )

    suspend fun getUserProfile(): UserProfileResponse

    suspend fun saveUserProfile(
        name: String,
        email: String,
        phone: String,
        address: String
    )

    suspend fun savePassword(
        password: String
    )

    suspend fun getAvatar(): AvatarResponse

    suspend fun saveAvatar(uri: Uri): AvatarResponse
}