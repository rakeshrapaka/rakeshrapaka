package data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import data.model.Groups
import data.model.User

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

    @Query("select * from groups_table order by id")
    fun getAllGroups(): LiveData<List<Groups>>
}