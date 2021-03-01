package com.akuwalink.ball.ui.mainview

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akuwalink.ball.MyApplication
import com.akuwalink.ball.R
import com.akuwalink.ball.logic.model.World
import com.akuwalink.ball.logic.physical.basic.Vec3
import com.akuwalink.ball.ui.gameview.SkinSurfaceView
import kotlinx.android.synthetic.main.gametitle.*
import kotlinx.android.synthetic.main.skin.*
import kotlinx.android.synthetic.main.skin_recycler.*


class Skin :AppCompatActivity(){
    private val texList=ArrayList<Tex>()
    lateinit var surfaceView:SkinSurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.skin)
        supportActionBar?.hide()
        window.navigationBarColor= Color.TRANSPARENT
        window.statusBarColor= Color.TRANSPARENT


        var world=World(Vec3())
        surfaceView=SkinSurfaceView(this)
        skin_show.addView(surfaceView)

        initSkin()
        val layouManager=LinearLayoutManager(this)
        skin_image.layoutManager=layouManager
        val adapter=TexAdapter(texList)
        skin_image.adapter=adapter

        game_view_back.setOnClickListener {
            MyApplication.soundPlay.playSound("touch_button")
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        surfaceView.onPause()
    }

    override fun onResume() {
        super.onResume()
        surfaceView.onResume()
    }
    fun initSkin(){
        texList.add(Tex(R.drawable.ball_1,"小球1"))
        texList.add(Tex(R.drawable.ball_2,"小球2"))
        texList.add(Tex(R.drawable.ball_3,"小球3"))
        texList.add(Tex(R.drawable.ball_4,"小球4"))
        texList.add(Tex(R.drawable.ball_5,"小球5"))
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
