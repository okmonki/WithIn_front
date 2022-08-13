package com.example.within_front.board

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.board.PostAdapter
import com.example.within_front.R
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class BoardActivity : AppCompatActivity() {

    private val client = OkHttpClient()

    private val postContainer : RecyclerView by lazy{
        findViewById(R.id.posts)
    }

    private var postList = mutableListOf<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
//        initRecyclerView()
        // TODO() getPost()
    }
    private fun initRecyclerView(){
        postContainer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        postContainer.setHasFixedSize(true)
        postContainer.adapter = PostAdapter(this, postList)
    }


    private fun getPost(category : String){
        val getPostRequest = Request.Builder().addHeader("Content-Type", "application/json").url("http:52.78.137.155:8080/post/boards/$category").build()

        client.newCall(getPostRequest).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("fail", "게시물 조회 실패")
                runOnUiThread{
                    Toast.makeText(
                        this@BoardActivity,
                        "게시물 조회에 실패했습니다. 다시 시도해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.code() == 200){
                    postList = mutableListOf()

                    val jsonArray = JSONArray(response.body()!!.string())

                    for(idx in 0 until jsonArray.length()){
                        val tempPost = jsonArray[idx] as JSONObject

                        val postTitle = tempPost.getString("title")
                        val author = tempPost.getString("authorNickname")
                        val content = tempPost.getString("content")
                        val commentCount = 1
                        val likeCount = tempPost.getInt("liked")
                        val postId = tempPost.getLong("id")

                        val post = Post(postTitle = postTitle, author = author,
                            content = content, likeCount = likeCount,
                            commentCount = commentCount, postId = postId)
                        postList.add(post)
                    }
                    Log.d("postList", postList.size.toString())
                    runOnUiThread{
                        initRecyclerView()
                    }
                }
            }
        })
    }
}