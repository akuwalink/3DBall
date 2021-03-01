package com.akuwalink.ball.logic.physical.event

import android.util.Log
import com.akuwalink.ball.logic.model.Model
import com.akuwalink.ball.logic.model.World
import com.akuwalink.ball.logic.physical.basic.BasicModel
import com.akuwalink.ball.logic.physical.basic.CollisionModels
import com.akuwalink.ball.logic.physical.basic.Vec3
import com.akuwalink.ball.ui.gameview.GameSurfaceView
import java.math.BigDecimal
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.concurrent.thread

object Collision{
    @Synchronized
    fun collisionStart(world: World,mode:Int=10){
        for(model in world.activity_list){
            var event=Event()
            var x:Float=0f
            var y:Float=0f
            var z:Float=0f
            var collision_list= diviteModel(model,world.model_list)

            model.moveTemp(model.speed.x,0f,0f)
            event=collisionDetection(model,collision_list,CollisionModels.COLLISION_MODE_BOX)
            if(event.collision_flag==true) {
                x=defaultFun(model,event.model!!,CollisionModels.COLLISION_SPEED_X)
                model.collsionEvent(event)
            }else{
                x=model.speed.x
            }

            model.moveTemp(0f,model.speed.y,0f)
            event=collisionDetection(model,collision_list,CollisionModels.COLLISION_MODE_BOX)
            if(event.collision_flag==true) {
                y=defaultFun(model,event.model!!,CollisionModels.COLLISION_SPEED_Y)
                model.collsionEvent(event)
            }else {
                y = model.speed.y
            }

            model.moveTemp(0f,0f,model.speed.z)
            event=collisionDetection(model,collision_list,CollisionModels.COLLISION_MODE_BOX)
            if(event.collision_flag==true) {
                z=defaultFun(model,event.model!!,CollisionModels.COLLISION_SPEED_Z)
                model.collsionEvent(event)
            }else {
                z = model.speed.z
            }

            model.translate(x, y, z)
            /*model.rotate((x/Math.PI*360).toFloat(),0f,0f,1f)
            if(x!=0f||y!=0f) {
                Log.e("test", model.collision_model.long.toString() + "  " + model.collision_model.width + "  ")
            }
            model.rotate((y/Math.PI*360).toFloat(),1f,0f,0f)*/
            updateSpeed(model)

        }
    }

    private fun collisionDetection(model: Model,modelList: CopyOnWriteArrayList<Model>, mode:Int):Event{
        var event=Event()
        when(mode){
            CollisionModels.COLLISION_MODE_BOX->{
                var dx: BigDecimal
                var dy: BigDecimal
                var dz: BigDecimal
                var ax: BigDecimal
                var ay:  BigDecimal
                var az:  BigDecimal
                var f_m:BasicModel
                var s_m:BasicModel

                for (i in modelList) {
                    if(model!=i) {
                        f_m = model.collision_model_temp
                        s_m = i.collision_model

                        dx=BigDecimal(s_m.center.x.toDouble()).subtract(BigDecimal(f_m.center.x.toDouble())).abs().setScale(6,BigDecimal.ROUND_HALF_UP)
                        dy =BigDecimal(s_m.center.y.toDouble()).subtract(BigDecimal(f_m.center.y.toDouble())).abs().setScale(6,BigDecimal.ROUND_HALF_UP)
                        dz =BigDecimal(s_m.center.z.toDouble()).subtract(BigDecimal(f_m.center.z.toDouble())).abs().setScale(6,BigDecimal.ROUND_HALF_UP)
                        ax = BigDecimal(s_m.long.toDouble()).add(BigDecimal(f_m.long.toDouble())).setScale(6,BigDecimal.ROUND_HALF_UP)
                        ay = BigDecimal(s_m.width.toDouble()).add(BigDecimal(f_m.width.toDouble())).setScale(6,BigDecimal.ROUND_HALF_UP)
                        az = BigDecimal(s_m.height.toDouble()).add(BigDecimal(f_m.height.toDouble())).setScale(6,BigDecimal.ROUND_HALF_UP)
                        if ((dx < ax) && (dy < ay) && (dz < az)) {
                            event.collision_flag = true
                            event.model = i
                            return event
                        }
                    }
                }
                return event
            }
            CollisionModels.COLLISION_MODE_BALL->{return event}
            CollisionModels.COLLISION_MODE_MOVING->{return event}
        }
        return Event()
    }

    private fun diviteModel(model:Model,list:List<Model>):CopyOnWriteArrayList<Model>{
        var now_list= CopyOnWriteArrayList<Model>()
        var x=model.collision_model.center.x
        var y=model.collision_model.center.y
        var z=model.collision_model.center.z

        for (i in list){
            if((Math.abs(i.collision_model.center.x-x)<=2)&&(Math.abs(i.collision_model.center.y-y)<=2)){
                now_list.add(i)
            }
        }
        return now_list
    }

    private fun defaultFun(model: Model,anModel: Model,mode:Int):Float{
        val ax=model.collision_model.long+anModel.collision_model.long
        val ay=model.collision_model.width+anModel.collision_model.width
        val az=model.collision_model.height+anModel.collision_model.height
        var result=0f

        when(mode){
            CollisionModels.COLLISION_SPEED_X->{
                if (model.speed.x>0){
                    result=anModel.collision_model.center.x-ax-model.collision_model.center.x
                }else if(model.speed.x<0){
                    result=anModel.collision_model.center.x+ax-model.collision_model.center.x
                }
                model.speed.x=-model.speed.x
            }
            CollisionModels.COLLISION_SPEED_Y->{
                if(model.speed.y>0){
                    result=anModel.collision_model.center.y-ay-model.collision_model.center.y
                }else if(model.speed.y<0){
                    result=anModel.collision_model.center.y+ay-model.collision_model.center.y
                }
                model.speed.y=-model.speed.y
            }
            CollisionModels.COLLISION_SPEED_Z->{
                if(model.speed.z>0){
                    result=anModel.collision_model.center.z-az-model.collision_model.center.z
                }else if(model.speed.z<0){
                    result=anModel.collision_model.center.z+az-model.collision_model.center.z
                }
                model.speed.z=-model.speed.z
            }

        }

        return result
    }

    private fun updateSpeed(model: Model){
        if(model.speed.x<0){
            model.speed.x+=model.rub
            if(model.speed.x>0){
                model.speed.x=0f
            }
        }else if(model.speed.x>0){
            model.speed.x-=model.rub
            if(model.speed.x<0){
                model.speed.x=0f
            }
        }
        if(model.speed.y<0){
            model.speed.y+=model.rub
            if(model.speed.y>0){
                model.speed.y=0f
            }
        }else if(model.speed.y>0){
            model.speed.y-=model.rub
            if(model.speed.y<0){
                model.speed.y=0f
            }
        }
        if(model.speed.z<0){
            model.speed.z+=model.rub
            if(model.speed.z>0){
                model.speed.z=0f
            }
        }else if(model.speed.z>0){
            model.speed.z-=model.rub
            if(model.speed.z<  0){
                model.speed.z=0f
            }
        }
    }
}
