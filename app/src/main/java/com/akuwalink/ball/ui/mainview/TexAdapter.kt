package com.akuwalink.ball.ui.mainview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akuwalink.ball.MyApplication.Companion.now_user
import com.akuwalink.ball.R
import kotlin.concurrent.thread

class TexAdapter (val texList:ArrayList<Tex>):RecyclerView.Adapter<TexAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.skin_recycler,parent,false)
        val viewHolder=ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position=viewHolder.adapterPosition
            val tex=texList[position]
            thread {
                now_user.texId=tex.imageId
            }
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
       return texList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val tex=texList[position]
        holder.image.setImageResource(tex.imageId)
        holder.text.text=tex.text
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val image=view.findViewById(R.id.recycler_image) as ImageView
        val text=view.findViewById(R.id.recycler_text) as TextView

    }
}

class Tex(var imageId:Int,var text:String)