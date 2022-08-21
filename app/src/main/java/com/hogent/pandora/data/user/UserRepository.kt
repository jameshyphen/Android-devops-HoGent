package com.hogent.pandora.data.user

import androidx.lifecycle.LiveData
import com.hogent.pandora.data.post.Post
import com.hogent.pandora.data.post.PostComment
import com.hogent.pandora.data.post.PostWithComments
import com.hogent.pandora.data.post.UserWithPosts

class UserRepository(private val userDao: UserDao) {
    val readAllData: LiveData<List<User>> = userDao.readAllData()

    val readUsersWithPosts: LiveData<List<UserWithPosts>> = userDao.readUsersWithPosts()
    val readPostWithComments: LiveData<List<PostWithComments>> = userDao.readPostWithComments()
    fun readPostWithCommentsByPostId(postId: Int): LiveData<List<PostWithComments>> =
        userDao.readPostWithCommentsByPostId(postId)

    fun addUser(user: User): Long {
        return userDao.addUser(user)
    }

    fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    fun login(username: String, passwordSha256: String): User {
        return userDao.login(username, passwordSha256)
    }

    fun isTaken(username: String): Boolean {
        return userDao.isTaken(username)
    }

    fun addPost(post: Post): Long {
        return userDao.addPost(post)
    }

    fun addComment(comment: PostComment): Long {
        return userDao.addComment(comment)
    }

    fun updatePost(post: Post) {
        return userDao.updatePost(post)
    }

    fun updateComment(comment: PostComment) {
        return userDao.updateComment(comment)

    }
}