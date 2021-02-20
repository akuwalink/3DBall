package com.akuwalink.ball.logic.dao

import androidx.room.*
import com.akuwalink.ball.logic.model.User

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User):Long

    @Update
    fun updataUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("select * from User where id =:userId")
    fun loadUserForId(userId:Int):User

    @Query("select * from User where name=:name")
    fun loadUserForName(name:String):User
}