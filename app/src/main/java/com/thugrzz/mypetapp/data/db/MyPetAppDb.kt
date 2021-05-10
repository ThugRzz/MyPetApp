package com.thugrzz.mypetapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thugrzz.mypetapp.data.db.dao.NotesDao
import com.thugrzz.mypetapp.data.model.local.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MyPetAppDb : RoomDatabase() {

    abstract fun getNotesDao(): NotesDao

    companion object {

        @Volatile
        private var INSTANCE: MyPetAppDb? = null

        fun getInstance(context: Context): MyPetAppDb =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MyPetAppDb::class.java, "MyPetApp.db"
            ).build()
    }
}