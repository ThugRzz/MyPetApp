package com.thugrzz.mypetapp.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*

abstract class BaseViewModel : ViewModel() {

    protected fun <T> Flow<T>.toStateFlow(defaultValue: T): StateFlow<T> =
        stateIn(viewModelScope, SharingStarted.Eagerly, defaultValue)

    protected fun <T> Flow<T>.toSharedFlow(): SharedFlow<T> =
        shareIn(viewModelScope, SharingStarted.Eagerly, replay = 0)
}