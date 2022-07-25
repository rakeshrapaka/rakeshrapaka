package com.amsolutions.mychatapplication.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.amsolutions.mychatapplication.data.local.ChatDatabase
import com.amsolutions.mychatapplication.data.local.UserDao
import com.amsolutions.mychatapplication.data.model.User
import com.amsolutions.mychatapplication.util.subscribeOnBackground

class UserRepository(application: Application) {
    private  var userDao: UserDao
    private  var allUsers: LiveData<List<User>>

    private val database = ChatDatabase.getInstance(application)

    init {
        userDao = database.userDao()
        allUsers = userDao.getAllUsers()
    }

    fun insert(user: User) {
        subscribeOnBackground {
            userDao.insert(user)
        }
    }

    fun update(user: User) {
        subscribeOnBackground {
            userDao.update(user)
        }
    }

    fun deleteUser(id:Int) {
        subscribeOnBackground {
            userDao.deleteUser(id)
        }
    }

    fun deleteAllUsers() {
        subscribeOnBackground {
            userDao.deleteAllUser()
        }
    }

    fun getAllUsers(): LiveData<List<User>> {
        return allUsers
    }

    fun getAllUsersForGroup(): List<User>{
        return userDao.getAllUsersForGroupCreation()
    }


}