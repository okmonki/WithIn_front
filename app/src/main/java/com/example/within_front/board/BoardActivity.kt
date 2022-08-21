package com.example.within_front.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.board.PostAdapter
import com.example.within_front.R
import okhttp3.*
import com.example.within_front.base.BaseActivity
import com.example.within_front.login.SignupActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class BoardActivity : BaseActivity() {

    private val boardName : TextView by lazy {
        findViewById(R.id.board)
    }

    val client = OkHttpClient()

    val backImageButton: ImageButton by lazy {
        findViewById(R.id.arrowImg)
    }

    val pencilImageButton: ImageButton by lazy {
        findViewById(R.id.write_button)
    }

    private val postContainer : RecyclerView by lazy{
        findViewById(R.id.posts)
    }

    private var postList = mutableListOf<Post>()
    private var boardId = 0L

    val category : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        val intent = intent
        boardId = intent.getLongExtra("board id", 0)
        if (boardId == 0L) {
            Toast.makeText(this, "게시판 조회에 실패했습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
        setBoardName(boardId)
        initRecyclerView()
        initBackImageButton()
        initPencilImageButton(boardId)

        initNavigation("board")
    }

    override fun onResume() {
        super.onResume()
        getPost(boardId)
    }

    private fun initRecyclerView(){
        postContainer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        postContainer.setHasFixedSize(true)
        postContainer.adapter = PostAdapter(this, postList)
    }

    private fun getPost(boardId : Long){
        val getPostRequest = Request.Builder().addHeader("Content-Type", "application/json").url("http:52.78.137.155:8080/post/boards/$boardId").build()

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
                        val commentCount = tempPost.getInt("commentCount")
                        val likeCount = tempPost.getInt("likeCount")
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

    private fun setBoardName(boardId : Long) {

        val getBoardNameRequest = Request.Builder().url("http:52.78.137.155:8080/post/boards/$boardId/name").build()

        client.newCall(getBoardNameRequest).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("fail", "게시판 이름 조회 실패")
                runOnUiThread{
                    Toast.makeText(
                        this@BoardActivity,
                        "게시판 이름 조회에 실패했습니다. 다시 시도해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.code() == 200){
                    runOnUiThread {
                        boardName.text = response.body()!!.string()
                    }
        } } })
    }

    private fun initBackImageButton(){
        backImageButton.setOnClickListener{
            finish()
        }
    }

    private fun initPencilImageButton(boardId: Long) {
        pencilImageButton.setOnClickListener {
            val intent = Intent(this, WriteANewPostActivity::class.java)
            intent.putExtra("board id", boardId)
            startActivity(intent)
        }
    }
}
