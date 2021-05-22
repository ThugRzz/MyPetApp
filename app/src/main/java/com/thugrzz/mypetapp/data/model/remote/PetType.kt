package com.thugrzz.mypetapp.data.model.remote

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "pet_type")
data class PetType(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("ID") val id: Long,

    @ColumnInfo(name = "type")
    @SerializedName("pet_type") val type: String
)