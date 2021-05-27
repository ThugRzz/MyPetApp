package com.thugrzz.mypetapp.data.model.local

import android.os.Parcelable
import com.thugrzz.mypetapp.data.model.remote.PetBreed
import com.thugrzz.mypetapp.data.model.remote.PetType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReferenceItem(
    val id: Long,
    val breedType: PetBreed?,
    val petType: PetType?,
    val title: String,
    val stepTitle: String,
    val description: String,
    val imageUrl: String
) : Parcelable