package com.amsolutions.mychatapplication.data.model

import android.provider.ContactsContract
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class User{
    var name:String? = null
    var email: String? = null
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var uid: String? = null
    @Ignore
    var isSelected: Boolean = false
        get() = field
        set(value) {
            field = value
        }

    constructor(name:String?,email: String?, uid:String?){
        this.name = name
        this.email = email
        this.uid = uid
    }

}
