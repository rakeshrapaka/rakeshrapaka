package com.amsolutions.mychatapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.amsolutions.mychatapplication.data.model.Groups
import com.amsolutions.mychatapplication.data.model.User

@Dao
interface GroupsDao {
    @Insert
    fun insert(group: Groups)

    @Update
    fun update(group: Groups)

    @Delete
    fun delete(group: Groups)

    @Query("delete from groups_table  where id = :grpID")
    fun deleteGroup(grpID:Int)

    @Query("delete from groups_table")
    fun deleteAllGroups()

    @Query("select * from groups_table order by id")
    fun getAllGroups(): LiveData<List<Groups>>

    @Query("select * from user_table order by id")
    fun getAllUsersForGroupCreation(): LiveData<List<User>>
}