package com.akuwalink.ball.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES30
import android.opengl.GLUtils
import com.akuwalink.ball.MyApplication
import com.akuwalink.ball.R
import java.io.IOException
import java.io.InputStream

object  TextureUtil{
    var min=GLES30.GL_NEAREST
    var mag=GLES30.GL_LINEAR
    var wrap_s=GLES30.GL_CLAMP_TO_EDGE
    var wrap_t=GLES30.GL_CLAMP_TO_EDGE

    fun getTextureId(vararg image:Int,context: Context):IntArray{
        val size=image.size
        var texture=IntArray(size)
        var result=IntArray(size)
        GLES30.glGenTextures(size,texture,0)
        val length=size-1
        for (i in 0..length) {
            result[i] = texture[i]
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, result[i])
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, min)
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, mag)
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, wrap_s)
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, wrap_t)
            var input: InputStream? = null
            var bitmap: Bitmap? = null
            try {
                input = context.resources.openRawResource(image[i])
                bitmap = BitmapFactory.decodeStream(input)
                GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0)
                input.close()
                bitmap.recycle()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                input?.close()
                bitmap?.recycle()
            }
        }
        return result
    }

    fun getTextureId(image:Int,context: Context):Int{
        var texture=IntArray(1)
        var result=0
        GLES30.glGenTextures(1,texture,0)
        result = texture[0]
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, result)
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, min)
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, mag)
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, wrap_s)
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, wrap_t)
        var input: InputStream? = null
        var bitmap: Bitmap? = null
        try {
            input = context.resources.openRawResource(image)
            bitmap = BitmapFactory.decodeStream(input)
            GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0)
            input.close()
            bitmap.recycle()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            input?.close()
            bitmap?.recycle()
        }

        return result
    }

    fun reset(){
        min=GLES30.GL_NEAREST
        mag=GLES30.GL_LINEAR
        wrap_s=GLES30.GL_CLAMP_TO_EDGE
        wrap_t=GLES30.GL_CLAMP_TO_EDGE
    }
}