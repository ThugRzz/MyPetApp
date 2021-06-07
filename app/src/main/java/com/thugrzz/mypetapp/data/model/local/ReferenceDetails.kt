package com.thugrzz.mypetapp.data.model.local

import android.os.Parcelable
import com.thugrzz.mypetapp.data.model.remote.PetBreed
import com.thugrzz.mypetapp.data.model.remote.PetType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReferenceDetails(
    val title: String,
    val petType: PetType?,
    val breed: PetBreed?,
    val referenceSteps: List<ReferenceItem>
) : Parcelable