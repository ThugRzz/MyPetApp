package com.thugrzz.mypetapp.util

import com.thugrzz.mypetapp.data.model.local.Time
import java.util.concurrent.TimeUnit

object TimeUtil {

    fun getTimeFromMillis(millis: Long): Time {
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        val minutesAll = TimeUnit.MILLISECONDS.toMinutes(millis)
        val minutesRemainder = minutesAll - TimeUnit.HOURS.toMinutes(hours)
        return Time(hours.toInt(), minutesRemainder.toInt())
    }
}