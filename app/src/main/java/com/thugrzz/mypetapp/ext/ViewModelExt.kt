package com.thugrzz.mypetapp.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

inline fun <T> ViewModel.collect(
    source: Flow<T>,
    crossinline consumer: suspend (T) -> Unit
) {
    viewModelScope.launch {
        source.collect {
            consumer(it)
        }
    }
}