package com.akuwalink.ball.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(var name:String,var pass:String, var star:Int,var now:Int,var texId:Int){
    @PrimaryKey(autoGenerate = true)
    var id:Long=0
}