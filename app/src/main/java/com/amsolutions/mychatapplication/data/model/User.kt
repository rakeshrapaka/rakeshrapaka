package com.amsolutions.mychatapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(val name:String, val mobileNum: Long, @PrimaryKey(autoGenerate = true) val id: Int? = null)
