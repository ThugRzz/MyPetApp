package com.thugrzz.mypetapp.features.auth.authorization

import androidx.lifecycle.viewModelScope
import com.thugrzz.mypetapp.arch.BaseViewModel
import com.thugrzz.mypetapp.data.repository.NetworkRepository
import com.thugrzz.mypetapp.data.repository.PreferencesRepository
import com.thugrzz.mypetapp.data.validation.AcceptableValue
import com.thugrzz.mypetapp.data.validation.Validators
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AuthViewModel(
    private val networkRepository: NetworkRepository,
    private val preferencesRepository: PreferencesRepository,
) : BaseViewModel() {

    private val innerEmailFlow = MutableStateFlow("")
    val emailFlow = innerEmailFlow.map { Validators.validateEmail(it) }

    private val innerPasswordFlow = MutableStateFlow("")
    val passwordFlow = innerPasswordFlow.map { Validators.validatePassword(it) }

    private val innerIsLoadingFlow = MutableStateFlow(false)
    val isLoadingFlow: Flow<Boolean>
        get() = innerIsLoadingFlow

    private val innerErrorActionFlow = MutableSharedFlow<Throwable>()
    val errorActionFlow: Flow<Throwable>
        get() = innerErrorActionFlow

    private val innerSuccessAuthActionFlow = MutableSharedFlow<Unit>()
    val successAuthActionFlow: Flow<Unit>
        get() = innerSuccessAuthActionFlow

    val isActionButtonEnabledFlow = combine(emailFlow, passwordFlow, ::getButtonEnabled)

    fun login() = viewModelScope.launch {
        val email = innerEmailFlow.value
        val password = innerPasswordFlow.value
        innerIsLoadingFlow.emit(true)
        try {
            val authResponse = networkRepository.login(email, password)
            preferencesRepository.setToken(authResponse.token)
            preferencesRepository.setUserId(authResponse.userId)
            innerSuccessAuthActionFlow.emit(Unit)
        } catch (e: Throwable) {
            innerErrorActionFlow.emit(e)
        } finally {
            innerIsLoadingFlow.emit(false)
        }
    }

    fun onEmailInputChange(email: String) = viewModelScope.launch {
        innerEmailFlow.emit(email)
    }

    fun onPasswordInputChange(password: String) = viewModelScope.launch {
        innerPasswordFlow.emit(password)
    }

    private fun getButtonEnabled(
        emailAcceptable: AcceptableValue<String>,
        passwordAcceptable: AcceptableValue<String>
    ): Boolean {
        val emailStatus = emailAcceptable.status
        val passwordStatus = passwordAcceptable.status
        return emailStatus.isAccepted && passwordStatus.isAccepted
    }
}