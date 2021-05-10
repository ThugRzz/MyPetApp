package com.thugrzz.mypetapp.features.schedule.add_schedule

import androidx.lifecycle.viewModelScope
import com.thugrzz.mypetapp.arch.BaseViewModel
import com.thugrzz.mypetapp.data.model.local.Note
import com.thugrzz.mypetapp.data.model.local.NotePeriodicity
import com.thugrzz.mypetapp.data.model.local.NoteType
import com.thugrzz.mypetapp.data.model.local.Time
import com.thugrzz.mypetapp.data.repository.DatabaseRepository
import com.thugrzz.mypetapp.data.repository.NetworkRepository
import com.thugrzz.mypetapp.data.repository.PreferencesRepository
import com.thugrzz.mypetapp.util.DateUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddScheduleViewModel(
    private val networkRepository: NetworkRepository,
    private val preferencesRepository: PreferencesRepository,
    private val databaseRepository: DatabaseRepository
) : BaseViewModel() {

    private val innerTitleFlow = MutableStateFlow("")
    val titleFlow: Flow<String>
        get() = innerTitleFlow

    private val innerDescriptionFlow = MutableStateFlow("")
    val descriptionFlow: Flow<String>
        get() = innerDescriptionFlow

    private val innerDateFlow = MutableStateFlow(0L)
    val dateFlow: Flow<Long>
        get() = innerDateFlow

    private val innerTimeFlow = MutableStateFlow(Time(0, 0))
    val timeFlow: Flow<Time>
        get() = innerTimeFlow

    private val innerNoteTypeFlow = MutableStateFlow(NoteType.FEED)
    val noteTypeFlow: Flow<NoteType>
        get() = innerNoteTypeFlow

    private val innerNotePeriodicityFlow = MutableStateFlow(NotePeriodicity.ONCE)
    val notePeriodicityFlow: Flow<NotePeriodicity>
        get() = innerNotePeriodicityFlow

    fun onTitleChange(text: String) = viewModelScope.launch {
        innerTitleFlow.emit(text)
    }

    fun onDescriptionChange(text: String) = viewModelScope.launch {
        innerDescriptionFlow.emit(text)
    }

    fun onDateChange(millis: Long) = viewModelScope.launch {
        innerDateFlow.emit(millis)
    }

    fun onTimeChange(time: Time) = viewModelScope.launch {
        innerTimeFlow.emit(time)
    }

    fun onNoteTypeChange(noteType: NoteType) = viewModelScope.launch {
        innerNoteTypeFlow.emit(noteType)
    }

    fun onNotePeriodicityChange(notePeriodicity: NotePeriodicity) = viewModelScope.launch {
        innerNotePeriodicityFlow.emit(notePeriodicity)
    }

    fun addNote() = viewModelScope.launch(Dispatchers.IO) {
        val title = innerTitleFlow.value
        val description = innerDescriptionFlow.value
        val date = innerDateFlow.value
        val time = innerTimeFlow.value
        val noteType = innerNoteTypeFlow.value
        val notePeriodicity = innerNotePeriodicityFlow.value
        val stringDate = DateUtil.getStringDate(date)
        val note = Note(
            title = title,
            description = description,
            date = stringDate,
            time = time,
            type = noteType,
            isChecked = false,
            notePeriodicity = notePeriodicity
        )
        databaseRepository.addNote(note)
    }
}