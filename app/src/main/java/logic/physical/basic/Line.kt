package logic.physical.basic

class Line(var start:Point, var end:Point){

    fun getLength():Float{
        val p=end-start
        val length=Math.abs(Math.pow(p.x.toDouble(),2.toDouble())+Math.pow(p.y.toDouble(),2.toDouble())+Math.pow(p.z.toDouble(),2.toDouble()))
        return length.toFloat()
    }
}