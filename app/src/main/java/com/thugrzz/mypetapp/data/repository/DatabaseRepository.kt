package com.thugrzz.mypetapp.data.repository

import com.thugrzz.mypetapp.data.model.local.Note
import com.thugrzz.mypetapp.data.model.remote.PetBreed
import com.thugrzz.mypetapp.data.model.remote.PetType
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    fun addNote(note: Note)

    fun getNotes(): Flow<List<Note>>

    fun getTodayNotes(currentDate: String): Flow<List<Note>>

    fun getDailyNotes(): Flow<List<Note>>

    fun getWeeklyNotes(): Flow<List<Note>>

    fun getMonthlyNotes(): Flow<List<Note>>

    fun addPetTypes(types: List<PetType>)

    fun addPetBreeds(breeds: List<PetBreed>)

    fun getAllPetTypes(): Flow<List<PetType>>

    fun getAllPetBreeds(): Flow<List<PetBreed>>
}