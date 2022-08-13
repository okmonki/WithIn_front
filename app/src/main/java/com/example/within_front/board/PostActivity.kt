package com.example.within_front.board

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.R
//import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class PostActivity : AppCompatActivity() {

//    private val client = OkHttpClient()

    private val commentContainer : RecyclerView by lazy{
        findViewById(R.id.comments)
    }

    private var commentList = mutableListOf<Comment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        initRecyclerView()
    }
    private fun initRecyclerView(){
        commentContainer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        commentContainer.setHasFixedSize(true)
        commentContainer.adapter = CommentAdapter(this, commentList)
    }
}
