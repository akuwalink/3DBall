package com.akuwalink.ball.logic.physical.basic



object CollisionModels{

    val COLLISION_MODE_BOX=1
    val COLLISION_MODE_BALL=2
    val COLLISION_MODE_MOVING=3

    val COLLISION_MODE_SELF=11
    val COLLISION_MODE_STOP=10

    val COLLISION_SPEED_X=20
    val COLLISION_SPEED_Y=21
    val COLLISION_SPEED_Z=22
    val COLLISION_SPEED_X_Y=23
    val COLLISION_SPEED_X_Z=24
    val COLLISION_SPEED_Y_Z=25
    val COLLISION_SPEED_X_Y_Z=26

    fun getRectangle(long:Float,width:Float,height:Float):BasicModel {
        var model=BasicModel()
        model.long=long
        model.width=width
        model.height=height
        return model
    }

    fun getCircle(r:Float,center:Vec3):BasicModel{
        val model=BasicModel().apply { this.r=r
            this.center=center}
        return model
    }

    fun getColumn(start_vec3:Vec3, end_vec3:Vec3, r:Float):BasicModel{
        val model= BasicModel().apply {
            this.start_point=start_vec3
            this.end_point=end_vec3
            this.r=r
        }
        return model
    }
}