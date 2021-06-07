package com.thugrzz.mypetapp.data.model.remote

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "pet_type")
@Parcelize
data class PetType(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("ID") val id: Long,

    @ColumnInfo(name = "type")
    @SerializedName("pet_type") val type: String
) : Parcelable