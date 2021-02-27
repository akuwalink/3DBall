package com.akuwalink.ball.logic.physical.basic

class Vec3(var x:Float=0f, var y:Float=0f, var z:Float=0f){
    operator fun plus(p:Vec3):Vec3{
        val new_p=Vec3(p.x+x,p.y+y,p.z+z)
        return new_p
    }

    operator fun minus(p:Vec3):Vec3{
        val new_p=Vec3(p.x-x,p.y-y,p.z-z)
        return new_p
    }

}