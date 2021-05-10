package com.thugrzz.mypetapp.data.db

import androidx.room.TypeConverter
import com.thugrzz.mypetapp.data.model.local.NotePeriodicity
import com.thugrzz.mypetapp.data.model.local.NoteType
import com.thugrzz.mypetapp.data.model.local.Time
import com.thugrzz.mypetapp.util.TimeUtil

class Converters {

    @TypeConverter
    fun fromNoteType(value: NoteType): Int = NoteType.values().indexOf(value)

    @TypeConverter
    fun toNoteType(value: Int) = NoteType.values()[value]

    @TypeConverter
    fun fromNotePeriodicity(value: NotePeriodicity): Int = NotePeriodicity.values().indexOf(value)

    @TypeConverter
    fun toNotePeriodicity(value: Int) = NotePeriodicity.values()[value]

    @TypeConverter
    fun fromTime(time: Time): Long = time.toMillis()

    @TypeConverter
    fun toTime(millis: Long): Time = TimeUtil.getTimeFromMillis(millis)
}