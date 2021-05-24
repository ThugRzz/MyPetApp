package com.thugrzz.mypetapp.features.profile

import androidx.lifecycle.viewModelScope
import com.thugrzz.mypetapp.arch.BaseViewModel
import com.thugrzz.mypetapp.data.model.remote.PetBreed
import com.thugrzz.mypetapp.data.model.remote.PetType
import com.thugrzz.mypetapp.data.repository.DatabaseRepository
import com.thugrzz.mypetapp.data.repository.NetworkRepository
import com.thugrzz.mypetapp.data.response.PetProfileResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository
) : BaseViewModel() {

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