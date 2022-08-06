package com.hogent.pandora.data.user

import androidx.lifecycle.LiveData
import com.hogent.pandora.data.post.UserWithPosts

class UserRepository(private val userDao: UserDao) {
    val readAllData: LiveData<List<User>> = userDao.readAllData()

    val readUsersWithPosts: List<UserWithPosts> = userDao.readUsersWithPosts()

    fun addUser(user: User) {
        userDao.addUser(user)
    }

    fun login(username: String, passwordSha256: String): User {
        return userDao.login(username, passwordSha256)
    }

    fun isTaken(username: String): Boolean {
        return userDao.isTaken(username)
    }
}