package com.akuwalink.ball.ui.mainview

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akuwalink.ball.R
import com.akuwalink.ball.ui.gameview.GameView
import kotlinx.android.synthetic.main.start.*


class StartMap :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.start)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        initLayout()
        game_one.setOnClickListener { val intent=Intent(this,GameView::class.java)
            startActivity(intent)
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



}