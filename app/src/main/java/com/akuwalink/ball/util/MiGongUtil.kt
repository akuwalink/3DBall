package com.akuwalink.ball.util

import android.content.Context
import android.util.Log
import com.akuwalink.ball.MyApplication
import com.akuwalink.ball.R
import com.akuwalink.ball.logic.model.Model
import com.akuwalink.ball.logic.model.models.Ball
import com.akuwalink.ball.logic.model.models.Wall
import com.akuwalink.ball.logic.physical.basic.CollisionModels
import com.akuwalink.ball.logic.physical.basic.Vec3
import java.io.*
import java.util.concurrent.CopyOnWriteArrayList

fun loadMapFile(context: Context,fname:String):Array<IntArray>?{
    var result:Array<IntArray>?=null
    var baos:ByteArrayOutputStream?=null
    try {
        var fis= context.assets.open(fname)
        baos= ByteArrayOutputStream()
        var data=0
        data=fis.read()
        while (data!=-1){
            baos.write(data)
            data=fis.read()
        }
        var buff=baos.toByteArray()
        baos.close()
        fis.close()
        var temp= String(buff,Charsets.UTF_8)
        temp=temp.replace("\r\n","\n")
        var one=temp.split("\n")
        var two=one[0].split("\t")
        var one_length=one.size-1
        val two_length=two.size-1
        result= Array(one.size-1){IntArray(two.size-1)}
        for (i in 0 until one_length){
            two=one[i].split("\t")
            for(j in 0 until two_length){
                result[i][j]=two[j].toInt()
            }
        }

    }catch (e:IOException){
        e.printStackTrace()
    }finally {
    }
    return result
}

fun loadModel(map_data:Array<IntArray>):List<Model>{
    var list=CopyOnWriteArrayList<Model>()
    val ball_v=loadVOBJ("ball.obj")
    val ball_t=loadTOBJ("ball.obj")
    val ball_vn=loadVNOBJ("ball.obj")

    val wall_v= loadVOBJ("wall.obj")
    val wall_t= loadTOBJ("wall.obj")
    val wall_vn= loadVNOBJ("wall.obj")

    val pillar_v= loadVOBJ("pillar.obj")
    val pillar_t= loadTOBJ("pillar.obj")
    val pillar_vn= loadVNOBJ("pillar.obj")

    val ground_v= loadVOBJ("ground.obj")
    val ground_t= loadTOBJ("ground.obj")
    val ground_vn= loadVNOBJ("ground.obj")

    if(ball_v==null||ball_t==null||ball_vn==null){
        Log.e("LOAD_MIGONG","ball load error")
        return list
    }
    if(wall_v==null||wall_t==null||wall_vn==null){
        Log.e("LOAD_MIGONG","wall load error")
        return list
    }
    if (pillar_v==null||pillar_t==null||pillar_vn==null){
        Log.e("LOAD_MIGONG","pillar load error")
        return list
    }
    if (ground_v==null||ground_t==null||ground_vn==null){
        Log.e("LOAD_MIGONG","ground load error")
        return list
    }

    val a=map_data.size
    val b=map_data[0].size

    val scale_x=(a/2).toFloat()
    val scale_y=(b/2).toFloat()
    var start_x=-scale_x
    var start_y=scale_y

    var ground=Wall(ground_v,ground_t,ground_vn)
    ground.setTex(R.drawable.ground_back)
    ground.collision_model=CollisionModels.getRectangle(1f,1f,0.2f)
    ground.scale(scale_x,scale_y,0f)
    ground.translate(0f,0f,-ground.collision_model.height)

    var ball=Ball(ball_v,ball_t,ball_vn)
    ball.collision_model=CollisionModels.getRectangle(0.5f,0.5f,0.5f)
    ball.translate(start_x+1,start_y-1,ball.collision_model.height)

    list.add(ground)
    list.add(ball)

    var model:Model
    for (i in 0 until a){
        for(j in 0 until b){
            if(map_data[i][j]==0) {
                if ((i % 2 == 0)&&(j%2==0)) {
                    model=Wall(pillar_v,pillar_t,pillar_vn)
                    model.collision_model=CollisionModels.getRectangle(0.1f,0.1f,1f)
                    model.translate(start_x,start_y,0f)
                    list.add(model)
                }else if((i % 2 == 0)&&(j % 2 != 0)){
                    model=Wall(wall_v,wall_t,wall_vn)
                    model.collision_model=CollisionModels.getRectangle(0.1f,0.9f,1f)
                    model.translate(start_x,start_y,0f)
                    model.rotate(90f,0f,0f,1f)
                    list.add(model)
                }else if((i % 2 != 0)&&(j % 2 == 0)){
                    model=Wall(wall_v,wall_t,wall_vn)
                    model.collision_model=CollisionModels.getRectangle(0.1f,0.9f,1f)
                    model.translate(start_x,start_y,0f)
                    list.add(model)
                }
            }
            start_x+=1
        }
        start_x=-scale_x
        start_y-=1
    }
    return list
}