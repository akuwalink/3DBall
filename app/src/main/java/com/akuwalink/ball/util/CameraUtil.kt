package com.akuwalink.ball.util

import android.opengl.Matrix

class MatrixUtil(){
    //分别是投影矩阵，朝向矩阵，最终矩阵，当前变换矩阵，矩阵变换堆，栈顶
    var matrix=FloatArray(16)
    var dire_m=FloatArray(16)
    var final_m=FloatArray(16)
    var now_m=FloatArray(16)
    var stack_m=Array(16){FloatArray(16)}
    var top=-1
    //重置矩阵堆
    fun resetStack(){
        now_m= FloatArray(16)
        top=-1
        Matrix.setRotateM(now_m,0,0F,1F,0F,1F)
    }
    //设置相机位置
    fun setCamera(cx:Float,cy:Float,cz:Float,ex:Float,ey:Float,ez:Float,ux:Float,uy:Float,uz:Float){
        Matrix.setLookAtM(dire_m,0,cx,cy,cz,ex,ey,ez,ux,uy,uz)
    }
    //透视投影
    fun setPresp(left:Float,right:Float,bottom:Float,top:Float,near:Float,far:Float){
        Matrix.frustumM(matrix,0,left,right,bottom,top,near,far)
    }
    //平行投影
    fun setParallel(left:Float,right:Float,bottom:Float,top:Float,near:Float,far:Float){
        Matrix.orthoM(matrix,0,left,right, bottom, top, near, far)
    }
    //进栈
    fun inStack(){
        if(top==15) return
        top++
        for(i in 0..15){
            stack_m[top][i]=now_m[i]
        }
    }
    //出栈
    fun outStack(){
        if(top==-1) return
        for (i in 0..15){
            now_m[i]=stack_m[top][i]
        }
        top--
    }
    //平行变换
    fun translate(x:Float,y:Float,z:Float){
        Matrix.translateM(now_m,0,x,y,z)
    }
    //旋转变换
    fun rotate(angle:Float,x:Float,y:Float,z:Float){
        Matrix.rotateM(now_m,0,angle,x,y,z)
    }
    //缩放变换
    fun scale(x:Float,y:Float,z:Float){
        Matrix.scaleM(now_m,0,x,y,z)
    }
    //获取最终变换矩阵
    fun getFinalMatrix():FloatArray{
        Matrix.multiplyMM(final_m,0,dire_m,0,now_m,1)
        Matrix.multiplyMM(final_m,0,matrix,0,final_m,0)
        return final_m
    }
}