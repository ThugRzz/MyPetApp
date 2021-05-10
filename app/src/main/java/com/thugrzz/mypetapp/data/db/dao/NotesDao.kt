package com.thugrzz.mypetapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thugrzz.mypetapp.data.model.local.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note: Note)

    @Query("SELECT * FROM notes")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE note_date = :currentDate")
    fun getTodayNotes(currentDate: String): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE note_periodicity = 1")
    fun getDailyNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE note_periodicity = 2")
    fun getWeeklyNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE note_periodicity = 3")
    fun getMonthlyNotes(): Flow<List<Note>>
}