package com.example.within_front.board

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.R
import com.example.within_front.base.BaseActivity
//import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class PostActivity : BaseActivity() {

//    private val client = OkHttpClient()

    private val commentContainer : RecyclerView by lazy{
        findViewById(R.id.comments)
    }

    private var commentList = mutableListOf<Comment>(
        Comment("SuperH0ng", "안녕하세요", "2022-08-13 | 16:03"),
        Comment("eunsun", "ㅎ2ㅎ2", "2022-08-13 | 16:05"),
        Comment("SuperH0ng", "안녕하세요", "2022-08-13 | 16:03"),
        Comment("eunsun", "ㅎ2ㅎ2", "2022-08-13 | 16:05"),
        Comment("SuperH0ng", "안녕하세요", "2022-08-13 | 16:03"),
        Comment("eunsun", "ㅎ2ㅎ2", "2022-08-13 | 16:05"),
        Comment("SuperH0ng", "안녕하세요", "2022-08-13 | 16:03"),
        Comment("eunsun", "ㅎ2ㅎ2", "2022-08-13 | 16:05")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        initRecyclerView()

        initNavigation("board")
    }
    private fun initRecyclerView(){
        commentContainer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        commentContainer.setHasFixedSize(true)
        commentContainer.adapter = CommentAdapter(this, commentList)
    }
}
