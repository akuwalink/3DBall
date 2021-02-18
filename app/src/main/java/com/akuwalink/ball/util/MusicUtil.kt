package com.akuwalink.ball.util

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import com.akuwalink.ball.MyApplication

class MusicUtil(){
    lateinit var sp:SoundPool
    var volume:Float=0F
    var mp:MediaPlayer= MediaPlayer()
    val am:AudioManager=MyApplication.context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    var max=am.getStreamMaxVolume(AudioManager.STREAM_MUSIC) as Float

    lateinit var soundMap: HashMap<String,Int>

    fun soundInit(max:Int,audio:AudioAttributes){
        sp= with(SoundPool.Builder(),{
            setMaxStreams(max)
            setAudioAttributes(audio)
            build()
        })
        soundMap= HashMap()
    }

    fun updataVolume(){
        var now=am.getStreamVolume(AudioManager.STREAM_MUSIC) as Float
        volume=now/max
    }

    fun addSound(sign:String,resId:Int){
        soundMap.put(sign,resId)
        sp?.load(MyApplication.context,resId,1)
    }

    fun addSoundList(hashMap: HashMap<String,Int>){
        for (i in hashMap){
            addSound(i.key,i.value)
        }
    }

    fun stopSound(sign: String){
        if(soundMap[sign]==null) return
        sp?.stop(soundMap[sign]!!)
    }

    fun releaseASound(sign: String){
        if(soundMap[sign]==null) return
        soundMap.remove(sign)
        sp?.resume(soundMap[sign]!!)
    }

    fun releaseSound(){
        soundMap.clear()
        sp?.release()
    }

    fun playSound(resId:Int,left:Float,right:Float,pro:Int,loop:Int,rate:Float){
        sp?.play(resId,left,right,pro,loop,rate)
    }

    fun playSound(sign: String,left:Float,right:Float){
        if(soundMap[sign]==null) return
        playSound(soundMap[sign]!!,left,right,1,0,1.0F)
    }

    fun playSound(sign: String){
        if(soundMap[sign]==null) return
        playSound(soundMap[sign]!!,volume,volume,1,0,1.0F)
    }

    fun startMedia(resId:Int){
        mp.reset()
        mp= MediaPlayer.create(MyApplication.context,resId)
        mp.start()
    }

    fun pauseMedia(){
        mp.pause()
    }

    fun stopMedia(){
        mp.stop()
    }

    fun resetMedia(){
        mp.reset()
    }
}