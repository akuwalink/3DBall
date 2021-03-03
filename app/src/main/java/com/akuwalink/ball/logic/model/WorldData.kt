package com.akuwalink.ball.logic.model

import android.opengl.GLSurfaceView
import com.akuwalink.ball.MyApplication
import com.akuwalink.ball.R
import com.akuwalink.ball.logic.physical.basic.Vec3
import com.akuwalink.ball.util.Matrix
import com.akuwalink.ball.util.MusicUtil
import com.akuwalink.ball.util.loadMapFile
import java.util.concurrent.CopyOnWriteArrayList

class World(g:Vec3){

    /**
     * 模型列表，主动物体列表，待添加物体列表，光线，相机
     */
    var model_list=CopyOnWriteArrayList<Model>()
    var activity_list=CopyOnWriteArrayList<Model>()
    var next_add_list=CopyOnWriteArrayList<Model>()
    var light=Light(Vec3(0f,0f,0f))
    var camera=Matrix()

    /**
     * 初始化相机
     */
    init {
        camera.setPresp(-1f,1f,-1f,1f,2f,10f)
    }

    /**
     * 添加模型
     */
    @Synchronized
    fun addModel(model: Model){
        next_add_list.add(model)
    }

    /**
     * 移除模型
     */
    @Synchronized
    fun removeModdel(model: Model){
        model_list.remove(model)
        if (model.move_flag==true) activity_list.remove(model)
    }

    /**
     * 移除所有模型
     */
    @Synchronized
    fun removeAll(){
        model_list.clear()
        activity_list.clear()
        next_add_list.clear()
    }

    /**
     * 将未添加的模型导入绘制列表，绘制
     */
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