package com.hogent.pandora.data.user

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hogent.pandora.data.post.Post
import com.hogent.pandora.data.post.UserWithPosts

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: User)

    @Update
    fun updateUser(user: User)

    @Query("SELECT * FROM user ORDER BY userId ASC")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT EXISTS(SELECT * FROM user WHERE userName=:userName)")
    fun isTaken(userName: String): Boolean

    @Query("SELECT * FROM user WHERE userName=:userName")
    fun getByUsername(userName: String): User

    @Query("SELECT * FROM user WHERE userName=:userName AND password=:password")
    fun login(userName: String, password: String): User

    @Transaction
    @Query("SELECT * FROM user ORDER BY userId ASC")
    fun readUsersWithPosts(): LiveData<List<UserWithPosts>>

    @Transaction
    @Insert
    fun addPost(post: Post)

    @Query("DELETE FROM user")
    fun nukeUsers()

    @Query("DELETE FROM post")
    fun nukePosts()
}