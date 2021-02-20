package com.akuwalink.ball.logic.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.akuwalink.ball.logic.model.User

@Database(version = 1,entities = [User::class])
abstract class DataBase :RoomDatabase(){
    abstract  fun userDao():UserDao

    companion object{
        private  var instance:DataBase?=null

        @Synchronized
        fun getDataBase(context: Context):DataBase{
            instance?.let { return it }
            return Room.databaseBuilder(context.applicationContext,DataBase::class.java,"user_database").build().apply { instance=this }
        }
    }

}