package data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(val name:String, @PrimaryKey val mobileNum: Long, @PrimaryKey(autoGenerate = false) val id: Int? = null)
