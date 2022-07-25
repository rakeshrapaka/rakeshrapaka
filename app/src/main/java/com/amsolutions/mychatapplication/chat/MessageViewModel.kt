package com.amsolutions.mychatapplication.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.amsolutions.mychatapplication.data.model.Message
import com.amsolutions.mychatapplication.data.model.User
import com.amsolutions.mychatapplication.data.repository.MessageRepository
import com.amsolutions.mychatapplication.data.repository.UserRepository

class MessageViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = MessageRepository(app)
    private val allMessages = repository.getAllmessages()

    fun insert(message:Message) {
        repository.insert(message)
    }

    fun update(message:Message) {
        repository.update(message)
    }

    fun delete(message:Message) {
        repository.deleteMessage(message)
    }

    fun getAllMessages(): LiveData<List<Message>> {
       return allMessages
    }

}