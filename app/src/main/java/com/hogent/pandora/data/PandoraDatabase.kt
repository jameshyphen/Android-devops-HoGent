package com.hogent.pandora.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hogent.pandora.data.user.User
import com.hogent.pandora.data.user.UserDao
import com.hogent.pandora.utils.Converters

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PandoraDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        // Singleton
        @Volatile
        private var INSTANCE: PandoraDatabase? = null

        fun getDatabase(context: Context): PandoraDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PandoraDatabase::class.java,
                    "user_database"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}