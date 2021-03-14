package com.akuwalink.ball.ui.mainview

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.akuwalink.ball.MyApplication
import com.akuwalink.ball.MyApplication.Companion.delectList
import com.akuwalink.ball.R
import com.akuwalink.ball.ui.gameview.GameView
import kotlinx.android.synthetic.main.random.*

class RandomMap :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.random)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        window.navigationBarColor= Color.TRANSPARENT
        window.statusBarColor= Color.TRANSPARENT
        var long=12
        var width=12
        var rub=50
        random_sure.setOnClickListener {
            MyApplication.soundPlay.playSound("touch_button")
            long=random_long.text.toString().toInt()
            width=random_width.text.toString().toInt()
            rub=random_rub.text.toString().toInt()
            if(long<=5||width<=5||rub<=20){
                random_show_error.setText("长和宽必须大于5，光滑度必须大于20")
            }else if(long>40||width>40||rub>100){
                random_show_error.setText("长或者宽不得超过40，光滑度不得超过100")
            }else{
                var intent=Intent(this,GameView::class.java)
                intent.putExtra("long",long)
                intent.putExtra("width",width)
                intent.putExtra("rub",rub)
                intent.putExtra("mode",1)
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        for(i in delectList){
            i.finish()
        }
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


}

