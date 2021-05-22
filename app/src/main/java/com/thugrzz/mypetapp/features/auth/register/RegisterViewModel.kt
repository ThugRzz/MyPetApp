package com.thugrzz.mypetapp.features.auth.register

import androidx.lifecycle.viewModelScope
import com.thugrzz.mypetapp.arch.BaseViewModel
import com.thugrzz.mypetapp.data.model.remote.PetBreed
import com.thugrzz.mypetapp.data.model.remote.PetType
import com.thugrzz.mypetapp.data.repository.DatabaseRepository
import com.thugrzz.mypetapp.data.repository.NetworkRepository
import com.thugrzz.mypetapp.data.repository.PreferencesRepository
import com.thugrzz.mypetapp.data.validation.AcceptableValue
import com.thugrzz.mypetapp.data.validation.Acceptance
import com.thugrzz.mypetapp.data.validation.Validators
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
    private val preferencesRepository: PreferencesRepository
) : BaseViewModel() {

    /**
     * First register fragment. User info
     */
    private val innerEmailFlow = MutableStateFlow("")
    val emailFlow = innerEmailFlow.drop(1)
        .map(Validators::validateEmail)
        .onStart { emit(AcceptableValue("", Acceptance.ACCEPTED)) }

    private val innerOwnerNameFlow = MutableStateFlow("")
    val ownerNameFlow = innerOwnerNameFlow.drop(1)
        .map(Validators::validateName)
        .onStart { emit(AcceptableValue("", Acceptance.ACCEPTED)) }

    private val innerPhoneFlow = MutableStateFlow("")
    val phoneFlow = innerPhoneFlow.drop(1)
        .map(Validators::validatePhone)
        .onStart { emit(AcceptableValue("", Acceptance.ACCEPTED)) }

    private val innerPasswordFlow = MutableStateFlow("")
    val passwordFlow = innerPasswordFlow.drop(1)
        .map(Validators::validatePassword)
        .onStart { emit(AcceptableValue("", Acceptance.ACCEPTED)) }

    val isNextButtonEnabledFlow = combine(
        emailFlow,
        ownerNameFlow,
        phoneFlow,
        passwordFlow,
        ::getButtonEnabled
    )

    /**
     * Second register fragment. Pet info
     */

    private val innerPetNameFlow = MutableStateFlow("")
    val petNameFlow = innerPetNameFlow.drop(1)
        .map(Validators::validateName)
        .onStart { emit(AcceptableValue("", Acceptance.ACCEPTED)) }

    private val innerPetAgeFlow = MutableStateFlow("")
    val petAgeFlow = innerPetAgeFlow

    private val selectedTypePositionFlow = MutableStateFlow(-1)

    private val selectedBreedPositionFlow = MutableStateFlow(-1)

    private val innerPetTypesFlow = databaseRepository.getAllPetTypes()
    val petTypesFlow = innerPetTypesFlow.map { it.map(PetType::type) }

    private val innerPetBreedsFlow = databaseRepository.getAllPetBreeds()
    val petBreedsFlow = selectedTypePositionFlow.combine(innerPetBreedsFlow, ::getBreeds)

    private val innerIsLoadingFlow = MutableStateFlow(false)
    val isLoadingFlow: Flow<Boolean>
        get() = innerIsLoadingFlow

    private val innerErrorActionFlow = MutableSharedFlow<Throwable>()
    val errorActionFlow: Flow<Throwable>
        get() = innerErrorActionFlow

    private val innerSuccessRegisterActionFlow = MutableSharedFlow<Unit>()
    val successRegisterActionFlow: Flow<Unit>
        get() = innerSuccessRegisterActionFlow

    val isRegisterButtonEnabledFlow = combine(
        petNameFlow,
        petAgeFlow,
        selectedTypePositionFlow,
        selectedBreedPositionFlow,
        ::getRegisterButtonEnabled
    )

    init {
        loadPetTypesAndBreeds()
    }

    fun registration() = viewModelScope.launch {
        innerIsLoadingFlow.emit(true)
        try {
            val email = innerEmailFlow.value
            val password = innerPasswordFlow.value
            val phone = innerPhoneFlow.value
            val ownerName = innerOwnerNameFlow.value
            val petName = innerPetNameFlow.value
            val petAge = innerPetAgeFlow.value
            val petType = selectedTypePositionFlow.value + 1
            val breed = selectedBreedPositionFlow.value + 1
            networkRepository.register(
                email = email,
                password = password,
                phone = phone,
                ownerName = ownerName,
                petName = petName,
                petAge = petAge,
                petType = petType,
                breed = breed
            )
            innerSuccessRegisterActionFlow.emit(Unit)
        } catch (e: Throwable) {
            innerErrorActionFlow.emit(e)
        } finally {
            innerIsLoadingFlow.emit(false)
        }
    }

    fun onEmailChange(text: String) = viewModelScope.launch {
        innerEmailFlow.emit(text)
    }

    fun onOwnerNameChange(text: String) = viewModelScope.launch {
        innerOwnerNameFlow.emit(text)
    }

    fun onPhoneChange(text: String) = viewModelScope.launch {
        innerPhoneFlow.emit(text)
    }

    fun onPasswordChange(text: String) = viewModelScope.launch {
        innerPasswordFlow.emit(text)
    }

    fun onPetNameChange(text: String) = viewModelScope.launch {
        innerPetNameFlow.emit(text)
    }

    fun onPetAgeChange(text: String) = viewModelScope.launch {
        innerPetAgeFlow.emit(text)
    }

    fun onPetTypeClick(position: Int) = viewModelScope.launch {
        selectedTypePositionFlow.emit(position)
    }

    fun onPetBreedClick(position: Int) = viewModelScope.launch {
        selectedBreedPositionFlow.emit(position)
    }

    private fun loadPetTypesAndBreeds() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val petTypes = networkRepository.getPetTypes()
            val petBreeds = networkRepository.getPetBreeds()
            databaseRepository.addPetBreeds(petBreeds)
            databaseRepository.addPetTypes(petTypes)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private fun getBreeds(typePosition: Int, breeds: List<PetBreed>): List<String> =
        when (typePosition) {
            -1 -> emptyList()
            else -> {
                val typeId: Long = typePosition.toLong() + 1
                breeds.filter { it.petType == typeId }.map { it.name }
            }
        }

    private fun getRegisterButtonEnabled(
        petName: AcceptableValue<String>,
        age: String,
        typePosition: Int,
        breedPosition: Int
    ): Boolean = petName.value.isNotEmpty() && petName.isAccepted && age.isNotEmpty()
            && (typePosition != -1) && (breedPosition != -1)

    private fun getButtonEnabled(
        email: AcceptableValue<String>,
        ownerName: AcceptableValue<String>,
        phone: AcceptableValue<String>,
        password: AcceptableValue<String>
    ): Boolean =
        email.value.isNotEmpty() && email.isAccepted && ownerName.value.isNotEmpty() && ownerName.isAccepted
                && phone.value.isNotEmpty() && phone.isAccepted && password.value.isNotEmpty() && password.isAccepted
}