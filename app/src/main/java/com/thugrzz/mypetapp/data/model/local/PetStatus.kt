package com.thugrzz.mypetapp.data.model.local

import androidx.annotation.StringRes
import com.google.gson.annotations.SerializedName
import com.thugrzz.mypetapp.R

enum class PetStatus(@StringRes val titleId: Int) {

    @SerializedName("1")
    CASTRATED(R.string.status_castrated),

    @SerializedName("2")
    UNCASTRATED(R.string.status_uncastrated),

    @SerializedName("3")
    STERILIZED(R.string.status_sterilized),

    @SerializedName("4")
    UNSTERILIZED(R.string.status_unsterilized)
}