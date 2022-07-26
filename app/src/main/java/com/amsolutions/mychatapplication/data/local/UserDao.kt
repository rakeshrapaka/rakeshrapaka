package com.amsolutions.mychatapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.amsolutions.mychatapplication.data.model.User

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("delete from user_table where id = :id")
    fun deleteUser(id:Int)

    @Query("delete from user_table ")
    fun deleteAllUser()

    @Query("select * from user_table order by id")
    fun getAllUsers(): LiveData<List<User>>

    @Query("select * from user_table order by id")
    fun getAllUsersForGroupCreation(): List<User>
}