package com.akuwalink.ball.ui.gameview

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.util.Log
import com.akuwalink.ball.MyApplication
import com.akuwalink.ball.logic.model.Light
import com.akuwalink.ball.logic.model.Model
import com.akuwalink.ball.logic.model.World
import com.akuwalink.ball.logic.model.models.Ball
import com.akuwalink.ball.logic.model.models.Wall
import com.akuwalink.ball.logic.physical.basic.CollisionModels
import com.akuwalink.ball.logic.physical.basic.Vec3
import com.akuwalink.ball.logic.physical.event.Collision
import com.akuwalink.ball.util.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.MotionEvent


class GameSurfaceView (context: Context):GLSurfaceView(context){

    var last_x=0f
    var last_y=0f
    lateinit var world:World
    init{
        this.setEGLContextClientVersion(3)
        world=World(Vec3())

        val renderer=GameRenderer(world)
        this.setRenderer(renderer)
        this.renderMode=GLSurfaceView.RENDERMODE_CONTINUOUSLY
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event!=null&&world.model_list.size>0){
            var x=event.x
            var y=event.y
            when(event.action){
                MotionEvent.ACTION_DOWN->{
                    last_x=x
                    last_y=y
                }
                MotionEvent.ACTION_MOVE->{
                    var dx=x-last_x
                    var dy=last_y-y
                    world.model_list[1].speed.x+=dx/10000
                    world.model_list[1].speed.y+=dy/10000

                }
            }
        }
        return true
    }
    class GameRenderer(world: World) :GLSurfaceView.Renderer{
        var world:World
        var now_width=0
        var now_height=0
        override fun onDrawFrame(gl: GL10?) {
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT)
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
            if(world.model_list.size>0) {
                Collision.collisionStart(world)
            }
            world.drawAll(1)
            //Log.e("now x y",world.model_list[1].collision_model.center.x.toString()+" "+world.collision_model.center.y)
        }
        init {
            this.world=world
        }
        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            val ratio=(width/(height*1.0f))
            GLES30.glViewport(0,0,width,height)
            now_width=width
            now_height=height
            world.camera.setPresp(-ratio*12,ratio*12,-1f*12,1f*12,28f,32f)
            world.light= Light(Vec3(0f,0f,20f))
            world.camera.setCamera(0f,0f,30f,0f,0f,0f,0f,1f,0f)
            //checkError("onSurfaceChange")
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES30.glClearColor(0.5f,0.5f,0.5f,0.5f)
            GLES30.glEnable(GLES30.GL_DEPTH_TEST)
            var data=loadMapFile(MyApplication.context,"one.txt")
            val list= loadModel(data!!)
            for (i in list){
                world.addModel(i)
            }

        }

       /* fun initFRBUffers(){
            var tia=IntArray(1)
            GLES30.glGenFramebuffers(1,tia,0)
            frameBufferId=tia[0]

            GLES30.glGenRenderbuffers(1,tia,0)
            renderDepthBufferId=tia[0]
            GLES30.glBindRenderbuffer(GLES30.GL_RENDERBUFFER,renderDepthBufferId)
            GLES30.glRenderbufferStorage(GLES30.GL_RENDERBUFFER,GLES30.GL_DEPTH_COMPONENT16,4096,4096)
            var tempIds=IntArray(1)
            GLES30.glGenTextures(1,tempIds,0)
            shadowId=tempIds[0]
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,shadowId)
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER,GLES30.GL_LINEAR)
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_MAG_FILTER,GLES30.GL_LINEAR)
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S,GLES30.GL_CLAMP_TO_EDGE)
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_CLAMP_TO_EDGE)

            GLES30.glTexImage2D(GLES30.GL_TEXTURE_2D, 0, GLES30.GL_R16F, 4096, 4096, 0, GLES30.GL_RED, GLES30.GL_FLOAT, null)
            GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, frameBufferId)
            GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, GLES30.GL_COLOR_ATTACHMENT0, GLES30.GL_TEXTURE_2D, shadowId, 0)
            GLES30.glFramebufferRenderbuffer(GLES30.GL_FRAMEBUFFER, GLES30.GL_DEPTH_ATTACHMENT, GLES30.GL_RENDERBUFFER, renderDepthBufferId)

            if(GLES30.glCheckFramebufferStatus(GLES30.GL_FRAMEBUFFER)!=GLES30.GL_FRAMEBUFFER_COMPLETE){
                Log.e("init error","now error")
            }
            GLES30.glBindRenderbuffer(GLES30.GL_RENDERBUFFER,0)
            checkError("initFRBBuffer")
        }

        fun shadowImage(){
            GLES30.glViewport(0,0,4096,4096)
            GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER,frameBufferId)
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT)
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
            checkError("shadowImage")
            world.camera.setShadowMatrix(-1f,1f,-1f,1f,2f,40f,world.light)

            world.drawAll(1)
        }

        fun drawScane(gl: GL10?){
            GLES30.glViewport(0,0,now_width,now_height)
            GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER,0)
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT)
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
            world.shadowId=shadowId
            checkError("drawScane")
            world.drawAll(0)
        }*/
    }
}
