package com.thugrzz.mypetapp.features.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thugrzz.mypetapp.R

enum class NavTab(
    @DrawableRes val iconId: Int,
    @StringRes val titleId: Int
) {
    SCHEDULE(
        iconId = R.drawable.ic_schedule,
        titleId = R.string.schedule
    ),
    QR_CODE(
        iconId = R.drawable.ic_qr_code,
        titleId = R.string.qr_code
    ),
    REFERENCE(
        iconId = R.drawable.ic_reference,
        titleId = R.string.reference
    ),
    PROFILE(
        iconId = R.drawable.ic_profile,
        titleId = R.string.profile
    )
}
