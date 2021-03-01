package com.akuwalink.ball.ui.gameview

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.akuwalink.ball.MyApplication
import com.akuwalink.ball.MyApplication.Companion.win_flag
import com.akuwalink.ball.R
import com.akuwalink.ball.logic.model.World
import com.akuwalink.ball.logic.physical.basic.Vec3
import com.akuwalink.ball.ui.mainview.RandomMap
import com.akuwalink.ball.ui.mainview.StartMap
import kotlinx.android.synthetic.main.game.*
import kotlinx.android.synthetic.main.login_denglu.*
import kotlin.concurrent.thread

class GameView :AppCompatActivity(),View.OnClickListener{

    lateinit var surfaceView: GameSurfaceView
    lateinit var world: World
    lateinit var game_win:LinearLayout
    var show_dialog=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.game)
        world= World(Vec3())
        supportActionBar?.hide()
        window.navigationBarColor=Color.TRANSPARENT
        window.statusBarColor=Color.TRANSPARENT


        var map_numbber=intent.getIntExtra("now",1)
        var mode=intent.getIntExtra("mode",0)
        var long=intent.getIntExtra("long",12)
        var width=intent.getIntExtra("width",12)
        var rub=intent.getIntExtra("rub",10)


        surfaceView=GameSurfaceView(this,map_numbber,mode,long,width,rub,world)
        surfaceView.requestFocus()
        surfaceView.isFocusableInTouchMode=true


        game_win=findViewById(R.id.game_win)
        var gameAsync=GameAsync(this,game_win,mode)
        gameAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        game_win.setOnClickListener(this)
        win_flag=false
        game_sure.setOnClickListener(this)
        game_show.addView(surfaceView)


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.game_sure->{
                finish()
            }
        }
    }

   class GameAsync(activity: AppCompatActivity,linearLayout: LinearLayout,mode:Int): AsyncTask<Unit, Int, Boolean>(){
        var flag=false
        lateinit var layout: LinearLayout
        var activity=activity
       var mode=mode
        init {
            layout=linearLayout
        }
        override fun doInBackground(vararg params: Unit?): Boolean {
            while (true){
                if(win_flag==true){
                    flag=true
                    publishProgress()
                    break
                    return true
                }
            }
            return true
        }

        override fun onProgressUpdate(vararg values: Int?) {
            if(flag==true){
                //layout.visibility=View.VISIBLE
                var alertDialog=AlertDialog.Builder(activity).apply {
                    setTitle("闯关成功")
                    setMessage("恭喜")
                    setCancelable(false)
                    setPositiveButton("确认"){dialog, which ->
                        if(mode==0){
                            var intent=Intent(activity,StartMap::class.java)
                            activity.startActivity(intent)
                       }else if(mode==1){
                            var intent=Intent(activity, RandomMap::class.java)
                            activity.startActivity(intent)
                        }
                        activity.finish()
                    }
                }
                alertDialog.show()
                win_flag=false
            }
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


    override fun onResume() {
        super.onResume()
        surfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        surfaceView.onPause()
    }
}
