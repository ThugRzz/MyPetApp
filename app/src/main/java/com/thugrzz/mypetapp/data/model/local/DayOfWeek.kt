package com.thugrzz.mypetapp.data.model.local

import androidx.annotation.StringRes
import com.thugrzz.mypetapp.R

enum class DayOfWeek(
    @StringRes val titleRes: Int
) {
    SUNDAY(R.string.day_sunday),
    MONDAY(R.string.day_monday),
    TUESDAY(R.string.day_tuesday),
    WEDNESDAY(R.string.day_wednesday),
    THURSDAY(R.string.day_thursday),
    FRIDAY(R.string.day_friday),
    SATURDAY(R.string.day_saturday)
}