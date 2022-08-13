package com.example.within_front.board

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.R
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class PostActivity : AppCompatActivity() {

    private val client = OkHttpClient()

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
//        initRecyclerView()
        getComment(1)
    }

    private fun initRecyclerView(){
        commentContainer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        commentContainer.setHasFixedSize(true)
        commentContainer.adapter = CommentAdapter(this, commentList)
    }

    private fun getComment(postId : Long){
        val getCommentRequest = Request.Builder().addHeader("Content-Type", "application/json").url("http://52.78.137.155:8080/post/boards/1/comments").build()

        client.newCall(getCommentRequest).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("fail", "댓글 조회 실패")
                runOnUiThread{
                    Toast.makeText(
                        this@PostActivity,
                        "댓글 조회에 실패했습니다. 다시 시도해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.code() == 200){
                    commentList = mutableListOf()

                    val jsonArray = JSONArray(response.body()!!.string())

                    for(idx in 0 until jsonArray.length()){
                        val tempPost = jsonArray[idx] as JSONObject
                        val author = tempPost.getString("authorName")
                        val content = tempPost.getString("content")
                        val createdAt = tempPost.getString("createdAt")

                        val comment = Comment(author = author, content = content, date = createdAt)
                        commentList.add(comment)
                    }

                    Log.d("commentList", commentList.size.toString())
                    runOnUiThread{
                        initRecyclerView()
                    }
                }
            }
        })
    }
}
