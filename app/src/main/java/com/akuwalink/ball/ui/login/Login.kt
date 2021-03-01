package com.akuwalink.ball.ui.login

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.AnimationDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.service.autofill.UserData
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.akuwalink.ball.MyApplication
import com.akuwalink.ball.MyApplication.Companion.contextList
import com.akuwalink.ball.MyApplication.Companion.now_user
import com.akuwalink.ball.MyApplication.Companion.soundPlay
import com.akuwalink.ball.MyApplication.Companion.userDao
import com.akuwalink.ball.R
import com.akuwalink.ball.logic.dao.DataBase
import com.akuwalink.ball.logic.dao.UserDao
import com.akuwalink.ball.logic.model.User
import com.akuwalink.ball.ui.mainview.MainMenu
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.login_denglu.*
import kotlin.concurrent.thread

class Login:AppCompatActivity(),View.OnClickListener{

    var apppare_flag=false
    var _width:Int=0
    var _height:Int=0
    lateinit var login:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        supportActionBar?.hide()
        var point= Point()
        display?.getRealSize(point)
        _width=point.x
        _height=point.y
        clickLoginListenInit()
        login=getSharedPreferences("login.txt", Context.MODE_PRIVATE)
        supportActionBar?.hide()
        window.navigationBarColor= Color.TRANSPARENT
        window.statusBarColor= Color.TRANSPARENT
        var login_ani=login_background.background as AnimationDrawable
        login_ani.start()

        soundPlay.startMedia(R.raw.back_music)
        soundPlay.mp.isLooping=true
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
    fun layoutInit(x:Int,y:Int){
        logindenglu.layoutParams.height=(_height*0.62).toInt()
        logindenglu.layoutParams.width=(_width*0.5).toInt()

        var xx:Int=(x*0.05).toInt()
        var yy:Int=(y*0.07).toInt()
        login_close.layoutParams.width=xx
        login_close.layoutParams.height=yy

        xx=(x*0.4).toInt()
        yy=(y*0.12).toInt()
        login_name.layoutParams.width=xx
        login_name.layoutParams.height=yy
        login_pass.layoutParams.width=xx
        login_pass.layoutParams.height=yy

        xx=x/7
        yy=y/11
        val size=(y*0.02).toFloat()
        login_in.layoutParams.width=xx
        login_in.layoutParams.height=yy
        login_in.textSize=size
        login_register.layoutParams.width=xx
        login_register.layoutParams.height=yy
        login_register.textSize=size

        register_sure.layoutParams.width=xx
        register_sure.layoutParams.height=yy
        register_sure.textSize=size

        xx=(x*0.4).toInt()
        yy=(y*0.12).toInt()
        register_name.layoutParams.width=xx
        register_name.layoutParams.height=yy
        register_pass.layoutParams.width=xx
        register_pass.layoutParams.height=yy
        register_repass.layoutParams.width=xx
        register_repass.layoutParams.height=yy

        xx=(x*0.4).toInt()
        yy=(y*0.12).toInt()
        find_name.layoutParams.width=xx
        find_name.layoutParams.height=yy
        find_pass.layoutParams.width=xx
        find_pass.layoutParams.height=yy

        xx=x/7
        yy=y/11
        login_in.layoutParams.width=xx
        login_in.layoutParams.height=yy
        login_in.textSize=size
    }

    fun clickLoginListenInit(){
        login_close.setOnClickListener(this)
        login_register.setOnClickListener(this)
        login_in.setOnClickListener(this)
        login_remeber.setOnClickListener(this)
        login_find_pass.setOnClickListener(this)
        login_background.setOnClickListener(this)
        register_sure.setOnClickListener(this)
        find_sure.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.login_background->{
                soundPlay.playSound("touch_button")
                if(apppare_flag==false){
                    apppare_flag=true
                    logindenglu.visibility=View.VISIBLE
                    denglu.visibility=View.VISIBLE
                    register.visibility=View.INVISIBLE
                    zhaohui.visibility=View.INVISIBLE
                    layoutInit(_width,_height)
                    if(login.getBoolean("is_remember",false)){
                        var load_flag=false
                        val name = login.getString("last_login", "")
                        var pass: String=""
                        thread {

                            val user = userDao.loadUserForName(name!!)
                            if (user != null) {
                                pass = user.pass
                            }
                            load_flag=true
                        }
                        while (true) {
                            if (load_flag==true&&(!name.equals("")) && (!pass.equals(""))) {
                                login_name.setText(name)
                                login_pass.setText(pass)
                                login_remeber.isChecked = true
                                break
                            }
                        }
                    }
                }else if(!register.isVisible&&!zhaohui.isVisible){
                    logindenglu.visibility=View.INVISIBLE
                    apppare_flag=false
                }
            }
            R.id.login_close->{
                soundPlay.playSound("touch_button")
                if((apppare_flag!=false)&&(!register.isVisible)&&(!zhaohui.isVisible)){
                    logindenglu.visibility=View.INVISIBLE
                    apppare_flag=false
                }else if((apppare_flag!=false)&&(register.isVisible)){
                    register.visibility=View.INVISIBLE
                    denglu.visibility=View.VISIBLE
                }else if((apppare_flag!=false)&&(zhaohui.isVisible)){
                    zhaohui.visibility=View.INVISIBLE
                    denglu.visibility=View.VISIBLE
                }
            }
            R.id.login_in->{
                soundPlay.playSound("touch_button")
                val name=login_name.text.toString()
                val pass=login_pass.text.toString()
                thread {
                    val user = userDao.loadUserForName(name)
                    val old_pass: String
                    if (user == null) {
                        old_pass = ""
                    } else {
                        old_pass = user.pass
                    }
                    if ((!name.equals("")) && pass.equals(old_pass)) {
                        if (login_remeber.isChecked) {
                            val editor = login.edit()
                            editor.putString("last_login", name)
                            editor.putBoolean("is_remember", true)
                            editor.apply()
                        }
                        now_user=user
                        contextList.add(this)
                        val main_intent = Intent(this, MainMenu::class.java)
                        main_intent.putExtra("name",name)
                        startActivity(main_intent)

                    } else {
                        if (login_remeber.isChecked) {
                            val editor = login.edit()
                            editor.putString("last_login", name)
                            editor.putBoolean("is_remember", true)
                            editor.apply()
                        }
                        var show_error=LoginAsync(1)
                        show_error.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                    }
                }
            }
            R.id.login_register->{
                soundPlay.playSound("touch_button")
                denglu.visibility=View.INVISIBLE
                register.visibility=View.VISIBLE
                register_cue.setText("")
            }
            R.id.register_sure->{
                soundPlay.playSound("touch_button")
                val name=register_name.text.toString()
                val pass=register_pass.text.toString()
                val repass=register_repass.text.toString()

                if((name.equals(""))||(name.length<3)||(pass.length<6)||(repass.length<6)){
                    register_cue.text="名字至少三个字符，密码六个"
                }else if(!pass.equals(repass)){
                    register_cue.text="两次输入的密码不符合"
                }else if((name.length>=3)&&(pass.equals(repass))&&(pass.length>=6)&&(name.length<=10)&&(pass.length<=20)){
                    val editor=login.edit()
                    val user=User(name,pass,0,0,R.drawable.ball_1)

                    thread {
                        user.id=userDao.insertUser(user)
                        editor.putLong(name,user.id)
                        editor.apply()
                    }

                    register_name.setText("")
                    register_pass.setText("")
                    register_repass.setText("")

                    register.visibility=View.INVISIBLE
                    denglu.visibility=View.VISIBLE
                }
            }
            R.id.login_find_pass->{
                soundPlay.playSound("touch_button")
                denglu.visibility=View.INVISIBLE
                register.visibility=View.INVISIBLE
                zhaohui.visibility=View.VISIBLE
            }
            R.id.find_sure->{
                soundPlay.playSound("touch_button")
                val name=find_name.text.toString()
                var pass=""
                var find_flag=false
                thread {
                    val user=userDao.loadUserForName(name)
                        if(user!=null){
                        pass=user.pass
                        find_flag=true
                    }
                }

                while(true){
                    if(find_flag) {
                        find_pass.setText(pass)
                        find_flag = true
                        break
                    }
                }

            }
        }
    }

    class LoginAsync(code:Int):AsyncTask<Unit,Int,Boolean>(){
        val code=code
        override fun doInBackground(vararg params: Unit?): Boolean {
           if(code==1){
               publishProgress(1)
           }
            return true
        }

        override fun onProgressUpdate(vararg values: Int?) {
            var code=values[0]
            if(code==1){
                Toast.makeText(MyApplication.context, "您输入的账号或者密码错误", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        soundPlay.stopMedia()
    }
}