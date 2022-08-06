package com.hogent.pandora.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hogent.pandora.data.post.Post
import com.hogent.pandora.data.user.User
import com.hogent.pandora.data.user.UserDao
import com.hogent.pandora.utils.Converters
import com.hogent.pandora.utils.sha256
import kotlinx.coroutines.runBlocking
import java.time.LocalDate


@Database(entities = [User::class, Post::class], version = 1, exportSchema = false)
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

                populateDb(instance)

                INSTANCE = instance
                return instance
            }
        }

        private fun populateDb(instance: PandoraDatabase) {
            val userDao = instance.userDao()

            // Clean db
            userDao.nukeUsers()
            userDao.nukePosts()

            val userNonAdmin = User(
                0,
                "nonadmin",
                "nonadmin@gmail.com",
                "nonadmin123".sha256(),
                LocalDate.now(),
                false
            )
            val userAdmin = User(
                0,
                "admin",
                "admin@gmail.com",
                "admin123".sha256(),
                LocalDate.now(),
                true
            )
            userDao.addUser(userNonAdmin)
            userDao.addUser(userAdmin)

            val user1 = userDao.getByUsername("admin")
            val user2 = userDao.getByUsername("nonadmin")

            val post1 = Post(
                0,
                user1.userId,
                "Prepopulated content for this post!",
                listOf(user2.userId, user1.userId),
                false,
                LocalDate.now()
            )

            val post2 = Post(
                0,
                user2.userId,
                "Prepopulated content for this post!",
                listOf(user1.userId),
                false,
                LocalDate.now()
            )

            userDao.addPost(post1)
            userDao.addPost(post1)
            userDao.addPost(post1)
            userDao.addPost(post1)
            userDao.addPost(post1)
            userDao.addPost(post2)
        }
    }
}