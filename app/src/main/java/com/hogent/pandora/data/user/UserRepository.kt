package com.hogent.pandora.data.user

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {
    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    fun login(username: String, passwordSha256: String): User{
        return userDao.login(username, passwordSha256)
    }

    fun isTaken(username: String): Boolean {
        return userDao.isTaken(username)
    }
}