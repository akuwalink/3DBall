package com.akuwalink.ball.logic.physical.event

import com.akuwalink.ball.logic.model.Model
import com.akuwalink.ball.logic.model.World

class Event{

    var model:Model?=null
    var collision_flag=false
    var collision_dire:Int=0
    var world:World?=null
}