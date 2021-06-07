package com.thugrzz.mypetapp.data.model.local

import androidx.annotation.StringRes
import com.thugrzz.mypetapp.R

enum class NotePeriodicity(
    @StringRes val titleRes: Int
) {
    ONCE(R.string.periodicity_once),
    DAILY(R.string.periodicity_daily),
    WEEKLY(R.string.periodicity_weekly),
    MONTHLY(R.string.periodicity_monthly)
}