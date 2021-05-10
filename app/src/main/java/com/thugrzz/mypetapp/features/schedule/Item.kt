package com.thugrzz.mypetapp.features.schedule

import com.thugrzz.mypetapp.data.model.local.Note

sealed class Item {

    data class NoteItem(
        val note: Note
    ) : Item()

    data class DailyNote(
        val note: Note
    ) : Item()

    data class WeeklyNote(
        val note: Note
    ) : Item()

    data class MonthlyNotes(
        val note: Note
    ) : Item()

    object Divider : Item()
}