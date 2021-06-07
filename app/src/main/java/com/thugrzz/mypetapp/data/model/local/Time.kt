package com.thugrzz.mypetapp.data.model.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.concurrent.TimeUnit

@Parcelize
data class Time(
    val hours: Int,
    val minutes: Int
) : Parcelable {

    fun toMillis() =
        TimeUnit.HOURS.toMillis(this.hours.toLong()) + TimeUnit.MINUTES.toMinutes(this.minutes.toLong())
}
