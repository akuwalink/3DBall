package com.akuwalink.ball.ui.mainview

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akuwalink.ball.MyApplication
import com.akuwalink.ball.MyApplication.Companion.now_user
import com.akuwalink.ball.R
import com.akuwalink.ball.logic.dao.DataBase
import com.akuwalink.ball.logic.dao.UserDao
import com.akuwalink.ball.logic.model.User
import com.akuwalink.ball.ui.gameview.GameView
import kotlinx.android.synthetic.main.gametitle.*
import kotlinx.android.synthetic.main.mainmenu.*
import kotlinx.android.synthetic.main.start.*
import kotlin.concurrent.thread


class StartMap :AppCompatActivity(),View.OnClickListener{
    lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.start)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        initLayout()
        supportActionBar?.hide()
        window.navigationBarColor= Color.TRANSPARENT
        window.statusBarColor= Color.TRANSPARENT

        user= now_user
        openMap(user)
        addListener()

    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if(event?.keyCode==KeyEvent.KEYCODE_BACK){
            return true
        }else{
            return super.dispatchKeyEvent(event)
        }
    }


    private fun addListener(){
        game_one.setOnClickListener(this)
        game_two.setOnClickListener(this)
        game_three.setOnClickListener(this)
        game_four.setOnClickListener(this)
        game_five.setOnClickListener(this)
        game_six.setOnClickListener(this)
        game_seven.setOnClickListener(this)
        game_eight.setOnClickListener(this)
        game_nine.setOnClickListener(this)
        game_ten.setOnClickListener(this)
        game_eleven.setOnClickListener(this)
        game_twelve.setOnClickListener(this)
        game_view_back.setOnClickListener(this)
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
        var margin=game_two.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(120,0,0,0)
        margin=game_three.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(120,0,0,0)
        margin=game_four.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(120,0,0,0)

        margin=game_six.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(120,0,0,0)
        margin=game_seven.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(120,0,0,0)
        margin=game_eight.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(120,0,0,0)

        margin=game_ten.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(120,0,0,0)
        margin=game_eleven.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(120,0,0,0)
        margin=game_twelve.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(120,0,0,0)
    }

    private fun openMap(user: User){
        var count=user.now
        if(count>=0){
            game_one.setResId(R.drawable.star)
            count--
        }
        if(count>=0){
            game_two.setResId(R.drawable.star)
            count--
        }
        if(count>=0){
            game_three.setResId(R.drawable.star)
            count--
        }
        if(count>=0){
            game_four.setResId(R.drawable.star)
            count--
        }
        if(count>=0){
            game_five.setResId(R.drawable.star)
            count--
        }
        if(count>=0){
            game_six.setResId(R.drawable.star)
            count--
        }
        if(count>=0){
            game_seven.setResId(R.drawable.star)
            count--
        }
        if(count>=0){
            game_eight.setResId(R.drawable.star)
            count--
        }
        if(count>=0){
            game_nine.setResId(R.drawable.star)
            count--
        }
        if(count>=0){
            game_ten.setResId(R.drawable.star)
            count--
        }
        if(count>=0){
            game_eleven.setResId(R.drawable.star)
            count--
        }
        if(count>=0){
            game_twelve.setResId(R.drawable.star)
            count--
        }

    }

    override fun onStart() {
        super.onStart()
        openMap(user)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.game_view_back->{
                MyApplication.soundPlay.playSound("touch_button")
                val intent = Intent(this, MainMenu::class.java)
                startActivity(intent)
                finish()
            }
            R.id.game_one->{
                MyApplication.soundPlay.playSound("touch_button")
                if (user.now>=0) {
                    val intent = Intent(this, GameView::class.java)
                    intent.putExtra("now",1)
                    intent.putExtra("mode",0)
                    startActivity(intent)
                }
            }
            R.id.game_two->{
                MyApplication.soundPlay.playSound("touch_button")
                if (user.now>=1) {
                    val intent = Intent(this, GameView::class.java)
                    intent.putExtra("now",2)
                    intent.putExtra("mode",0)
                    startActivity(intent)
                }
            }
            R.id.game_three->{
                MyApplication.soundPlay.playSound("touch_button")
                if (user.now>=2) {
                    val intent = Intent(this, GameView::class.java)
                    intent.putExtra("now",3)
                    intent.putExtra("mode",0)
                    startActivity(intent)
                }
            }
            R.id.game_four->{
                MyApplication.soundPlay.playSound("touch_button")
                if (user.now>=3) {
                    val intent = Intent(this, GameView::class.java)
                    intent.putExtra("now",4)
                    intent.putExtra("mode",0)
                    startActivity(intent)
                }
            }
            R.id.game_five->{
                MyApplication.soundPlay.playSound("touch_button")
                if (user.now>=4) {
                    val intent = Intent(this, GameView::class.java)
                    intent.putExtra("now",5)
                    intent.putExtra("mode",0)
                    startActivity(intent)
                }
            }
            R.id.game_six->{
                MyApplication.soundPlay.playSound("touch_button")
                if (user.now>=5) {
                    val intent = Intent(this, GameView::class.java)
                    intent.putExtra("now",6)
                    intent.putExtra("mode",0)
                    startActivity(intent)
                }
            }
            R.id.game_seven->{
                MyApplication.soundPlay.playSound("touch_button")
                if (user.now>=6) {
                    val intent = Intent(this, GameView::class.java)
                    intent.putExtra("now",7)
                    intent.putExtra("mode",0)
                    startActivity(intent)
                }
            }
            R.id.game_eight->{
                MyApplication.soundPlay.playSound("touch_button")
                if (user.now>=7) {
                    val intent = Intent(this, GameView::class.java)
                    intent.putExtra("now",8)
                    intent.putExtra("mode",0)
                    startActivity(intent)
                }
            }
            R.id.game_nine->{
                MyApplication.soundPlay.playSound("touch_button")
                if (user.now>=8) {
                    val intent = Intent(this, GameView::class.java)
                    intent.putExtra("now",9)
                    intent.putExtra("mode",0)
                    startActivity(intent)
                }
            }
            R.id.game_ten->{
                MyApplication.soundPlay.playSound("touch_button")
                if (user.now>=9) {
                    val intent = Intent(this, GameView::class.java)
                    intent.putExtra("now",10)
                    intent.putExtra("mode",0)
                    startActivity(intent)
                }
            }
            R.id.game_eleven->{
                MyApplication.soundPlay.playSound("touch_button")
                if (user.now>=10) {
                    val intent = Intent(this, GameView::class.java)
                    intent.putExtra("now",11)
                    intent.putExtra("mode",0)
                    startActivity(intent)
                }
            }
            R.id.game_twelve->{
                MyApplication.soundPlay.playSound("touch_button")
                if (user.now>=11) {
                    val intent = Intent(this, GameView::class.java)
                    intent.putExtra("now",12)
                    intent.putExtra("mode",0)
                    startActivity(intent)
                }
            }
        }
    }

}