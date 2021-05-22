package com.thugrzz.mypetapp.data.model.remote

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "pet_breed")
data class PetBreed(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("ID")
    val id: Long,

    @ColumnInfo(name = "name")
    @SerializedName("breed_name")
    val name: String,

    @ColumnInfo(name = "type")
    @SerializedName("pet_type") val petType: Long
)