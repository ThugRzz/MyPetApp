package com.thugrzz.mypetapp.util

import com.thugrzz.mypetapp.data.model.local.DayOfWeek
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private val locale = Locale.getDefault()

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", locale)

    fun getCurrentDayInMillis(): String {
        val todayDateInMillis = System.currentTimeMillis()
        return dateFormat.format(todayDateInMillis)
    }

    fun getStringDate(millis: Long): String {
        return dateFormat.format(millis)
    }

    fun getDayOfWeek(stringDate: String): DayOfWeek {
        val date = dateFormat.parse(stringDate)
        val calendar = Calendar.getInstance()
        calendar.time = date ?: Date()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return DayOfWeek.values()[dayOfWeek - 1]
    }

    fun getDayOfMonth(stringDate: String): Int {
        val date = dateFormat.parse(stringDate)
        val calendar = Calendar.getInstance()
        calendar.time = date ?: Date()
        return calendar.get(Calendar.DAY_OF_MONTH)
    }
}