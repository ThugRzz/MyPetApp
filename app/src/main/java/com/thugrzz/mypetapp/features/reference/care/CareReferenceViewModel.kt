package com.thugrzz.mypetapp.features.reference.care

import androidx.lifecycle.viewModelScope
import com.thugrzz.mypetapp.arch.BaseViewModel
import com.thugrzz.mypetapp.data.model.local.ReferenceDetails
import com.thugrzz.mypetapp.data.model.local.ReferenceItem
import com.thugrzz.mypetapp.data.model.remote.PetBreed
import com.thugrzz.mypetapp.data.model.remote.PetType
import com.thugrzz.mypetapp.data.model.remote.Reference
import com.thugrzz.mypetapp.data.repository.DatabaseRepository
import com.thugrzz.mypetapp.data.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CareReferenceViewModel(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository
) : BaseViewModel() {

    private val innerReferencesFlow = MutableStateFlow(emptyList<Reference>())
    val referencesFlow = innerReferencesFlow.map(::getReferenceItems)

    private val innerIsLoadingFlow = MutableStateFlow(false)
    val isLoadingFlow: Flow<Boolean>
        get() = innerIsLoadingFlow

    private val innerErrorActionFlow = MutableSharedFlow<Throwable>()
    val errorActionFlow: Flow<Throwable>
        get() = innerErrorActionFlow

    private val petTypesFlow = databaseRepository.getAllPetTypes()
        .toStateFlow(emptyList())
    private val breedsFlow = databaseRepository.getAllPetBreeds()
        .toStateFlow(emptyList())

    init {
        loadCareReferences()
    }

    private fun loadCareReferences() = viewModelScope.launch {
        innerIsLoadingFlow.emit(true)
        try {
            val references = networkRepository.getCareReferences()
            innerReferencesFlow.emit(references)
        } catch (e: Throwable) {
            innerErrorActionFlow.emit(e)
        } finally {
            innerIsLoadingFlow.emit(false)
        }
    }

    private fun getReferenceItems(references: List<Reference>): List<ReferenceDetails> {
        val groupedReferences = references.groupBy { it.breedType }
        val referencesDetails = mutableListOf<ReferenceDetails>()
        for ((breedId, items) in groupedReferences) {
            val petBreed = getPetBreed(breedId)
            val referenceDetails = createReferenceDetails(petBreed, items)
            if (referenceDetails != null) {
                referencesDetails.add(referenceDetails)
            }
        }
        return referencesDetails
    }

    private fun createReferenceDetails(
        breed: PetBreed?,
        items: List<Reference>
    ) = if (items.isNotEmpty()) {
        val firstItem = items.first()
        ReferenceDetails(
            title = firstItem.title,
            petType = getPetType(firstItem.petType),
            breed = breed,
            referenceSteps = items.map { createReferenceItem(it) }
        )
    } else {
        null
    }

    private fun createReferenceItem(reference: Reference) = ReferenceItem(
        id = reference.id,
        breedType = getPetBreed(reference.breedType),
        petType = getPetType(reference.petType),
        title = reference.title,
        stepTitle = reference.stepTitle,
        description = reference.description,
        imageUrl = reference.imageUrl
    )

    private fun getPetType(typeId: Long): PetType? {
        val types = petTypesFlow.value
        return types.firstOrNull { it.id == typeId }
    }

    private fun getPetBreed(breedId: Long): PetBreed? {
        val breeds = breedsFlow.value
        return breeds.firstOrNull { it.id == breedId }
    }
}