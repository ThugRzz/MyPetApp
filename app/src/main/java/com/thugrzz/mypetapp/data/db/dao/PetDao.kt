package com.thugrzz.mypetapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thugrzz.mypetapp.data.model.remote.PetBreed
import com.thugrzz.mypetapp.data.model.remote.PetType
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPetTypes(types: List<PetType>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPetBreeds(breeds: List<PetBreed>)

    @Query("SELECT * FROM pet_type")
    fun getAllPetTypes(): Flow<List<PetType>>

    @Query("SELECT * FROM pet_breed")
    fun getAllPetBreeds(): Flow<List<PetBreed>>
}