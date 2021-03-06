package com.akuwalink.ball.ui.mainview

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.akuwalink.ball.MyApplication
import com.akuwalink.ball.MyApplication.Companion.contextList
import com.akuwalink.ball.MyApplication.Companion.now_user
import com.akuwalink.ball.MyApplication.Companion.soundPlay
import com.akuwalink.ball.MyApplication.Companion.userDao
import com.akuwalink.ball.R
import com.akuwalink.ball.logic.dao.DataBase
import com.akuwalink.ball.logic.dao.UserDao
import com.akuwalink.ball.logic.model.User
import com.akuwalink.ball.ui.gameview.MainSurfaceView
import kotlinx.android.synthetic.main.mainmenu.*
import kotlin.concurrent.thread

class MainMenu:AppCompatActivity(),View.OnClickListener{

    var _width=0
    var _height=0
    var appear_flag=false
    lateinit var mainSurfaceView: MainSurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.mainmenu)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        initLayout()
        addListener()
        val point= Point()
        display?.getRealSize(point)
        _width=point.x
        _height=point.y
        supportActionBar?.hide()
        window.navigationBarColor= Color.TRANSPARENT
        window.statusBarColor= Color.TRANSPARENT

        mainSurfaceView=MainSurfaceView(this)
        main_showball.addView(mainSurfaceView)
        setUser()
    }

    override fun onPause() {
        super.onPause()
        mainSurfaceView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    override fun onResume() {
        super.onResume()
        mainSurfaceView.onResume()
    }
    fun setUser(){
        main_name.setText(now_user.name)
        main_stat.setText("当前星数：" + now_user.star)
        main_now.setText("当前关卡：" + now_user.now)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus){
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }

    }
    fun initLayout(){
        var margin=main_random.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(0,50,0,0)
        margin=main_back.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(0,50,0,0)
        margin=main_skin.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(0,50,0,0)

        margin=main_name.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(0,100,0,0)
        margin=main_stat.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(0,50,0,0)
        margin=main_now.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(0,50,0,0)
        margin=main_back_to_login.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(0,50,0,0)
    }

    fun addListener(){
        main_head.setOnClickListener(this)
        main_start.setOnClickListener(this)
        main_random.setOnClickListener(this)
        main_back.setOnClickListener(this)
        main_skin.setOnClickListener(this)
        main_back_to_login.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.main_head->{
                if(appear_flag==false){
                    var anima_app=ObjectAnimator.ofFloat(main_name,"alpha",0F,1F)
                    anima_app.duration=500
                    anima_app.start()
                    anima_app=ObjectAnimator.ofFloat(main_stat,"alpha",0F,1F)
                    anima_app.duration=500
                    anima_app.start()
                    anima_app=ObjectAnimator.ofFloat(main_now,"alpha",0F,1F)
                    anima_app.duration=500
                    anima_app.start()
                    anima_app=ObjectAnimator.ofFloat(main_back_to_login,"alpha",0F,1F)
                    anima_app.duration=500
                    anima_app.start()
                    appear_flag=true
                }else {
                    var anima_app=ObjectAnimator.ofFloat(main_name,"alpha",1F,0F)
                    anima_app.duration=500
                    anima_app.start()
                    anima_app=ObjectAnimator.ofFloat(main_stat,"alpha",1F,0F)
                    anima_app.duration=500
                    anima_app.start()
                    anima_app=ObjectAnimator.ofFloat(main_now,"alpha",1F,0F)
                    anima_app.duration=500
                    anima_app.start()
                    anima_app=ObjectAnimator.ofFloat(main_back_to_login,"alpha",1F,0F)
                    anima_app.duration=500
                    anima_app.start()
                    appear_flag=false
                }
                soundPlay.playSound("touch_button")
            }
            R.id.main_start->{
                soundPlay.playSound("touch_button")
                var intent= Intent(this,StartMap::class.java)
                intent.putExtra("name",now_user.name)
                startActivity(intent)
            }
            R.id.main_random->{
                soundPlay.playSound("touch_button")
                val intent=Intent(this,RandomMap::class.java)
                startActivity(intent)
            }
            R.id.main_skin->{
                soundPlay.playSound("touch_button")
                val intent=Intent(this,Skin::class.java)
                startActivity(intent)
            }
            R.id.main_back->{
                soundPlay.playSound("touch_button")
                var alertDialog= AlertDialog.Builder(this).apply {
                    setTitle("提示")
                    setMessage("是否退出？")
                    setCancelable(true)
                    setPositiveButton("确认"){dialog, which ->
                        for (i in contextList){
                            i.finish()
                        }
                        finish()
                    }
                    setNegativeButton("取消"){dialog, which ->  }
                }
                alertDialog.show()

            }
            R.id.main_back_to_login->{
                soundPlay.playSound("touch_button")
                var alertDialog= AlertDialog.Builder(this).apply {
                    setTitle("提示")
                    setMessage("是否返回登陆界面？")
                    setCancelable(true)
                    setPositiveButton("确认"){dialog, which ->
                        finish()
                    }
                    setNegativeButton("取消"){dialog, which ->  }
                }
                alertDialog.show()
            }
        }

    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if(event?.keyCode== KeyEvent.KEYCODE_BACK){
            return true
        }else{
            return super.dispatchKeyEvent(event)
        }
    }
}