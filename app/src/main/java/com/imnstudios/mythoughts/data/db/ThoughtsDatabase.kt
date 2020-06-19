package com.imnstudios.mythoughts.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.imnstudios.mythoughts.data.db.entities.Thoughts

//Database Class
@Database(entities = [Thoughts::class], version = 1)
abstract class ThoughtsDatabase : RoomDatabase() {

    abstract fun thoughtsDao(): ThoughtsDao

    companion object {
        var instance: ThoughtsDatabase? = null
        fun getDatabase(context: Context): ThoughtsDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext, ThoughtsDatabase::class.java,
                    "thoughts_database"
                ).build()
            }
            return instance
        }
    }
}