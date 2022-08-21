package com.example.within_front.board

data class Post(
    val postTitle : String,
    val author : String,
    val content : String,
    val commentCount : Int,
    val likeCount : Int,
    val postId : Long,
//    val boardName : String,
//    val date : String
)