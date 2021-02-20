package com.akuwalink.ball

import android.app.Application
import android.content.Context
import com.akuwalink.ball.logic.model.User

class MyApplication :Application(){
    companion object{
        lateinit var context:Context
        lateinit var now_login:User
    }

    override fun onCreate() {
        super.onCreate()
        context =applicationContext
    }
}