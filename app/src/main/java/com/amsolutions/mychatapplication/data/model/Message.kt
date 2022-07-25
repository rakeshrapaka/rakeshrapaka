package com.amsolutions.mychatapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_table")
class Message {
    var message: String? = null
    var senderId: String? = null
    var senderName: String? =null
    var groupId: String? = null
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    constructor(message: String?,senderName:String?,senderId:String?,groupId:String?){
        this.message = message
        this.senderId = senderId
        this.senderName = senderName
        this.groupId = groupId
    }
}