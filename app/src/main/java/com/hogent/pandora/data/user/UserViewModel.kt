package com.hogent.pandora.data.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hogent.pandora.data.PandoraDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<User>>
    private val repository: UserRepository

    init {
        val userDao = PandoraDatabase.getDatabase(application).userDao()

        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(user)
        }
    }

    fun login(username: String, passwordSha256: String): User{
        return repository.login(username, passwordSha256)
    }

    fun isTaken(username: String): Boolean{
        return repository.isTaken(username)
    }
}