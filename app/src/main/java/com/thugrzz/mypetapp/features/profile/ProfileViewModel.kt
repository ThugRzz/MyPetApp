package com.thugrzz.mypetapp.features.profile

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.thugrzz.mypetapp.arch.BaseViewModel
import com.thugrzz.mypetapp.data.model.local.CropAvatar
import com.thugrzz.mypetapp.data.model.remote.PetBreed
import com.thugrzz.mypetapp.data.model.remote.PetType
import com.thugrzz.mypetapp.data.repository.DatabaseRepository
import com.thugrzz.mypetapp.data.repository.NetworkRepository
import com.thugrzz.mypetapp.data.response.PetProfileResponse
import com.thugrzz.mypetapp.data.source.ContentHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
    private val contentHelper: ContentHelper
) : BaseViewModel() {

    private var cameraFileUri: Uri? = null
    private var croppedFileUri: Uri? = null
    private var croppedContentUri: Uri? = null

    private val innerStartCameraActionsFlow = MutableSharedFlow<Uri>()
    val startCameraActionsFlow: Flow<Uri>
        get() = innerStartCameraActionsFlow

    private val innerCropImageActionFlow = MutableSharedFlow<CropAvatar>()
    val cropImageActionFlow: Flow<CropAvatar>
        get() = innerCropImageActionFlow

    private val innerAvatarUrlFlow = MutableStateFlow("")
    val avatarUrlFlow = innerAvatarUrlFlow.mapNotNull { it }

    private val innerPetTypesFlow = databaseRepository.getAllPetTypes()
        .toStateFlow(emptyList())
    private val innerPetBreedsFlow = databaseRepository.getAllPetBreeds()
        .toStateFlow(emptyList())

    private val petProfileFlow = MutableStateFlow<PetProfileResponse?>(null)

    val petNameFlow = petProfileFlow.mapNotNull { it?.petName }
    val petSexFlow = petProfileFlow.mapNotNull { it?.sex }
    val petStatusFlow = petProfileFlow.mapNotNull { it?.status }
    val petHeightFlow = petProfileFlow.mapNotNull { it?.height }
    val petWeightFlow = petProfileFlow.mapNotNull { it?.weight }
    val petTypeFlow = petProfileFlow.mapNotNull { getPetType(it?.petTypeId) }
    val petBreedFlow = petProfileFlow.mapNotNull { getPetBreed(it?.breedId) }

    private val innerIsLoadingFlow = MutableStateFlow(false)
    val isLoadingFlow: Flow<Boolean>
        get() = innerIsLoadingFlow

    private val innerErrorActionFlow = MutableSharedFlow<Throwable>()
    val errorActionFlow: Flow<Throwable>
        get() = innerErrorActionFlow

    init {
        loadPetProfile()
        loadAvatar()
    }

    fun changeAvatar() = viewModelScope.launch {
        innerIsLoadingFlow.emit(true)
        try {
            croppedContentUri?.let {
                val avatarUrl = networkRepository.saveAvatar(it)
                innerAvatarUrlFlow.emit(avatarUrl.avatarUrl)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            innerErrorActionFlow.emit(e)
        } finally {
            innerIsLoadingFlow.emit(false)
        }
    }

    fun onCameraSourceChosen() {
        viewModelScope.launch {
            val cameraUri = withContext(Dispatchers.IO) {
                contentHelper.createAvatarCameraTempFile()
            }
            val croppedUri = withContext(Dispatchers.IO) {
                contentHelper.createAvatarCameraTempFile()
            }
            val croppedPathUri = contentHelper.getFilePathUri(croppedUri)
            cameraFileUri = cameraUri
            croppedContentUri = croppedUri
            croppedFileUri = croppedPathUri
            innerStartCameraActionsFlow.emit(cameraUri)
        }
    }

    fun onCameraSuccess() {
        cameraFileUri?.let { uri ->
            croppedFileUri?.let { croppedUri ->
                viewModelScope.launch {
                    val cropAvatar = CropAvatar(sourceImageUri = uri, croppedImageUri = croppedUri)
                    innerCropImageActionFlow.emit(cropAvatar)
                }
                cameraFileUri = null
            }
        }
    }

    fun onFilePicked(uri: Uri) {
        val croppedUri = contentHelper.createAvatarCameraTempFile()
        val croppedPathUri = contentHelper.getFilePathUri(croppedUri)
        croppedFileUri = croppedPathUri
        croppedContentUri = croppedUri
        val cropAvatar = CropAvatar(sourceImageUri = uri, croppedImageUri = croppedPathUri)
        viewModelScope.launch {
            innerCropImageActionFlow.emit(cropAvatar)
        }
    }

     fun loadAvatar() = viewModelScope.launch {
        innerIsLoadingFlow.emit(true)
        try {
            val avatar = networkRepository.getAvatar()
            innerAvatarUrlFlow.emit(avatar.avatarUrl)
        } catch (e: Throwable) {
            innerErrorActionFlow.emit(e)
        } finally {
            innerIsLoadingFlow.emit(false)
        }
    }

    private fun getPetType(typeId: Long?): PetType? {
        val types = innerPetTypesFlow.value
        return types.firstOrNull { it.id == typeId }
    }

    private fun getPetBreed(breedId: Long?): PetBreed? {
        val breeds = innerPetBreedsFlow.value
        return breeds.firstOrNull { it.id == breedId }
    }

    private fun loadPetProfile() = viewModelScope.launch {
        innerIsLoadingFlow.emit(true)
        try {
            val petProfile = networkRepository.getPetProfile()
            petProfileFlow.emit(petProfile)

        } catch (e: Throwable) {
            innerErrorActionFlow.emit(e)
        } finally {
            innerIsLoadingFlow.emit(false)
        }
    }
}