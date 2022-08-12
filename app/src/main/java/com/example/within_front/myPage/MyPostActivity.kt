package com.example.within_front.myPage

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

class MyPostActivity : AppCompatActivity() {

    val client = OkHttpClient()

    private val myPostContainer : RecyclerView by lazy{
        findViewById(R.id.my_post_container)
    }

    private var myPostList = mutableListOf<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypost)
        // TODO getMyPost(userId)
    }

    private fun initRecyclerView(){
        myPostContainer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        myPostContainer.setHasFixedSize(true)

        myPostContainer.adapter = MyPostAdapter(this, myPostList)
    }

    private fun getMyPost(userId : Long){
        val getMyPostRequest = Request.Builder().addHeader("Content-Type", "application/json").url("http:localhost:8080/user/$userId/posts").build()

        client.newCall(getMyPostRequest).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("fail", "내가 쓴 글 조회 실패")
                runOnUiThread{
                    Toast.makeText(
                        this@MyPostActivity,
                        "내가 쓴 글 조회에 실패했습니다. 다시 시도해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.code() == 200){
                    myPostList = mutableListOf()

                    val jsonArray = JSONArray(response.body()!!.string())

                    for(idx in 0 until jsonArray.length()){
                        val tempPost = jsonArray[idx] as JSONObject
                        val author = tempPost.getString("authorNickname")
                        val content = tempPost.getString("content")
                        val createdAt = tempPost.getString("createdAt")
                        val board = tempPost.getString("boeadName")

                        val post = Post(nickname = author, boardName = board, content = content, date = createdAt)
                        myPostList.add(post)
                    }
                    myPostList.reverse()
                    Log.d("myPostList", myPostList.size.toString())
                    runOnUiThread{
                        initRecyclerView()
                    }
                }
            }
        })
    }
}