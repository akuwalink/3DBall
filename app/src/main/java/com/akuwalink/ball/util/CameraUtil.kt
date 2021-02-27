package com.akuwalink.ball.util

import android.opengl.Matrix
import com.akuwalink.ball.logic.model.Light

class Matrix(){
    //分别是投影矩阵，朝向矩阵，最终矩阵，相机位置
    var matrix=FloatArray(16)
    var dire_m=FloatArray(16)
    var final_m=FloatArray(16)
    var cameraLocation=FloatArray(3)

    //设置相机位置
    fun setCamera(cx:Float,cy:Float,cz:Float,ex:Float,ey:Float,ez:Float,ux:Float,uy:Float,uz:Float){
        Matrix.setLookAtM(dire_m,0,cx,cy,cz,ex,ey,ez,ux,uy,uz)
        cameraLocation[0]=cx
        cameraLocation[1]=cy
        cameraLocation[2]=cz
    }

    //透视投影
    fun setPresp(left:Float,right:Float,bottom:Float,top:Float,near:Float,far:Float){
        Matrix.frustumM(matrix,0,left,right,bottom,top,near,far)
    }
    //平行投影
    fun setParallel(left:Float,right:Float,bottom:Float,top:Float,near:Float,far:Float){
        Matrix.orthoM(matrix,0,left,right, bottom, top, near, far)
    }

    //获取最终变换矩阵
    fun getFinalMatrix(matrix_self:FloatArray):FloatArray{
        Matrix.multiplyMM(final_m,0,dire_m,0,matrix_self,0)
        Matrix.multiplyMM(final_m,0,matrix,0,final_m,0)
        return final_m
    }


}