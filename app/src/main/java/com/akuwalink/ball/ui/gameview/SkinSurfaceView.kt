package com.akuwalink.ball.ui.gameview

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.util.Log
import android.view.MotionEvent
import com.akuwalink.ball.MyApplication.Companion.now_user
import com.akuwalink.ball.MyApplication.Companion.userDao
import com.akuwalink.ball.R
import com.akuwalink.ball.logic.model.Light
import com.akuwalink.ball.logic.model.World
import com.akuwalink.ball.logic.model.models.Ball
import com.akuwalink.ball.logic.model.models.Wall
import com.akuwalink.ball.logic.physical.basic.Vec3
import com.akuwalink.ball.util.loadTOBJ
import com.akuwalink.ball.util.loadVNOBJ
import com.akuwalink.ball.util.loadVOBJ
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.concurrent.thread

class SkinSurfaceView(context: Context) : GLSurfaceView(context){
    var last_x=0f
    var last_y=0f
    lateinit var world: World
    init{
        this.setEGLContextClientVersion(3)
        world= World(Vec3())
        val renderer= SkinSurfaceView.GameRenderer(world,context)
        this.setRenderer(renderer)
        this.renderMode=GLSurfaceView.RENDERMODE_WHEN_DIRTY
        requestRender()
        thread {
            var old_tex= now_user.texId
            while(true){
                var now_tex=now_user.texId
                if(now_tex!=old_tex){
                    requestRender()
                    userDao.updataUser(now_user)
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                last_x=event.x
                last_y=event.y
            }
            MotionEvent.ACTION_MOVE->{
                var dx=(event.x-last_x)/100
                for (model in world.model_list){
                    model.rotate(dx,0f,0f,1f)
                }
            }
        }
        return true
    }

    class GameRenderer(world: World,c:Context) :GLSurfaceView.Renderer {
        var world: World
        var context=c
        var now_width = 0
        var now_height = 0
        override fun onDrawFrame(gl: GL10?) {
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT)
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
            var texId=now_user.texId
            if(world.model_list.size>0) {
                world.model_list[0].setTex(texId)
            }
            world.drawAll(1)
        }

        init {
            this.world = world
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            val ratio = (width / (height * 1.0f))
            GLES30.glViewport(0, 0, width, height)
            now_width = width
            now_height = height
            world.camera.setPresp(-ratio , ratio , -1f , 1f , 4f, 12f)
            world.light = Light(Vec3(0f, 5f, 3f))
            world.camera.setCamera(5f, 5f, 3f, 0f, 0f, 0f, 0f, 0f, 1f)
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES30.glClearColor(0.5f, 0.5f, 0.5f, 0.5f)
            GLES30.glEnable(GLES30.GL_DEPTH_TEST)

            var ball_v= loadVOBJ("ball.obj",context)
            var ball_t= loadTOBJ("ball.obj",context)
            var ball_vn= loadVNOBJ("ball.obj",context)

            if(ball_v==null&&ball_t==null&&ball_vn==null){
                Log.e("mainSurface","load error")
            }else {
                var ball = Ball(ball_v!!, ball_t!!, ball_vn!!,context)
                ball.translate(0f,0f,0.5f)
                world.addModel(ball)
            }

        }
    }
}