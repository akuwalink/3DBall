package com.akuwalink.ball.ui.mainview

import android.animation.ObjectAnimator
import android.content.pm.ActivityInfo
import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akuwalink.ball.R
import kotlinx.android.synthetic.main.mainmenu.*

class MainMenu:AppCompatActivity(),View.OnClickListener{

    var _width=0
    var _height=0
    var appear_flag=false
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
    }

    fun initLayout(){
        var margin=main_random.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(0,50,0,0)
        margin=main_counttime.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(0,50,0,0)
        margin=main_skin.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(0,50,0,0)

        margin=main_name.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(0,100,0,0)
        margin=main_stat.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(0,50,0,0)
        margin=main_now.layoutParams as ViewGroup.MarginLayoutParams
        margin.setMargins(0,50,0,0)
    }

    fun addListener(){
        main_head.setOnClickListener(this)
        main_start.setOnClickListener(this)
        main_random.setOnClickListener(this)
        main_counttime.setOnClickListener(this)
        main_skin.setOnClickListener(this)

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
                    appear_flag=false
                }
            }
            R.id.main_start->{

            }
        }

    }

}