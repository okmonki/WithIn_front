package com.example.within_front.myPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.R

class MessageChatAdapter(val mContext: Context, val messageChatList : MutableList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class SentMessageHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val messageText = itemView.findViewById<TextView>(R.id.message_text_me)
        private val timeText = itemView.findViewById<TextView>(R.id.timestamp_me)

        fun bind(message : Message){
            messageText.text = message.content
            timeText.text = message.dateTime
        }
    }

    class ReceivedMessageHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val messageText = itemView.findViewById<TextView>(R.id.message_text_other)
        private val timeText = itemView.findViewById<TextView>(R.id.timestamp_other)
        private val nicknameText = itemView.findViewById<TextView>(R.id.nickname_other)

        fun bind(message : Message){
            messageText.text = message.content
            timeText.text = message.dateTime
            nicknameText.text = message.nickname
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder{
        return if(viewType == VIEW_TYPE_MESSAGE_SENT){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_me, parent, false)
            SentMessageHolder(view)
        } else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_other, parent, false)
            ReceivedMessageHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messageChatList[position]

        when(holder.itemViewType){
            VIEW_TYPE_MESSAGE_SENT -> (holder as SentMessageHolder).bind(message)
            VIEW_TYPE_MESSAGE_RECEIVED -> (holder as ReceivedMessageHolder).bind(message)
        }
    }

    override fun getItemCount(): Int {
        return messageChatList.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messageChatList[position]

        return if(message.nickname == ""){
            VIEW_TYPE_MESSAGE_SENT
        } else{
            VIEW_TYPE_MESSAGE_RECEIVED
        }
    }

    companion object{
        const val VIEW_TYPE_MESSAGE_SENT = 1
        const val VIEW_TYPE_MESSAGE_RECEIVED = 2
    }
}