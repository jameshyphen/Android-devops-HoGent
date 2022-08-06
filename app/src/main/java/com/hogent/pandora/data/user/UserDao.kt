package com.hogent.pandora.data.user

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hogent.pandora.data.post.Post
import com.hogent.pandora.data.post.UserWithPosts

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

    @Transaction
    @Query("SELECT * FROM user_table ORDER BY userId ASC")
    fun readUsersWithPosts(): List<UserWithPosts>

    @Transaction
    @Insert
    fun addPost(post: Post?): Int
}