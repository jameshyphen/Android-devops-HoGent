package com.hogent.pandora.data.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hogent.pandora.data.PandoraDatabase
import com.hogent.pandora.data.post.UserWithPosts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    val readAllUsers: LiveData<List<User>>
    val readUsersWithPosts: LiveData<List<UserWithPosts>>

    private val repository: UserRepository

    init {
        val userDao = PandoraDatabase.getDatabase(application).userDao()

        repository = UserRepository(userDao)
        readAllUsers = repository.readAllData
        readUsersWithPosts = repository.readUsersWithPosts
    }


    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

    fun login(username: String, passwordSha256: String): User {
        return repository.login(username, passwordSha256)
    }

    fun isTaken(username: String): Boolean {
        return repository.isTaken(username)
    }
}