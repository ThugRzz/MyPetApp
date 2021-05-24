package com.thugrzz.mypetapp.features.profile.profile_settings

import androidx.lifecycle.viewModelScope
import com.thugrzz.mypetapp.arch.BaseViewModel
import com.thugrzz.mypetapp.data.repository.NetworkRepository
import com.thugrzz.mypetapp.data.response.UserProfileResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

class ProfileSettingsViewModel(
    private val networkRepository: NetworkRepository
) : BaseViewModel() {

    private val userProfileFlow = MutableStateFlow<UserProfileResponse?>(null)

    val nameFlow = userProfileFlow.mapNotNull { it?.name }

    val emailFlow = userProfileFlow.mapNotNull { it?.email }

    val phoneFLow = userProfileFlow.mapNotNull { it?.phone }

    val addressFlow = userProfileFlow.mapNotNull { it?.address }

    private val innerIsLoadingFlow = MutableStateFlow(false)
    val isLoadingFlow: Flow<Boolean>
        get() = innerIsLoadingFlow

    private val innerErrorActionFlow = MutableSharedFlow<Throwable>()
    val errorActionFlow: Flow<Throwable>
        get() = innerErrorActionFlow

    private val innerSuccessActionFlow = MutableSharedFlow<Unit>()
    val successActionFlow: Flow<Unit>
        get() = innerSuccessActionFlow

    init {
        loadUserProfile()
    }

    fun saveName(name: String) = viewModelScope.launch {
        innerIsLoadingFlow.emit(true)
        try {
            val oldProfile = userProfileFlow.value
            oldProfile?.let {
                networkRepository.saveUserProfile(
                    name = name,
                    email = it.email,
                    phone = it.phone,
                    address = it.address
                )
            }
            val userProfile = networkRepository.getUserProfile()
            userProfileFlow.emit(userProfile)
            innerSuccessActionFlow.emit(Unit)
        } catch (e: Throwable) {
            innerErrorActionFlow.emit(e)
        } finally {
            innerIsLoadingFlow.emit(false)
        }
    }

    fun saveEmail(email: String) = viewModelScope.launch {
        innerIsLoadingFlow.emit(true)
        try {
            val oldProfile = userProfileFlow.value
            oldProfile?.let {
                networkRepository.saveUserProfile(
                    name = it.name,
                    email = email,
                    phone = it.phone,
                    address = it.address
                )
            }
            val userProfile = networkRepository.getUserProfile()
            userProfileFlow.emit(userProfile)
            innerSuccessActionFlow.emit(Unit)
        } catch (e: Throwable) {
            innerErrorActionFlow.emit(e)
        } finally {
            innerIsLoadingFlow.emit(false)
        }
    }

    fun savePhone(phone: String) = viewModelScope.launch {
        innerIsLoadingFlow.emit(true)
        try {
            val oldProfile = userProfileFlow.value
            oldProfile?.let {
                networkRepository.saveUserProfile(
                    name = it.name,
                    email = it.email,
                    phone = phone,
                    address = it.address
                )
            }
            val userProfile = networkRepository.getUserProfile()
            userProfileFlow.emit(userProfile)
            innerSuccessActionFlow.emit(Unit)
        } catch (e: Throwable) {
            innerErrorActionFlow.emit(e)
        } finally {
            innerIsLoadingFlow.emit(false)
        }
    }

    fun saveAddress(address: String) = viewModelScope.launch {
        innerIsLoadingFlow.emit(true)
        try {
            val oldProfile = userProfileFlow.value
            oldProfile?.let {
                networkRepository.saveUserProfile(
                    name = it.name,
                    email = it.email,
                    phone = it.phone,
                    address = address
                )
            }
            val userProfile = networkRepository.getUserProfile()
            userProfileFlow.emit(userProfile)
            innerSuccessActionFlow.emit(Unit)
        } catch (e: Throwable) {
            innerErrorActionFlow.emit(e)
        } finally {
            innerIsLoadingFlow.emit(false)
        }
    }

    fun savePassword(password: String) = viewModelScope.launch {
        innerIsLoadingFlow.emit(true)
        try {
            networkRepository.savePassword(password)
            val userProfile = networkRepository.getUserProfile()
            userProfileFlow.emit(userProfile)
            innerSuccessActionFlow.emit(Unit)
        } catch (e: Throwable) {
            innerErrorActionFlow.emit(e)
        } finally {
            innerIsLoadingFlow.emit(false)
        }
    }

    private fun loadUserProfile() = viewModelScope.launch {
        innerIsLoadingFlow.emit(true)
        try {
            val userProfile = networkRepository.getUserProfile()
            userProfileFlow.emit(userProfile)
        } catch (e: Throwable) {
            innerErrorActionFlow.emit(e)
        } finally {
            innerIsLoadingFlow.emit(false)
        }
    }
}