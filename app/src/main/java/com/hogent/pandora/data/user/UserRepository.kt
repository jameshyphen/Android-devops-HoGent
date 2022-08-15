package com.hogent.pandora.data.user

import androidx.lifecycle.LiveData
import com.hogent.pandora.data.post.Post
import com.hogent.pandora.data.post.UserWithPosts

class UserRepository(private val userDao: UserDao) {
    val readAllData: LiveData<List<User>> = userDao.readAllData()

    val readUsersWithPosts: LiveData<List<UserWithPosts>> = userDao.readUsersWithPosts()

    fun addUser(user: User) {
        userDao.addUser(user)
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

    fun addPost(post: Post) {
        return userDao.addPost(post)
    }

    fun updatePost(post: Post){
        return userDao.updatePost(post)
    }
}