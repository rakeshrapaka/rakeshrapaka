package com.amsolutions.mychatapplication.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.amsolutions.mychatapplication.data.local.ChatDatabase
import com.amsolutions.mychatapplication.data.local.GroupsDao
import com.amsolutions.mychatapplication.data.model.Groups
import com.amsolutions.mychatapplication.data.model.User
import com.amsolutions.mychatapplication.util.subscribeOnBackground

class GroupsRepository (application: Application) {
    private  var groupDao: GroupsDao
    private  var allGroups: LiveData<List<Groups>>
    private  var allUsers: LiveData<List<User>>

    private val database = ChatDatabase.getInstance(application)

    init {
        groupDao = database.groupsDao()
        allGroups = groupDao.getAllGroups()
        allUsers = groupDao.getAllUsersForGroupCreation()
    }

    fun insert(group: Groups) {
        subscribeOnBackground {
            groupDao.insert(group)
        }
    }

    fun update(group: Groups) {
        subscribeOnBackground {
            groupDao.update(group)
        }
    }

    fun deleteGroup(id:Int) {
        subscribeOnBackground {
            groupDao.deleteGroup(id)
        }
    }

    fun deleteAllGroups() {
        subscribeOnBackground {
            groupDao.deleteAllGroups()
        }
    }

    fun getAllGroups(): LiveData<List<Groups>> {
        return allGroups
    }

    fun getAllUsers(): LiveData<List<User>> {
        return allUsers
    }


}