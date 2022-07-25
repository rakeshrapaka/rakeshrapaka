package com.amsolutions.mychatapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.amsolutions.mychatapplication.data.model.Message

@Dao
interface MessageDao {
    @Insert
    fun insert(message: Message)

    @Update
    fun update(message: Message)

    @Delete
    fun delete(message: Message)

    @Query("select * from message_table where groupId = :groupId order by id")
    fun getAllGroupMessages(groupId:Int): LiveData<List<Message>>

    @Query("select * from message_table order by id")
    fun getAllMessages(): LiveData<List<Message>>
}