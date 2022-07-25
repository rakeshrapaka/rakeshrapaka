package com.amsolutions.mychatapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.amsolutions.mychatapplication.data.model.Groups
import com.amsolutions.mychatapplication.data.model.Message
import com.amsolutions.mychatapplication.data.model.User
import com.amsolutions.mychatapplication.util.subscribeOnBackground

@Database(entities = [User::class,Groups::class,Message::class], version = 1)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun groupsDao(): GroupsDao
    abstract fun messagesDao():MessageDao

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
            val groupDao = db.groupsDao()
            subscribeOnBackground {
                userDao.insert(User("Rakesh R", "rak@home.com","3214567rh1"))
                userDao.insert(User("Sweta R", "swe@home.com","3214567rh2"))
                userDao.insert(User("Aadriti R", "Aad@home.com","3214567rh3"))
                userDao.insert(User("Mokshith R", "Mok@home.com","3214567rh4"))

                groupDao.insert(Groups("Family","3214567rh1","3214567rh1groupf1"))
                groupDao.insert(Groups("Family","3214567rh2","3214567rh1groupf1"))
                groupDao.insert(Groups("Family","3214567rh3","3214567rh1groupf1"))
                groupDao.insert(Groups("Family","3214567rh4","3214567rh1groupf1"))


            }
        }
    }

}