package com.akuwalink.ball.ui.login

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.Point
import android.os.Bundle
import android.service.autofill.UserData
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.akuwalink.ball.MyApplication.Companion.now_login
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
    lateinit var userDao: UserDao

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
        userDao=DataBase.getDataBase(this).userDao()
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


    }

    fun clickLoginListenInit(){
        login_close.setOnClickListener(this)
        login_register.setOnClickListener(this)
        login_in.setOnClickListener(this)
        login_remeber.setOnClickListener(this)
        login_find_pass.setOnClickListener(this)
        login_background.setOnClickListener(this)
        register_sure.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.login_background->{
                if(apppare_flag==false){
                    apppare_flag=true
                    logindenglu.visibility=View.VISIBLE
                    denglu.visibility=View.VISIBLE
                    register.visibility=View.INVISIBLE
                    layoutInit(_width,_height)
                    if(login.getBoolean("is_remember",false)){
                        thread {
                            val name = login.getString("last_login", "")
                            val user = userDao.loadUserForName(name!!)
                            val pass: String
                            if (user == null) {
                                pass = ""
                            } else {
                                pass = user.pass
                            }

                            if ((!name.equals("")) && (!pass.equals(""))) {
                                login_name.setText(name)
                                login_pass.setText(pass)
                                login_remeber.isChecked = true
                            }
                        }
                    }
                }else if(!register.isVisible){
                    logindenglu.visibility=View.INVISIBLE
                    apppare_flag=false
                }
            }
            R.id.login_close->{
                if((apppare_flag!=false)&&(!register.isVisible)){
                    logindenglu.visibility=View.INVISIBLE
                    apppare_flag=false
                }else if((apppare_flag!=false)&&(register.isVisible)){
                    register.visibility=View.INVISIBLE
                    denglu.visibility=View.VISIBLE
                }
            }
            R.id.login_in->{
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
                        now_login=user

                        val main_intent = Intent(this, MainMenu::class.java)
                        startActivity(main_intent)

                    } else {
                        if (login_remeber.isChecked) {
                            val editor = login.edit()
                            editor.putString("last_login", name)
                            editor.putBoolean("is_remember", true)
                            editor.apply()
                        }
                        Toast.makeText(this, "账号密码不正确", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            R.id.login_register->{
                Toast.makeText(this,"click register",Toast.LENGTH_SHORT).show()
                denglu.visibility=View.INVISIBLE
                register.visibility=View.VISIBLE
                register_cue.setText("")
            }
            R.id.register_sure->{
                val name=register_name.text.toString()
                val pass=register_pass.text.toString()
                val repass=register_repass.text.toString()

                if((name.equals(""))||(name.length<3)||(pass.length<6)||(repass.length<6)){
                    register_cue.text="名字至少三个字符，密码六个"
                }else if(!pass.equals(repass)){
                    register_cue.text="两次输入的密码不符合"
                }else if((name.length>=3)&&(pass.equals(repass))&&(pass.length>=6)){
                    val editor=login.edit()
                    val user=User(name,pass,0,0)

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
        }
    }
}