package com.example.within_front.myPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.R

class MyPostAdapter(val mContext: Context, val myPostList : MutableList<Post>) : RecyclerView.Adapter<MyPostAdapter.CustomViewHolder>() {

    class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val nickname = itemView.findViewById<TextView>(R.id.nickname)
        private val content = itemView.findViewById<TextView>(R.id.content)
        private val boardName = itemView.findViewById<TextView>(R.id.board_name)
        private val date = itemView.findViewById<TextView>(R.id.date)

        fun bind(myPost : Post){
            nickname.text = myPost.nickname
            content.text = myPost.content
            boardName.text = myPost.boardName
            date.text = myPost.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_post_item, parent, false)
        return CustomViewHolder(view).apply{

        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val post = myPostList[position]
        holder.apply{
            bind(post)
        }
    }

    override fun getItemCount(): Int {
        return myPostList.size
    }
}