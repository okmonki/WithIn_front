package com.example.within_front.myPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.R

class MessageBoxActivity : AppCompatActivity() {

    private val messageBoxContainer : RecyclerView by lazy{
        findViewById(R.id.messagebox_container)
    }

    private val messageList = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypost)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        messageBoxContainer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        messageBoxContainer.setHasFixedSize(true)

        messageBoxContainer.adapter = MessageAdapter(this, messageList)
    }
}