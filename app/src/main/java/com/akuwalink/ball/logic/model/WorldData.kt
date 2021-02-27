package com.akuwalink.ball.logic.model

import android.opengl.GLSurfaceView
import com.akuwalink.ball.logic.physical.basic.Vec3
import com.akuwalink.ball.util.Matrix
import com.akuwalink.ball.util.loadMapFile
import java.util.concurrent.CopyOnWriteArrayList

class World(g:Vec3){
    var model_list=CopyOnWriteArrayList<Model>()
    var activity_list=CopyOnWriteArrayList<Model>()
    var next_add_list=CopyOnWriteArrayList<Model>()
    var light=Light(Vec3(0f,0f,0f))
    var camera=Matrix()

    init {
        camera.setPresp(-1f,1f,-1f,1f,2f,10f)
    }
    @Synchronized
    fun addModel(model: Model){
        next_add_list.add(model)
    }
    @Synchronized
    fun removeModdel(model: Model){
        model_list.remove(model)
        if (model.move_flag==true) activity_list.remove(model)
    }
    @Synchronized
    fun drawAll(drawMode:Int){
        for(i in next_add_list){
            model_list.add(i)
            if(i.move_flag==true){
                activity_list.add(i)
            }
            next_add_list.clear()
        }

        if (model_list.size > 0) {
            for (model in model_list) {
                model.drawself(light, camera)
            }
        }

    }
}