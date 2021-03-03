package com.akuwalink.ball.logic.physical.basic



object CollisionModels{
    /**
     * 1为盒型碰撞，2为球形碰撞，3为移动碰撞
     */
    val COLLISION_MODE_BOX=1
    val COLLISION_MODE_BALL=2
    val COLLISION_MODE_MOVING=3

    val COLLISION_MODE_SELF=11
    val COLLISION_MODE_STOP=10

    /**
     * x方向碰撞，y方向碰撞，z方向碰撞，xy方向碰撞等
     */
    val COLLISION_SPEED_X=20
    val COLLISION_SPEED_Y=21
    val COLLISION_SPEED_Z=22
    val COLLISION_SPEED_X_Y=23
    val COLLISION_SPEED_X_Z=24
    val COLLISION_SPEED_Y_Z=25
    val COLLISION_SPEED_X_Y_Z=26

    /**
     * @param long 长方形模型的长
     * @param width 长方形模型的宽
     * @param height 长方形模型的稿
     * 获取长方形的碰撞模型
     */
    fun getRectangle(long:Float,width:Float,height:Float):BasicModel {
        var model=BasicModel()
        model.long=long
        model.width=width
        model.height=height
        return model
    }

    /**
     * @param r 球形半径
     * @param center 球形中心
     * 获取球形碰撞模型
     */
    fun getCircle(r:Float,center:Vec3):BasicModel{
        val model=BasicModel().apply { this.r=r
            this.center=center}
        return model
    }

    /**
     * @param start_vec3 柱形碰撞模型向量开始点
     * @param end_vec3 柱形碰撞模型向量结束点
     * @param r 柱形碰撞模型半径
     * 获取柱形碰撞模型
     */
    fun getColumn(start_vec3:Vec3, end_vec3:Vec3, r:Float):BasicModel{
        val model= BasicModel().apply {
            this.start_point=start_vec3
            this.end_point=end_vec3
            this.r=r
        }
        return model
    }
}