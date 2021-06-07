package com.thugrzz.mypetapp.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey
    @ColumnInfo(name = "note_id")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "note_title")
    val title: String,

    @ColumnInfo(name = "note_description")
    val description: String,

    @ColumnInfo(name = "note_date")
    val date: String,

    @ColumnInfo(name = "note_time")
    val time: Time,

    @ColumnInfo(name = "note_type")
    val type: NoteType,

    @ColumnInfo(name = "isChecked")
    val isChecked: Boolean,

    @ColumnInfo(name = "note_periodicity")
    val notePeriodicity: NotePeriodicity
)