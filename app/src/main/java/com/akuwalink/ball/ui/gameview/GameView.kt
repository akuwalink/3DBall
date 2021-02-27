package com.akuwalink.ball.ui.gameview

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.akuwalink.ball.R

class GameView :AppCompatActivity(){
    lateinit var surfaceView: GameSurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        surfaceView=GameSurfaceView(this)
        surfaceView.requestFocus()
        surfaceView.isFocusableInTouchMode=true
        setContentView(surfaceView)
        supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()
        surfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        surfaceView.onPause()
    }
}
