package com.hogent.pandora.data.user

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hogent.pandora.data.post.Post
import com.hogent.pandora.data.post.PostComment
import com.hogent.pandora.data.post.PostWithComments
import com.hogent.pandora.data.post.UserWithPosts

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: User): Long

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
    fun addPost(post: Post): Long

    @Transaction
    @Update
    fun updatePost(post: Post)

    @Query("DELETE FROM user")
    fun nukeUsers()

    @Query("DELETE FROM post")
    fun nukePosts()

    @Query("DELETE FROM postcomment")
    fun nukeComments()

    @Transaction
    @Query("SELECT * FROM post ORDER BY postId ASC")
    fun readPostWithComments(): LiveData<List<PostWithComments>>

    @Transaction
    @Insert
    fun addComment(comment: PostComment): Long
}