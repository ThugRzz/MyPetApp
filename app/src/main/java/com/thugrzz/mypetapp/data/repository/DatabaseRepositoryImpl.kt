package com.thugrzz.mypetapp.data.repository

import android.content.Context
import com.thugrzz.mypetapp.data.db.MyPetAppDb
import com.thugrzz.mypetapp.data.model.local.Note
import kotlinx.coroutines.flow.Flow
import org.koin.core.KoinComponent

class DatabaseRepositoryImpl(
    context: Context
) : DatabaseRepository, KoinComponent {

    private var database: MyPetAppDb = MyPetAppDb.getInstance(context)

    override fun addNote(note: Note) {
        database.getNotesDao().addNote(note)
    }

    override fun getNotes(): Flow<List<Note>> =
        database.getNotesDao().getNotes()

    override fun getTodayNotes(currentDate: String): Flow<List<Note>> =
        database.getNotesDao().getTodayNotes(currentDate)

    override fun getDailyNotes(): Flow<List<Note>> =
        database.getNotesDao().getDailyNotes()

    override fun getWeeklyNotes(): Flow<List<Note>> =
        database.getNotesDao().getWeeklyNotes()

    override fun getMonthlyNotes(): Flow<List<Note>> =
        database.getNotesDao().getMonthlyNotes()
}