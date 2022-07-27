package com.example.within_front.myPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.R

class MessageAdapter(val mContext: Context, val messageList : MutableList<Message>) : RecyclerView.Adapter<MessageAdapter.CustomViewHolder>() {
    class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val nickname = itemView.findViewById<TextView>(R.id.nickname)
        private val content = itemView.findViewById<TextView>(R.id.content)
        private val dateTime = itemView.findViewById<TextView>(R.id.date_time)

        fun bind(message: Message){
            nickname.text = message.nickname
            content.text = message.content
            dateTime.text = message.dateTime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return CustomViewHolder(view).apply{

        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val message = messageList[position]
        holder.apply{
            bind(message)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
}