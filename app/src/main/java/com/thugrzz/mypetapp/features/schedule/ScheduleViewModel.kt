package com.thugrzz.mypetapp.features.schedule

import androidx.lifecycle.viewModelScope
import com.thugrzz.mypetapp.arch.BaseViewModel
import com.thugrzz.mypetapp.data.model.local.Note
import com.thugrzz.mypetapp.data.repository.DatabaseRepository
import com.thugrzz.mypetapp.util.DateUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ScheduleViewModel(
    private val databaseRepository: DatabaseRepository
) : BaseViewModel() {

    private val refreshNotesActionFlow = MutableSharedFlow<Unit>(1)

    private val todayNotesFlow = databaseRepository.getTodayNotes(DateUtil.getCurrentDayInMillis())
        .map(::getTodayItemNotes)
    private val dailyNotesFlow = databaseRepository.getDailyNotes()
        .map(::getDailyItemNotes)
    private val weeklyNotesFlow = databaseRepository.getWeeklyNotes()
        .map(::getWeeklyItemNotes)
    private val monthlyNotesFlow = databaseRepository.getMonthlyNotes()
        .map(::getMonthlyItemNotes)

    private val innerSortFlow = MutableStateFlow(ScheduleSort.ONCE)
    val sortFlow: Flow<ScheduleSort>
        get() = innerSortFlow

    val notesFlow = combine(
        sortFlow,
        todayNotesFlow,
        dailyNotesFlow,
        weeklyNotesFlow,
        monthlyNotesFlow,
        ::getCurrentItemNotes
    )

    init {
        viewModelScope.launch { refreshNotesActionFlow.emit(Unit) }
    }

    fun onItemCheckedChange(
        item: Item.NoteItem,
        isChecked: Boolean
    ) = viewModelScope.launch(Dispatchers.IO) {
        val changedItem = item.note.copy(isChecked = isChecked)
        databaseRepository.addNote(changedItem)
        refreshNotesActionFlow.emit(Unit)
    }

    fun onSortSelected(sort: ScheduleSort) = viewModelScope.launch {
        innerSortFlow.emit(sort)
    }

    private fun getCurrentItemNotes(
        sort: ScheduleSort,
        todayNotes: List<Item>,
        dailyNotes: List<Item>,
        weeklyNotes: List<Item>,
        monthlyNotes: List<Item>,
    ) = when (sort) {
        ScheduleSort.ONCE -> todayNotes
        ScheduleSort.DAILY -> dailyNotes
        ScheduleSort.WEEKLY -> weeklyNotes
        ScheduleSort.MONTHLY -> monthlyNotes
    }

    private fun getDailyItemNotes(notes: List<Note>): List<Item> {
        val sortedNotes = notes.sortedBy { it.time.hours }
            .sortedBy { it.time.minutes }
        val items = mutableListOf<Item>()
        for (note in sortedNotes) {
            items.add(Item.DailyNote(note))
        }
        return items
    }

    private fun getWeeklyItemNotes(notes: List<Note>): List<Item> {
        val sortedNotes = notes.sortedBy { it.time.hours }
            .sortedBy { it.time.minutes }
        val items = mutableListOf<Item>()
        for (note in sortedNotes) {
            items.add(Item.WeeklyNote(note))
        }
        return items
    }

    private fun getMonthlyItemNotes(notes: List<Note>): List<Item> {
        val sortedNotes = notes.sortedBy { it.time.hours }
            .sortedBy { it.time.minutes }
        val items = mutableListOf<Item>()
        for (note in sortedNotes) {
            items.add(Item.MonthlyNotes(note))
        }
        return items
    }

    private fun getTodayItemNotes(notes: List<Note>): List<Item> {
        val sortedNotes =
            notes.sortedWith(compareBy(Note::isChecked)
                .thenBy { it.time.hours }
                .thenBy { it.time.minutes })
        val items = mutableListOf<Item>()
        var isDividerAdded = false
        for (note in sortedNotes) {
            if (!isDividerAdded && note.isChecked) {
                items.add(Item.Divider)
                isDividerAdded = true
            }
            items.add(Item.NoteItem(note))
        }
        return items
    }
}