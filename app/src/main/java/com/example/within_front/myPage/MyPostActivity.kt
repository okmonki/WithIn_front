package com.example.within_front.myPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.R

class MyPostActivity : AppCompatActivity() {

    private val myPostContainer : RecyclerView by lazy{
        findViewById(R.id.my_post_container)
    }

    private val myPostList = mutableListOf<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypost)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        myPostContainer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        myPostContainer.setHasFixedSize(true)

        myPostContainer.adapter = MyPostAdapter(this, myPostList)
    }
}