package com.example.within_front.myPage

data class Message(
    val nickname : String, // 보낸 사람 닉네임
    val userId : Long = 0, // 상대방의 유저 아이디
    val content : String,
    val dateTime : String
)