package com.amsolutions.mychatapplication.groups

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.amsolutions.mychatapplication.data.model.Groups
import com.amsolutions.mychatapplication.data.model.User
import com.amsolutions.mychatapplication.data.repository.GroupsRepository
import com.amsolutions.mychatapplication.data.repository.UserRepository

class GroupViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = GroupsRepository(app)

    private val allUsers = repository.getAllUsers()

    private val allGroups = repository.getAllGroups()

    fun insert(group: Groups) {
        repository.insert(group)
    }

    fun update(group: Groups) {
        repository.update(group)
    }

    fun delete(id: Int) {
        repository.deleteGroup(id)
    }

    fun deleteAllUsers() {
        repository.deleteAllGroups()
    }

    fun getAllGroups(): LiveData<List<Groups>> {
        return allGroups
    }

    fun getAllUsers(): LiveData<List<User>> {
        return allUsers
    }


}