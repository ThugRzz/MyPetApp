package com.thugrzz.mypetapp.features.profile.profile_edit

import androidx.lifecycle.viewModelScope
import com.thugrzz.mypetapp.arch.BaseViewModel
import com.thugrzz.mypetapp.data.model.local.PetStatus
import com.thugrzz.mypetapp.data.model.local.Sex
import com.thugrzz.mypetapp.data.model.remote.PetBreed
import com.thugrzz.mypetapp.data.model.remote.PetType
import com.thugrzz.mypetapp.data.repository.DatabaseRepository
import com.thugrzz.mypetapp.data.repository.NetworkRepository
import com.thugrzz.mypetapp.data.validation.AcceptableValue
import com.thugrzz.mypetapp.data.validation.Acceptance
import com.thugrzz.mypetapp.data.validation.Validators
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileEditViewModel(
    private val databaseRepository: DatabaseRepository,
    private val networkRepository: NetworkRepository
) : BaseViewModel() {

    private val innerSelectedTypeFlow = MutableStateFlow<PetType?>(null)
    val selectedTypeFlow = innerSelectedTypeFlow.map { it }

    private val innerSelectedBreedFlow = MutableStateFlow<PetBreed?>(null)
    val selectedBreedFlow = innerSelectedBreedFlow.map { it }

    private val innerPetTypesFlow = databaseRepository.getAllPetTypes()
        .toStateFlow(emptyList())
    val petTypesFlow = innerPetTypesFlow

    private val innerPetBreedsFlow = databaseRepository.getAllPetBreeds()
        .toStateFlow(emptyList())
    val petBreedsFlow = innerSelectedTypeFlow.combine(innerPetBreedsFlow, ::getBreeds)

    private val innerPetNameFlow = MutableStateFlow("")
    val petNameFlow = innerPetNameFlow.drop(1)
        .map(Validators::validateName)
        .onStart { emit(AcceptableValue("", Acceptance.ACCEPTED)) }

    private val innerPetHeightFlow = MutableStateFlow("")
    val petHeightFlow: Flow<String>
        get() = innerPetHeightFlow

    private val innerPetWeightFlow = MutableStateFlow("")
    val petWeightFlow: Flow<String>
        get() = innerPetWeightFlow

    private val innerSexFlow = MutableStateFlow<Sex?>(null)
    val sexFlow: Flow<Sex?>
        get() = innerSexFlow

    private val innerStatusFlow = MutableStateFlow<PetStatus?>(null)
    val statusFlow: Flow<PetStatus?>
        get() = innerStatusFlow

    private val innerIsLoadingFlow = MutableStateFlow(false)
    val isLoadingFlow: Flow<Boolean>
        get() = innerIsLoadingFlow

    private val innerErrorActionFlow = MutableSharedFlow<Throwable>()
    val errorActionFlow: Flow<Throwable>
        get() = innerErrorActionFlow

    private val innerSuccessActionFlow = MutableSharedFlow<Unit>()
    val successActionFlow: Flow<Unit>
        get() = innerSuccessActionFlow

    private val innerEmptyDataActionFlow = MutableSharedFlow<Unit>()
    val emptyDataActionFlow: Flow<Unit>
        get() = innerEmptyDataActionFlow

    init {
        loadPetProfile()
        savePetProfile()
    }

    fun onSexChange(sex: Sex) = viewModelScope.launch {
        innerSexFlow.emit(sex)
    }

    fun onStatusChange(status: PetStatus) = viewModelScope.launch {
        innerStatusFlow.emit(status)
    }

    fun onPetNameChange(text: String) = viewModelScope.launch {
        innerPetNameFlow.emit(text)
    }

    fun onPetHeightChange(text: String) = viewModelScope.launch {
        innerPetHeightFlow.emit(text)
    }

    fun onPetWeightChange(text: String) = viewModelScope.launch {
        innerPetWeightFlow.emit(text)
    }

    fun onPetTypeClick(type: PetType) = viewModelScope.launch {
        innerSelectedTypeFlow.emit(type)
    }

    fun onPetBreedClick(breed: PetBreed) = viewModelScope.launch {
        innerSelectedBreedFlow.emit(breed)
    }

    private fun loadPetProfile() = viewModelScope.launch {
        innerIsLoadingFlow.emit(true)
        try {
            val petProfile = networkRepository.getPetProfile()
            val types = innerPetTypesFlow.value
            val breeds = innerPetBreedsFlow.value
            val type = types.firstOrNull { it.id == petProfile.petTypeId }
            val breed = breeds.firstOrNull { it.id == petProfile.breedId }
            innerPetNameFlow.emit(petProfile.petName)
            innerSelectedTypeFlow.emit(type)
            innerSelectedBreedFlow.emit(breed)
            innerSexFlow.emit(petProfile.sex)
            innerStatusFlow.emit(petProfile.status)
            innerPetHeightFlow.emit(petProfile.height.toString())
            innerPetWeightFlow.emit(petProfile.weight.toString())
        } catch (e: Throwable) {
            innerErrorActionFlow.emit(e)
        } finally {
            innerIsLoadingFlow.emit(false)
        }
    }

    fun savePetProfile() = viewModelScope.launch {
        innerIsLoadingFlow.emit(true)
        try {
            val petName = innerPetNameFlow.value
            val petType = innerSelectedTypeFlow.value
            val breed = innerSelectedBreedFlow.value
            val sex = innerSexFlow.value
            val status = innerStatusFlow.value
            val height = innerPetHeightFlow.value
            val weight = innerPetWeightFlow.value
            if (petName.isEmpty() || petType == null || breed == null || sex == null || status == null || height.isEmpty() || weight.isEmpty()) {
                innerEmptyDataActionFlow.emit(Unit)
            } else {
                networkRepository.savePetProfile(
                    petName = petName,
                    petTypeId = petType.id,
                    breedId = breed.id,
                    sex = sex,
                    status = status,
                    height = height.toDouble(),
                    weight = weight.toDouble()
                )
                innerSuccessActionFlow.emit(Unit)
            }
        } catch (e: Throwable) {
            innerErrorActionFlow.emit(e)
        } finally {
            innerIsLoadingFlow.emit(false)
        }
    }

    private fun getBreeds(type: PetType?, breeds: List<PetBreed>): List<PetBreed> =
        when (type) {
            null -> emptyList()
            else -> {
                breeds.filter { it.petType == type.id }
            }
        }

}