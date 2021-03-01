package com.akuwalink.ball.logic.model.models

import android.content.Context
import android.opengl.GLES30
import com.akuwalink.ball.MyApplication
import com.akuwalink.ball.R
import com.akuwalink.ball.logic.model.Light
import com.akuwalink.ball.logic.model.Model
import com.akuwalink.ball.logic.physical.basic.CollisionModels
import com.akuwalink.ball.logic.physical.event.Event
import com.akuwalink.ball.util.*
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class Ball(point:FloatArray, vein:FloatArray,normal:FloatArray,c: Context):Model(point, vein, normal,c){

    var vertexShader=0
    var fragShader=0

    var finalMatrixHander=0
    var modelMatrixHander=0
    var cameraHander=0
    var lightHander=0

    var program=0
    var positionHander=0
    var veinHander=0
    var normalHander=0
    var ambientHander=0
    var diffuseHander=0
    var specularHander=0
    var shininessHander=0
    var lightModeHander=0

    lateinit var pointBuffer:FloatBuffer
    lateinit var veinBuffer: FloatBuffer
    lateinit var normalBuffer: FloatBuffer

    var pointBufferId=0
    var veinBufferId=0
    var normalBufferId=0

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

    init{
        ballInit()
        initShader()
        initPoint(point, vein, normal)
        setTex(R.drawable.ball_back_1)
    }

    fun ballInit(){
        modelInit(10f,10f,false,true,CollisionModels.getRectangle(0f,0f,0f))
    }

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

    fun initShader(){
        vertexShader= loadShader(GLES30.GL_VERTEX_SHADER,"eventment_vertex.sh",context.resources)
        fragShader= loadShader(GLES30.GL_FRAGMENT_SHADER,"eventment_frag.sh", context.resources)
        program= createProgram(vertexShader,fragShader)
        positionHander=GLES30.glGetAttribLocation(program,"inPosition")
        veinHander=GLES30.glGetAttribLocation(program,"inTex")
        normalHander=GLES30.glGetAttribLocation(program,"inNormal")
        ambientHander=GLES30.glGetAttribLocation(program,"inAmbient")
        diffuseHander=GLES30.glGetAttribLocation(program,"inDiffuse")
        specularHander=GLES30.glGetAttribLocation(program,"inSpecular")
        shininessHander=GLES30.glGetAttribLocation(program,"inShininess")

        lightModeHander=GLES30.glGetUniformLocation(program,"lightMode")
        finalMatrixHander=GLES30.glGetUniformLocation(program,"finalMatrix")
        modelMatrixHander=GLES30.glGetUniformLocation(program,"modelMatrix")
        cameraHander=GLES30.glGetUniformLocation(program,"cameraLocation")
        lightHander=GLES30.glGetUniformLocation(program,"lightLocation")

    }

    override fun rotate(angle:Float,x:Float,y:Float,z:Float){
        android.opengl.Matrix.rotateM(martix_self,0,angle,x,y,z)

    }
    fun initPoint(point:FloatArray, vein:FloatArray,normal:FloatArray){
        var buffIds=IntArray(3)
        GLES30.glGenBuffers(3,buffIds,0)
        pointBufferId=buffIds[0]
        veinBufferId=buffIds[1]
        normalBufferId=buffIds[2]

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


        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,pointBufferId)
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,point.size*4,pointBuffer,GLES30.GL_DYNAMIC_DRAW)
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,veinBufferId)
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,vein.size*4,veinBuffer,GLES30.GL_DYNAMIC_DRAW)
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,normalBufferId)
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,normal.size*4,normalBuffer,GLES30.GL_DYNAMIC_DRAW)
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,0)
        initVAO()
    }

    override fun setTex(resId:Int){
        texId=TextureUtil.getTextureId(resId,context)
    }

    override fun drawself(light: Light, matrix: Matrix){
        GLES30.glUseProgram(program)
        checkError("draw")
        GLES30.glUniformMatrix4fv(finalMatrixHander,1,false,matrix.getFinalMatrix(martix_self),0)
        checkError("draw")
        GLES30.glUniform3fv(lightHander,1,light.getPointArray(),0)
        checkError("draw")
        GLES30.glUniform3fv(cameraHander,1,matrix.cameraLocation,0)
        checkError("draw")
        GLES30.glUniformMatrix4fv(modelMatrixHander,1,false,martix_self,0)
        checkError("draw")
        /*GLES30.glVertexAttribPointer(positionHander,3, GLES30.GL_FLOAT,false,3*4,pointBuffer)
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
        GLES30.glBindVertexArray(vadId)
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,texId)
        GLES30.glActiveTexture(GLES30.GL_TEXTURE1)
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,shadowId)
        GLES30.glUniform1i(modelTexHander,0)
        GLES30.glUniform1i(shadowTexHander,1)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,count)
        GLES30.glBindVertexArray(0)*/

        GLES30.glVertexAttrib4fv(ambientHander,light.ambient.toFloatArray(),0)
        checkError("draw")
        GLES30.glVertexAttrib4fv(diffuseHander,light.diffuse.toFloatArray(),0)
        checkError("draw")
        GLES30.glVertexAttrib4fv(specularHander,light.specular.toFloatArray(),0)
        checkError("draw")
        GLES30.glVertexAttrib1f(shininessHander,rough)
        GLES30.glUniform1i(lightModeHander,light.light_mode)

        GLES30.glBindVertexArray(vadId)
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0)
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,texId)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,count)
        GLES30.glBindVertexArray(0)
    }

   /* override fun drawShadow(light: Light, matrix: Matrix) {
        GLES30.glUseProgram(program_s)
        GLES30.glUniformMatrix4fv(finalMatrix_s,1,false,matrix.getFinalShaderMatrix(martix_self),0)
        GLES30.glUniform3fv(lightHander_s,1,light.getPointArray(),0)
        GLES30.glUniformMatrix4fv(modelMatrix_s,1,false,martix_self,0)
        GLES30.glVertexAttribPointer(positionHander_s,3,GLES30.GL_FLOAT,false,3*4,pointBuffer)
        GLES30.glEnableVertexAttribArray(positionHander_s)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,count)
    }*/

    override fun collsionEvent(event: Event) {
        MyApplication.soundPlay.playSound("collision")
        //MyApplication.soundPlay.playSound("touch_button")
    }
}

