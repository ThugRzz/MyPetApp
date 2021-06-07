package com.thugrzz.mypetapp.data.model.local

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ReferenceData(
    val id: Int,
    @StringRes val titleResId: Int,
    @DrawableRes val iconResId: Int
)