package com.thugrzz.mypetapp.data.model.local

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class OnboardingItem(
    val title: String,
    val description: String,
    @DrawableRes val iconRes: Int,
    @ColorRes val backgroundColor: Int,
    val pageNumber: Int
)
