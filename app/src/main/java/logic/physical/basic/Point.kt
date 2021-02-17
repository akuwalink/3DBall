package logic.physical.basic

class Point(var x:Float,var y:Float, var z:Float){
    operator fun plus(p:Point):Point{
        val new_p=Point(p.x+x,p.y+y,p.z+z)
        return new_p
    }

    operator fun minus(p:Point):Point{
        val new_p=Point(p.x-x,p.y-y,p.z-z)
        return new_p
    }

}