package com.akuwalink.ball.util

import com.akuwalink.ball.MyApplication
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.ArrayList

fun loadVOBJ(fname:String):FloatArray?{
    val ver_min=ArrayList<Float>()
    var vertex=ArrayList<Float>()
    var result:FloatArray?=null
    try {
        val input=MyApplication.context.resources.assets.open(fname)
        val isr=InputStreamReader(input)
        val br=BufferedReader(isr)
        var s:String?=br.readLine()
        while(s!=null){
            var ss=s.split(" ")
            if (ss[0].trim().equals("v")){
                vertex.add(ss[1].toFloat())
                vertex.add(ss[2].toFloat())
                vertex.add(ss[3].toFloat())
            }else if(ss[0].trim().equals("f")){
                for(i in 1..3){
                    var index=ss[i].split("/")[0].toInt()-1
                    ver_min.add(vertex[3*index])
                    ver_min.add(vertex[3*index+1])
                    ver_min.add(vertex[3*index+2])
                }
            }
            s=br.readLine()
        }
        input.close()
        isr.close()
        br.close()
        result=ver_min.toFloatArray()

    }catch (e:IOException){
        e.printStackTrace()
    }
    return result
}

fun loadTOBJ(fname:String):FloatArray?{
    val ver_min=ArrayList<Float>()
    var vertex_T=ArrayList<Float>()
    var result:FloatArray?=null
    try {
        val input=MyApplication.context.resources.assets.open(fname)
        val isr=InputStreamReader(input)
        val br=BufferedReader(isr)
        var s:String?=br.readLine()
        while(s!=null){
            var ss=s.split(" ")
            if (ss[0].trim().equals("vt")){
                vertex_T.add(ss[1].toFloat())
                vertex_T.add(ss[2].toFloat())
            }else if(ss[0].trim().equals("f")){
                for(i in 1..3){
                    var index=ss[i].split("/")[1].toInt()-1
                    ver_min.add(vertex_T[2*index])
                    ver_min.add(vertex_T[2*index+1])
                }
            }
            s=br.readLine()
        }
        input.close()
        isr.close()
        br.close()
        result=ver_min.toFloatArray()

    }catch (e:IOException){
        e.printStackTrace()
    }
    return result
}

fun loadVNOBJ(fname:String):FloatArray?{
    val ver_min=ArrayList<Float>()
    var vertex_N=ArrayList<Float>()
    var result:FloatArray?=null
    try {
        val input=MyApplication.context.resources.assets.open(fname)
        val isr=InputStreamReader(input)
        val br=BufferedReader(isr)
        var s:String?=br.readLine()
        while(s!=null){
            var ss=s.split(" ")
            if (ss[0].trim().equals("vn")){
                vertex_N.add(ss[1].toFloat())
                vertex_N.add(ss[2].toFloat())
                vertex_N.add(ss[3].toFloat())
            }else if(ss[0].trim().equals("f")){
                for(i in 1..3){
                    var index=ss[i].split("/")[2].toInt()-1
                    ver_min.add(vertex_N[3*index])
                    ver_min.add(vertex_N[3*index+1])
                    ver_min.add(vertex_N[3*index+2])
                }
            }
            s=br.readLine()
        }
        input.close()
        isr.close()
        br.close()
        result=ver_min.toFloatArray()

    }catch (e:IOException){
        e.printStackTrace()
    }
    return result
}