package com.akuwalink.ball.logic.model.models

import android.content.Context
import android.opengl.GLES30
import com.akuwalink.ball.MyApplication
import com.akuwalink.ball.R
import com.akuwalink.ball.logic.model.Light
import com.akuwalink.ball.logic.model.Model
import com.akuwalink.ball.logic.physical.basic.CollisionModels
import com.akuwalink.ball.util.Matrix
import com.akuwalink.ball.util.TextureUtil
import com.akuwalink.ball.util.createProgram
import com.akuwalink.ball.util.loadShader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class Wall(point:FloatArray, vein:FloatArray,normal:FloatArray,c:Context):Model(point, vein, normal,c){
    /**点着色器和片元着色器的引用定义
     */
    var vertexShader=0
    var fragShader=0
    /**
     * 最终变换矩阵引用，模型变换矩阵引用，相机位置引用，光线位置引用
     */
    var finalMatrixHander=0
    var modelMatrixHander=0
    var cameraHander=0
    var lightHander=0
    /**
     * 着色程序引用，点位置，纹理，法向引用，环境光，镜面光，反射光引用，光线粗糙度，光线模式
     */
    var program=0
    var positionHander=0
    var veinHander=0
    var normalHander=0
    var ambientHander=0
    var diffuseHander=0
    var specularHander=0
    var shininessHander=0
    var lightModeHander=0

    /**
     * 将导入到OpenGLes的buffer
     */
    lateinit var pointBuffer:FloatBuffer
    lateinit var veinBuffer: FloatBuffer
    lateinit var normalBuffer: FloatBuffer

    /**
     * 各缓冲区Id
     */
    var pointBufferId=0
    var veinBufferId=0
    var normalBufferId=0

    /**
     * 模型总点数，纹理id
     */
    var count:Int=0
    var vadId=0

    /*var modelTexHander=0
    var shadowTexHander=0
    var finalShadowMatrixHander=0

    var program_s=0
    var finalMatrix_s=0
    var modelMatrix_s=0
    var positionHander_s=0
    var lightHander_s=0
    var vertexShader_s=0
    var fragShader_s=0*/

    /**
     * 初始化模型，着色器，加载模型数据，设置纹理ID
     */
    init{
        ballInit()
        initShader()
        initPoint(point, vein, normal)
        setTex(R.drawable.wall_back)
    }

    /**
     * 调用父类modelInit进行初始化
     */
    fun ballInit(){
        modelInit(10f,10f,false,false, CollisionModels.getRectangle(0.25f,2.5f,2.5f))
    }

    /**
     * 初始化模型缓冲区，绑定点，纹理和法向量
     */
    fun initVAO(){
        var vaoIds=IntArray(1)
        GLES30.glGenVertexArrays(1,vaoIds,0)
        vadId=vaoIds[0]
        GLES30.glBindVertexArray(vadId)
        GLES30.glEnableVertexAttribArray(positionHander)
        GLES30.glEnableVertexAttribArray(veinHander)
        GLES30.glEnableVertexAttribArray(normalHander)

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,pointBufferId)
        GLES30.glVertexAttribPointer(positionHander,3,GLES30.GL_FLOAT,false,3*4,0)
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,veinBufferId)
        GLES30.glVertexAttribPointer(veinHander,2,GLES30.GL_FLOAT,false,2*4,0)
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,normalBufferId)
        GLES30.glVertexAttribPointer(normalHander,3,GLES30.GL_FLOAT,false,3*4,0)

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,0)
        GLES30.glBindVertexArray(0)
    }

    /**
     * 加载着色器文件，链接程序，获取着色器文件内的变量引用
     */
    fun initShader(){
        vertexShader= loadShader(GLES30.GL_VERTEX_SHADER,"eventment_vertex.sh", context.resources)
        fragShader= loadShader(GLES30.GL_FRAGMENT_SHADER,"eventment_frag.sh", context.resources)
        program= createProgram(vertexShader,fragShader)
        positionHander= GLES30.glGetAttribLocation(program,"inPosition")
        veinHander= GLES30.glGetAttribLocation(program,"inTex")
        normalHander= GLES30.glGetAttribLocation(program,"inNormal")
        ambientHander= GLES30.glGetAttribLocation(program,"inAmbient")
        diffuseHander= GLES30.glGetAttribLocation(program,"inDiffuse")
        specularHander= GLES30.glGetAttribLocation(program,"inSpecular")
        shininessHander= GLES30.glGetAttribLocation(program,"inShininess")

        lightModeHander= GLES30.glGetUniformLocation(program,"lightMode")
        finalMatrixHander=GLES30.glGetUniformLocation(program,"finalMatrix")
        modelMatrixHander=GLES30.glGetUniformLocation(program,"modelMatrix")
        cameraHander=GLES30.glGetUniformLocation(program,"cameraLocation")
        lightHander=GLES30.glGetUniformLocation(program,"lightLocation")
        /*modelTexHander=GLES30.glGetUniformLocation(program,"sTexture")
        shadowTexHander=GLES30.glGetUniformLocation(program,"sTexture1")
        finalShadowMatrixHander=GLES30.glGetUniformLocation(program,"finalShadowMatrix")

        vertexShader_s= loadShader(GLES30.GL_VERTEX_SHADER,"shadow_vertex.sh",MyApplication.context.resources)
        fragShader_s= loadShader(GLES30.GL_FRAGMENT_SHADER,"shadow_frag.sh",MyApplication.context.resources)
        program_s= createProgram(vertexShader_s,fragShader_s)
        finalMatrix_s=GLES30.glGetUniformLocation(program_s,"finalMatrix")
        positionHander_s=GLES30.glGetAttribLocation(program_s,"inPosition")
        lightHander_s=GLES30.glGetUniformLocation(program_s,"lightLocation")
        modelMatrix_s=GLES30.glGetUniformLocation(program_s,"modelMatrix")*/
    }

    /**
     * @param point 点的float数组
     * @param vein 纹理数组
     * @param normal 法向量数组
     * 加载模型数据
     */
    fun initPoint(point:FloatArray, vein:FloatArray,normal:FloatArray){


        count=point.size/3
        val pbb=ByteBuffer.allocateDirect(point.size*4)
        pbb.order(ByteOrder.nativeOrder())
        pointBuffer=pbb.asFloatBuffer()
        pointBuffer.put(point).position(0)


        val vbb=ByteBuffer.allocateDirect(vein.size*4)
        vbb.order(ByteOrder.nativeOrder())
        veinBuffer=vbb.asFloatBuffer()
        veinBuffer.put(vein).position(0)


        val nbb=ByteBuffer.allocateDirect(normal.size*4)
        nbb.order(ByteOrder.nativeOrder())
        normalBuffer=nbb.asFloatBuffer()
        normalBuffer.put(normal).position(0)


        var buffIds=IntArray(3)
        GLES30.glGenBuffers(3,buffIds,0)
        pointBufferId=buffIds[0]
        veinBufferId=buffIds[1]
        normalBufferId=buffIds[2]
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,pointBufferId)
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,point.size*4,pointBuffer,GLES30.GL_STATIC_DRAW)
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,veinBufferId)
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,vein.size*4,veinBuffer,GLES30.GL_STATIC_DRAW)
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,normalBufferId)
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,normal.size*4,normalBuffer,GLES30.GL_STATIC_DRAW)
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,0)
        initVAO()
    }


    /**
     * @param resId 模型的纹理id
     * 加载自定义模型纹理
     */
    override fun setTex(resId:Int){
        texId= TextureUtil.getTextureId(resId,context)
    }


    /**
     * @param light 光，获取光位置等一系列变量
     * @param matrix 自定义matrix，从中获取最终变换矩阵
     */
    override fun drawself(light: Light, matrix: Matrix){
        GLES30.glUseProgram(program)
        GLES30.glUniformMatrix4fv(finalMatrixHander,1,false,matrix.getFinalMatrix(martix_self),0)
        GLES30.glUniform3fv(lightHander,1,light.getPointArray(),0)
        GLES30.glUniform3fv(cameraHander,1,matrix.cameraLocation,0)
        GLES30.glUniformMatrix4fv(modelMatrixHander,1,false,martix_self,0)

       /* GLES30.glVertexAttribPointer(positionHander,3, GLES30.GL_FLOAT,false,3*4,pointBuffer)
       GLES30.glVertexAttribPointer(veinHander,2, GLES30.GL_FLOAT,false,2*4,veinBuffer)
       GLES30.glVertexAttribPointer(normalHander,3, GLES30.GL_FLOAT,false,3*4,normalBuffer)
       GLES30.glVertexAttrib4fv(ambientHander,light.ambient.toFloatArray(),0)
       GLES30.glVertexAttrib4fv(diffuseHander,light.diffuse.toFloatArray(),0)
       GLES30.glVertexAttrib4fv(specularHander,light.specular.toFloatArray(),0)
       GLES30.glVertexAttrib1f(shininessHander,rough)
       GLES30.glUniform1i(lightModeHander,light.light_mode)

       GLES30.glEnableVertexAttribArray(positionHander)
       GLES30.glEnableVertexAttribArray(veinHander)
       GLES30.glEnableVertexAttribArray(normalHander)
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0)
       GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,texId)

       GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,count)*/

        GLES30.glVertexAttrib4fv(ambientHander,light.ambient.toFloatArray(),0)
        GLES30.glVertexAttrib4fv(diffuseHander,light.diffuse.toFloatArray(),0)
        GLES30.glVertexAttrib4fv(specularHander,light.specular.toFloatArray(),0)
        GLES30.glVertexAttrib1f(shininessHander,rough)
        GLES30.glUniform1i(lightModeHander,light.light_mode)

        GLES30.glBindVertexArray(vadId)
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0)
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,texId)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,count)
        GLES30.glBindVertexArray(0)
    }

    /*override fun drawShadow(light: Light, matrix: Matrix) {
        GLES30.glUseProgram(program_s)
        GLES30.glUniformMatrix4fv(finalMatrix_s,1,false,matrix.getFinalShaderMatrix(martix_self),0)
        GLES30.glUniform3fv(lightHander_s,1,light.getPointArray(),0)
        GLES30.glUniformMatrix4fv(modelMatrix_s,1,false,martix_self,0)
        GLES30.glVertexAttribPointer(positionHander_s,3,GLES30.GL_FLOAT,false,3*4,pointBuffer)
        GLES30.glEnableVertexAttribArray(positionHander_s)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,count)
    }*/
}
