package com.amsolutions.mychatapplication.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.amsolutions.mychatapplication.data.local.ChatDatabase
import com.amsolutions.mychatapplication.data.local.GroupsDao
import com.amsolutions.mychatapplication.data.model.Groups
import com.amsolutions.mychatapplication.util.subscribeOnBackground

class GroupsRepository (application: Application) {
    private  var groupDao: GroupsDao
    private  var allGroups: LiveData<List<Groups>>

    private val database = ChatDatabase.getInstance(application)

    init {
        groupDao = database.groupsDao()
        allGroups = groupDao.getAllGroups()
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

    fun deleteUser(id:Int) {
        subscribeOnBackground {
            groupDao.deleteGroup(id)
        }
    }

    fun getAllUsers(): LiveData<List<Groups>> {
        return allGroups
    }



}