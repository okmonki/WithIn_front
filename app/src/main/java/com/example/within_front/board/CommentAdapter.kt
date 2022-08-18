package com.example.within_front.board

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.R
import com.example.within_front.board.Post

class CommentAdapter(val context: Context, private var commentList : MutableList<Comment>) : RecyclerView.Adapter<CommentAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val author = itemView.findViewById<TextView>(R.id.author)
        private val content = itemView.findViewById<TextView>(R.id.content)
        private val date = itemView.findViewById<TextView>(R.id.date)

        fun bind(comment : Comment){
            author.text = comment.author
            content.text = comment.content
            date.text = comment.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val comment = commentList[position]
        holder.apply{
            bind(comment)
        }
    }

    override fun getItemCount(): Int {
        Log.d("size", commentList.size.toString())
        return commentList.size
    }
}
