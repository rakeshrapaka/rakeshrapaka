package data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups_table")
data class Groups (val groupName:String,val userID:Int,@PrimaryKey(autoGenerate = false) val id: Int? = null)