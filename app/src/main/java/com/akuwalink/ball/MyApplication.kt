package com.akuwalink.ball

import android.app.Activity
import android.app.Application
import android.content.Context
import android.media.AudioAttributes
import com.akuwalink.ball.logic.dao.DataBase
import com.akuwalink.ball.logic.dao.UserDao
import com.akuwalink.ball.logic.model.User
import com.akuwalink.ball.util.MusicUtil

class MyApplication :Application(){
    companion object{
        lateinit var context:Context
        lateinit var now_user:User
        lateinit var soundPlay:MusicUtil
        lateinit var userDao: UserDao
        var contextList=ArrayList<Activity>()
        var win_flag=false
    }

    fun initMusic(){
        var audioAttributes=AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
        soundPlay.soundInit(10,audioAttributes)
        soundPlay.addSound("collision",R.raw.collide)
        soundPlay.addSound("touch_button",R.raw.touch_button)
        Thread.sleep(1000)
    }
    override fun onCreate() {
        super.onCreate()
        context =applicationContext
        soundPlay= MusicUtil(this)

        initMusic()
        userDao=DataBase.getDataBase(this).userDao()

    }
}