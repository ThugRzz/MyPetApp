package com.thugrzz.mypetapp.data.repository

import android.net.Uri
import com.thugrzz.mypetapp.data.model.local.PetStatus
import com.thugrzz.mypetapp.data.model.local.Sex
import com.thugrzz.mypetapp.data.model.remote.PetBreed
import com.thugrzz.mypetapp.data.model.remote.PetType
import com.thugrzz.mypetapp.data.model.remote.Reference
import com.thugrzz.mypetapp.data.request.*
import com.thugrzz.mypetapp.data.response.*
import com.thugrzz.mypetapp.data.source.ContentHelper
import com.thugrzz.mypetapp.data.source.network.NetworkDataSource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.URLEncoder

class NetworkRepositoryImpl(
    private val networkDataSource: NetworkDataSource,
    private val contentHelper: ContentHelper
) : NetworkRepository {

    override suspend fun register(
        email: String,
        password: String,
        phone: String,
        ownerName: String,
        petName: String,
        petAge: String,
        petType: Long,
        breed: Long
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

    override suspend fun getFoodReferences(): List<Reference> {
        return networkDataSource.getFoods().data
    }

    override suspend fun getCareReferences(): List<Reference> {
        return networkDataSource.getCares().data
    }

    override suspend fun getDiseaseReferences(): List<Reference> {
        return networkDataSource.getDiseases().data
    }

    override suspend fun getTrainingReferences(): List<Reference> {
        return networkDataSource.getTrainings().data
    }

    override suspend fun getPetBreeds(): List<PetBreed> {
        return networkDataSource.getPetBreeds().data
    }

    override suspend fun getPetTypes(): List<PetType> {
        return networkDataSource.getPetTypes().data
    }

    override suspend fun getPetProfile(): PetProfileResponse {
        return networkDataSource.getPetProfile().data
    }

    override suspend fun savePetProfile(
        petName: String,
        petTypeId: Long,
        breedId: Long,
        sex: Sex,
        status: PetStatus,
        height: Double,
        weight: Double
    ) {
        val request = PetProfileRequest(
            petName, petTypeId, breedId, sex, status, height, weight
        )
        val isOk = networkDataSource.savePetProfile(request).status
        if (!isOk) {
            throw Throwable()
        }
    }

    override suspend fun getUserProfile(): UserProfileResponse {
        return networkDataSource.getUserProfile().data
    }

    override suspend fun saveUserProfile(
        name: String,
        email: String,
        phone: String,
        address: String,
    ) {
        val request = UserProfileRequest(
            name, email, phone, address
        )
        val isOk = networkDataSource.saveUserProfile(request).status
        if (!isOk) throw Throwable()
    }

    override suspend fun savePassword(password: String) {
        val request = PasswordRequest(password)
        val isOk = networkDataSource.savePassword(request).status
        if (!isOk) throw Throwable()
    }

    override suspend fun getAvatar(): AvatarResponse {
        val response = networkDataSource.getAvatar()
        return if (!response.status) throw Throwable() else response.data
    }

    override suspend fun saveAvatar(uri: Uri): AvatarResponse {
        val fileName = contentHelper.getFileName(uri)
        val mimeType = contentHelper.getMimeType(uri)
        contentHelper.openInputStream(uri).use { inputStream ->
            val fileType = mimeType.toMediaTypeOrNull()
            val bytes = inputStream.readBytes()
            val fileBody = bytes.toRequestBody(fileType, 0, bytes.size)
            val encodedFileName = URLEncoder.encode(fileName, FILE_NAME_ENCODING)
            val filePart =
                MultipartBody.Part.createFormData(FILE_PART_NAME, encodedFileName, fileBody)
            val response = networkDataSource.saveAvatar(filePart)
            return if (!response.status) throw Throwable() else response.data
        }
    }

    private companion object {

        private const val FILE_NAME_ENCODING = "utf-8"
        private const val FILE_PART_NAME = "photo"
    }
}