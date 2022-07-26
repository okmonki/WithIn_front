package com.example.within_front.board

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.R
import okhttp3.*
import com.example.within_front.base.BaseActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.time.LocalDateTime

class PostActivity : BaseActivity() {

    private val client = OkHttpClient()

    // TextView
    private val boardName : TextView by lazy {
        findViewById(R.id.board)
    }
    private val postTitle: TextView by lazy {
        findViewById(R.id.title)
    }
    private val author: TextView by lazy {
        findViewById(R.id.nickname)
    }
    private val content: TextView by lazy {
        findViewById(R.id.content)
    }
    private val commentCount: TextView by lazy {
        findViewById(R.id.comment_count)
    }
    private val likeCount: TextView by lazy {
        findViewById(R.id.like_count)
    }
    private val date: TextView by lazy {
        findViewById(R.id.date)
    }
    private val writeCommentBtn: ImageButton by lazy {
        findViewById(R.id.write_comment_btn)
    }
    private val likeBtn : ImageButton by lazy {
        findViewById(R.id.like_img)
    }
    private val getComment : EditText by lazy {
        findViewById(R.id.get_comment)
    }
    private val backImageButton : ImageButton by lazy {
        findViewById(R.id.arrowImg)
    }
    private val commentAdapter : CommentAdapter by lazy {
        CommentAdapter(this, commentList)
    }


    private val pref by lazy {
        getSharedPreferences(USER_INFO, MODE_PRIVATE)
    }
    private var userId = 0L
    private var userNickname = ""


    private val commentContainer : RecyclerView by lazy{
        findViewById(R.id.comments)
    }

    private var commentList = mutableListOf<Comment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        val intent = intent
        userId = pref.getLong("user id", -1)
        userNickname = pref.getString("user nickname", "").toString()

        // TODO default 0으로 바꿔주어야 함
        val postId = intent.getLongExtra("postId", 0)
        if(postId == 0L){
            Toast.makeText(this, "게시글 조회에 실패했습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
        getComments(postId)
        getPost(postId)
        initWriteCommentButton(postId)

        initLikeButton(postId)
        initBackImageButton()
        initNavigation("board")
    }

    private fun initRecyclerView(){
        commentContainer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        commentContainer.adapter = commentAdapter
    }

    private fun getComments(postId : Long){
        val getCommentRequest = Request.Builder().addHeader("Content-Type", "application/json").url("http://52.78.137.155:8080/post/boards/$postId/comments").build()

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
                        val tempComment = jsonArray[idx] as JSONObject
                        val author = tempComment.getString("authorName")
                        val content = tempComment.getString("content")
                        val createdAt = tempComment.getString("createdAt").substring(0..9)
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


    private fun getPost(postId : Long){
        val getPostRequest = Request.Builder().addHeader("Content-Type", "application/json").url("http://52.78.137.155:8080/post/boards/posts/$postId").build()

        client.newCall(getPostRequest).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("fail", "게시물 조회 실패")
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
                    //commentList = mutableListOf()
                    val tempPost = JSONObject(response.body()!!.string())

                    runOnUiThread {
                        boardName.text = tempPost.getString("boardName").plus(" 게시판")
                        postTitle.text = tempPost.getString("title")
                        author.text = tempPost.getString("authorNickname")
                        content.text = tempPost.getString("content")
                        commentCount.text = tempPost.getInt("commentCount").toString()
                        likeCount.text = tempPost.getInt("likeCount").toString()
                        date.text = tempPost.getString("createdAt").substring(0..9)
                    }
                }
            }
        })
    }

    private fun initWriteCommentButton(postId: Long){
        writeCommentBtn.setOnClickListener {
            Log.d("click", "btn")
            // {"content": "댓글"}

            if(getComment.text.isNotEmpty()){
                val commentData = "{\"content\": \"${getComment.text}\"}"
                val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), commentData)
                val writeCommentRequest = Request.Builder().addHeader("Content-Type", "application/json").url("http:52.78.137.155:8080/post/boards/$postId/comments?authorId=$userId").post(body).build()

                client.newCall(writeCommentRequest).enqueue(object : Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        Log.d("fail", "댓글 작성 실패")
                        runOnUiThread{
                            Toast.makeText(
                                this@PostActivity,
                                "댓글 작성에 실패하였습니에. 다시 시도해주세요.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    @SuppressLint("NewApi")
                    override fun onResponse(call: Call, response: Response) {
                        if(response.code() == 200){
                            Log.d("success", "댓글 작성 성공")
                            // TODO author에 작성자 이름 뜨게.. << 이것도 user
                            commentList.add(Comment(author = userNickname, content = getComment.text.toString(), date = LocalDateTime.now().toString().substring(11..15)))
                            Log.d("list", commentList.lastOrNull().toString())
                            runOnUiThread {
                                commentAdapter.notifyItemInserted(commentList.size)
                                commentContainer.smoothScrollToPosition(commentList.size)
                                commentCount.text = (commentCount.text.toString().toInt()+1).toString()
                                getComment.setText("")
                            }
                        }
                    }
                })
            }
        }
    }

    private fun initLikeButton(postId: Long){
        likeBtn.setOnClickListener {
            val getIsLikedRequest = Request.Builder().url("http:52.78.137.155:8080/post/boards/${postId}/isLiked?userId=$userId").build()

            client.newCall(getIsLikedRequest).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("fail", "게시판 좋아요 조회 실패")
                    runOnUiThread{
                        Toast.makeText(
                            this@PostActivity,
                            "게시판 좋아요 조회에 실패했습니다. 다시 시도해주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    if(response.code() == 200){
                        // 좋아요 누른 적 있으면 true
                        if (response.body()!!.string().toBoolean()) {
                            runOnUiThread {
                                likeBtn.setImageResource(R.drawable.heart)
                            }
                            val likesRequest = Request.Builder().addHeader("Content-Type", "application/json").url("http:52.78.137.155:8080/post/boards/$postId/unlikes?userId=$userId").build()

                            client.newCall(likesRequest).enqueue(object: Callback {
                                override fun onFailure(call: Call, e: IOException) {
                                    Log.d("fail", "좋아요 취소 실패")
                                    runOnUiThread {
                                        Toast.makeText(
                                            this@PostActivity,
                                            "좋아요 취소를 실패했습니다. 다시 시도해주세요.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                                override fun onResponse(call: Call, response: Response) {
                                    if (response.code() == 200) {
                                        val count = response.body()!!.string()
                                        runOnUiThread {
                                            likeBtn.setImageResource(R.drawable.heart_empty)
                                            likeCount.text = count.toString()
                                        }
                                    } else{
                                        Log.d("like post fail", response.code().toString())
                                    }
                                }
                            })
                        } else {

                            val likesRequest = Request.Builder().addHeader("Content-Type", "application/json").url("http:52.78.137.155:8080/post/boards/$postId/likes?userId=$userId").build()
                            client.newCall(likesRequest).enqueue(object: Callback {
                                override fun onFailure(call: Call, e: IOException) {
                                    Log.d("fail", "좋아요 실패")
                                    runOnUiThread {
                                        Toast.makeText(
                                            this@PostActivity,
                                            "좋아요를 실패했습니다. 다시 시도해주세요.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                                override fun onResponse(call: Call, response: Response) {
                                    if (response.code() == 200) {
                                        val count = response.body()!!.string()
                                        runOnUiThread {
                                            likeBtn.setImageResource(R.drawable.heart)
                                            likeCount.text = count
                                        }
                                    } else{
                                        Log.d("unlike fail", response.code().toString())
                                    }
                                }
                            })
                        }
                    } else{
                        Log.d("fail", "게시판 좋아요 조회 실패, ${response.code()}")
                    }
                }
            })
        }
    }

    private fun initBackImageButton(){
        backImageButton.setOnClickListener{
            finish()
        }
    }

    /**
     * 현재 포커스된 뷰의 영역이 아닌 다른 곳을 클릭 시 키보드를 내리고 포커스 해제
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val focusView = currentFocus
        if (focusView != null) {
            val rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = ev.x.toInt()
            val y = ev.y.toInt()
            if (!rect.contains(x, y)) {
                val imm: InputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                if (imm != null) imm.hideSoftInputFromWindow(focusView.windowToken, 0)
                focusView.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    companion object{
        const val USER_INFO = "user info"
    }
}
