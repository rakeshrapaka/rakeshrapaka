package data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import data.local.ChatDatabase
import data.local.UserDao
import data.model.User
import util.subscribeOnBackground

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

    fun getAllUsers(): LiveData<List<User>> {
        return allUsers
    }



}