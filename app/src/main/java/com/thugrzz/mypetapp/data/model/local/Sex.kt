package com.thugrzz.mypetapp.data.model.local

import androidx.annotation.StringRes
import com.google.gson.annotations.SerializedName
import com.thugrzz.mypetapp.R

enum class Sex(@StringRes val titleId: Int) {
    @SerializedName("1")
    WOMAN(R.string.sex_woman),

    @SerializedName("2")
    MAN(R.string.sex_man)
}