package com.akuwalink.ball.logic.physical.basic

data class Vec4(var x:Float,var y:Float,var z:Float,var a:Float){
    fun toFloatArray():FloatArray{
        return floatArrayOf(x,y,z,a)
    }
}