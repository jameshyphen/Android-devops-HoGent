package com.hogent.pandora.data.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY userId ASC")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT EXISTS(SELECT * FROM user_table WHERE userName=:userName)")
    fun isTaken(userName: String): Boolean

    @Query("SELECT * FROM user_table WHERE userName=:userName AND password=:password")
    fun login(userName: String, password: String): User
}