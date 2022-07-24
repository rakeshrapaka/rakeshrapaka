package com.amsolutions.mychatapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.amsolutions.mychatapplication.data.model.Groups
import com.amsolutions.mychatapplication.data.model.User
import com.amsolutions.mychatapplication.util.subscribeOnBackground

@Database(entities = [User::class,Groups::class], version = 1)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun groupsDao(): GroupsDao

    companion object{
        private var instance: ChatDatabase?= null

        @Synchronized
        fun getInstance(ctx:Context):ChatDatabase{
            if(instance == null){
                instance = Room.databaseBuilder(ctx.applicationContext, ChatDatabase::class.java,
                    "chat_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance!!
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }
        private fun populateDatabase(db: ChatDatabase) {
            val userDao = db.userDao()
            subscribeOnBackground {
                userDao.insert(User("Rakesh R", 9963541791))
                userDao.insert(User("Sweta R", 987654321))
                userDao.insert(User("Aadriti R", 887654321))
                userDao.insert(User("Mokshith R", 787654321))

            }
        }
    }

}