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

class PostAdapter(val context: Context, private var postList : MutableList<Post>) : RecyclerView.Adapter<PostAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val postTitle = itemView.findViewById<TextView>(R.id.post_title)
        private val content = itemView.findViewById<TextView>(R.id.content)
        private val author = itemView.findViewById<TextView>(R.id.author)
        private val commentCount = itemView.findViewById<TextView>(R.id.comment_count)
        private val likeCount = itemView.findViewById<TextView>(R.id.like_count)

//        private val boardName = itemView.findViewById<TextView>(R.id.board_name)
//        private val date = itemView.findViewById<TextView>(R.id.date)

        fun bind(post : Post){
            postTitle.text = post.postTitle
            author.text = post.author
            content.text = post.content
            commentCount.text = post.commentCount.toString()
            likeCount.text = post.likeCount.toString()


//            boardName.text = post.boardName
//            date.text = post.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val post = postList[position]
        holder.apply{
            bind(post)
        }
    }

    override fun getItemCount(): Int {
        Log.d("size", postList.size.toString())
        return postList.size
    }
}



//package com.example.within_front.board
//
//import android.content.Context
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.within_front.R
//import com.example.within_front.board.Post
//
//class PostAdapter(private val context: Context) : RecyclerView.Adapter<PostAdapter.CustomViewHolder>() {
//
//    var postList = mutableListOf<Post>()
//
//    inner class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
//        private val postTitle = itemView.findViewById<TextView>(R.id.post_title)
//        private val content = itemView.findViewById<TextView>(R.id.content)
//        private val author = itemView.findViewById<TextView>(R.id.author)
//        private val commentCount = itemView.findViewById<TextView>(R.id.comment_count)
//        private val likeCount = itemView.findViewById<TextView>(R.id.like_count)
//
////        private val boardName = itemView.findViewById<TextView>(R.id.board_name)
////        private val date = itemView.findViewById<TextView>(R.id.date)
//
//        fun bind(post : Post){
//            postTitle.text = post.postTitle
//            author.text = post.author
//            content.text = post.content
//            commentCount.text = post.commentCount.toString()
//            likeCount.text = post.likeCount.toString()
//
//
////            boardName.text = post.boardName
////            date.text = post.date
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
//        return CustomViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
////        val post = postList[position]
////        holder.apply{
////            bind(post)
////        }
//        holder.bind(postList[position])
//    }
//
//    override fun getItemCount(): Int {
//        Log.d("size", postList.size.toString())
//        return postList.size
//    }
//}