package com.thugrzz.mypetapp.data.model.local

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.thugrzz.mypetapp.R

enum class NoteType(
    @DrawableRes val iconRes: Int,
    @ColorRes val colorRes: Int
) {
    FEED(
        iconRes = R.mipmap.onboarding_fourth,
        colorRes = R.color.purple_A349A3
    ),
    CARE(
        iconRes = R.mipmap.onboarding_first,
        colorRes = R.color.blue_43C6D0
    ),
    ENTERTAINMENT(
        iconRes = R.mipmap.onboarding_third,
        colorRes = R.color.green_ABCF15
    ),
    OTHER(
        iconRes = R.mipmap.onboarding_second,
        colorRes = R.color.orange_FE9900
    )
}