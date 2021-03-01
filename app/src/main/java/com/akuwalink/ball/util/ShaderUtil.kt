package com.akuwalink.ball.util

import android.content.res.Resources
import android.opengl.GLES30
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.RuntimeException

fun checkError(er:String){
    var error=GLES30.glGetError()
    while (error!=GLES30.GL_NO_ERROR){
        Log.e("GLES30_ERROR",er+"error"+error)
        throw RuntimeException(er+"glError"+error)
        error=GLES30.glGetError()
    }
}

fun loadShader(type:Int,fname:String,r:Resources):Int{
    var result=""
    var shader= GLES30.glCreateShader(type)
    try {
        var isr:InputStream=r.assets.open(fname)
        var i:Int=isr.read()
        var baos=ByteArrayOutputStream()
        while (i!=-1){
            baos.write(i)
            i=isr.read()
        }
        var buff=baos.toByteArray()
        baos.close()
        isr.close()
        result=String(buff,Charsets.UTF_8)
        result=result.replace("\r\n","\n")

    }catch (e:IOException){
        e.printStackTrace()
    }

    if (shader!=0){
        GLES30.glShaderSource(shader,result)
        GLES30.glCompileShader(shader)
        var state=IntArray(1)
        GLES30.glGetShaderiv(shader,GLES30.GL_COMPILE_STATUS,state,0)
        if(state[0]==0){
            Log.e("GLES30_ERROR","compile error")
            Log.e("GLES30_ERROR",fname+GLES30.glGetShaderInfoLog(shader))
            GLES30.glDeleteShader(shader)
            shader=0
        }
    }
    return shader
}

fun createProgram(v_Shader:Int,f_Shader:Int):Int{
    if ((v_Shader==0)||(f_Shader==0))return 0

    var program=GLES30.glCreateProgram()
    if(program!=0){
        GLES30.glAttachShader(program,v_Shader)
        checkError("attach vertex")
        GLES30.glAttachShader(program,f_Shader)
        checkError("attach frag")
        GLES30.glLinkProgram(program)
        var state=IntArray(1)
        GLES30.glGetProgramiv(program,GLES30.GL_LINK_STATUS,state,0)
        if(state[0]==0){
            Log.e("GLES30_ERROR","link error")
            GLES30.glDeleteProgram(program)
            program=0
        }
    }
    return program
}
