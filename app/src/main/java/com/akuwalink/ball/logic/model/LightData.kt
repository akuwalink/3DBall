package com.akuwalink.ball.logic.model

import com.akuwalink.ball.logic.physical.basic.Vec3
import com.akuwalink.ball.logic.physical.basic.Vec4

/**
 * @param vec3 光线位置
 * @param ambient 环境光
 * @param diffuse 散射光
 * @param specular 镜面光
 * @param light_mode 光线模型 0为平行光，1为点光
 */
data class Light(var vec3:Vec3, var ambient:Vec4=Vec4(0.5f,0.5f,0.5f,0.5f), var diffuse:Vec4= Vec4(0.5f,0.5f,0.5f,0.5f), var specular:Vec4=Vec4(0.5f,0.5f,0.5f,0.5f), var light_mode:Int=0){
    fun getPointArray():FloatArray{
        return floatArrayOf(vec3.x,vec3.y,vec3.z)
    }
}