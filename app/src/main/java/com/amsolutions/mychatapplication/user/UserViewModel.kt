package com.amsolutions.mychatapplication.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.amsolutions.mychatapplication.data.model.User
import com.amsolutions.mychatapplication.data.repository.UserRepository

class UserViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = UserRepository(app)
    private val allUsers = repository.getAllUsers()

    fun insert(user: User) {
        repository.insert(user)
    }

    fun update(user: User) {
        repository.update(user)
    }

    fun delete(id: Int) {
        repository.deleteUser(id)
    }

    fun deleteAllUsers() {
        repository.deleteAllUsers()
    }

    fun getAllUsers(): LiveData<List<User>> {
        return allUsers
    }


}