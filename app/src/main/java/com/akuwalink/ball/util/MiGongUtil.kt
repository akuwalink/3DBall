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
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.ArrayList

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

fun loadModel(map_data:Array<IntArray>,context: Context):List<Model>{
    var list=CopyOnWriteArrayList<Model>()
    val ball_v=loadVOBJ("ball.obj",context)
    val ball_t=loadTOBJ("ball.obj",context)
    val ball_vn=loadVNOBJ("ball.obj",context)

    val wall_v= loadVOBJ("wall.obj",context)
    val wall_t= loadTOBJ("wall.obj",context)
    val wall_vn= loadVNOBJ("wall.obj",context)

    val pillar_v= loadVOBJ("pillar.obj",context)
    val pillar_t= loadTOBJ("pillar.obj",context)
    val pillar_vn= loadVNOBJ("pillar.obj",context)

    val ground_v= loadVOBJ("ground.obj",context)
    val ground_t= loadTOBJ("ground.obj",context)
    val ground_vn= loadVNOBJ("ground.obj",context)

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

    var ground=Wall(ground_v,ground_t,ground_vn,context)
    ground.setTex(R.drawable.ground_back)
    ground.collision_model=CollisionModels.getRectangle(1f,1f,0.2f)
    ground.scale(scale_x,scale_y,0f)
    ground.translate(0f,0f,-ground.collision_model.height)

    var ball=Ball(ball_v,ball_t,ball_vn,context)
    ball.collision_model=CollisionModels.getRectangle(0.5f,0.5f,0.5f)
    ball.translate(start_x+1,start_y-1,ball.collision_model.height)
    var texId = MyApplication.now_user.texId
    ball.setTex(texId)
    list.add(ground)
    list.add(ball)

    var model:Model
    for (i in 0 until a){
        for(j in 0 until b){
            if(map_data[i][j]==0) {
                if ((i % 2 == 0)&&(j%2==0)) {
                    model=Wall(pillar_v,pillar_t,pillar_vn,context)
                    model.collision_model=CollisionModels.getRectangle(0.1f,0.1f,1f)
                    model.translate(start_x,start_y,0f)
                    list.add(model)
                }else if((i % 2 == 0)&&(j % 2 != 0)){
                    model=Wall(wall_v,wall_t,wall_vn,context)
                    model.collision_model=CollisionModels.getRectangle(0.1f,0.9f,1f)
                    model.translate(start_x,start_y,0f)
                    model.rotate(90f,0f,0f,1f)
                    list.add(model)
                }else if((i % 2 != 0)&&(j % 2 == 0)){
                    model=Wall(wall_v,wall_t,wall_vn,context)
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

fun makeMap(xx:Int,yy:Int,start:IntArray):Array<IntArray>{
    var r= Random()
    var start_x=start[0]
    var start_y=start[1]
    var x=xx*2
    var y = yy * 2
    start_x*=2
    start_y*=2
    start_x++
    start_y++
    var result=Array(x+1){IntArray(y+1)}
    for(i in 0..x){
        for (j in 0..y){
            result[i][j]=0
        }
    }
    var list=ArrayList<Int>()
    var  ax=start_x
    var ay=start_y;
    result[ax][ay]=1;
    if(ay>1) {
        list.add(ax)
        list.add(ay-1)
        list.add(0)
    }
    if(ay<y-1) {
        list.add(ax)
        list.add(ay+1)
        list.add(1)
    }
    if(ax>1) {
        list.add(ax-1)
        list.add(ay)
        list.add(2)
    }
    if(ax<x-1) {
        list.add(ax+1)
        list.add(ay)
        list.add(3)
    }
    while(list.size>0) {
        var  now=r.nextInt(list.size/3)*3
        var target=list.get(now+2)
        if(target==0) {
            ax=list.get(now)
            ay=list.get(now+1)-1
        }else if(target==1){
            ax=list.get(now)
            ay=list.get(now+1)+1
        }else if(target==2) {
            ax=list.get(now)-1
            ay=list.get(now+1)
        }else if(target==3) {
            ax=list.get(now)+1
            ay=list.get(now+1)
        }

        if(result[ax][ay]==0) {
            result[ax][ay]=1
            result[list.get(now)][list.get(now+1)]=1
            if(ay>1) {
                if(ay>1&&(result[ax][ay-1]==0)&&(result[ax][ay-2]==0)) {
                    list.add(ax)
                    list.add(ay-1)
                    list.add(0)

                }
            }
            if(ay<y-1) {
                if(ay<y&&(result[ax][ay+1]==0)&&(result[ax][ay+2]==0)) {
                    list.add(ax)
                    list.add(ay+1)
                    list.add(1)

                }
            }
            if(ax>1) {
                if(ax>1&&(result[ax-1][ay]==0)&&(result[ax-2][ay]==0)) {
                    list.add(ax-1)
                    list.add(ay)
                    list.add(2)

                }
            }
            if(ax<x-1) {
                if(ax<x&&(result[ax+1][ay]==0)&&(result[ax+2][ay]==0)) {
                    list.add(ax+1)
                    list.add(ay)
                    list.add(3)

                }
            }
        }
        list.removeAt(now)
        list.removeAt(now)
        list.removeAt(now)
    }

    return result

}