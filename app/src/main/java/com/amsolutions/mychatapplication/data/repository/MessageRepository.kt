package com.amsolutions.mychatapplication.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.amsolutions.mychatapplication.data.local.ChatDatabase
import com.amsolutions.mychatapplication.data.local.GroupsDao
import com.amsolutions.mychatapplication.data.local.MessageDao
import com.amsolutions.mychatapplication.data.model.Groups
import com.amsolutions.mychatapplication.data.model.Message
import com.amsolutions.mychatapplication.util.subscribeOnBackground

class MessageRepository (application: Application) {
    private  var messageDao: MessageDao
    private  var allUserMessages: LiveData<List<Message>>

    private val database = ChatDatabase.getInstance(application)

    init {
        messageDao = database.messagesDao()
        allUserMessages = messageDao.getAllMessages()
    }

    fun insert(message: Message) {
        subscribeOnBackground {
            messageDao.insert(message)
        }
    }

    fun update(message: Message) {
        subscribeOnBackground {
            messageDao.update(message)
        }
    }

    fun deleteMessage(message: Message) {
        subscribeOnBackground {
            messageDao.delete(message)
        }
    }

    fun getAllmessages(): LiveData<List<Message>> {
        return allUserMessages
    }

}