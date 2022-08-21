package com.example.within_front.board

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.within_front.R
import com.example.within_front.base.BaseActivity
import okhttp3.*
import java.io.IOException
import java.time.LocalDateTime

class WriteANewPostActivity : BaseActivity() {

    val client = OkHttpClient()

    val backImageView: ImageView by lazy {
        findViewById(R.id.backImageView)
    }
    val writeTextView: TextView by lazy {
        findViewById(R.id.writeTextView)
    }
    val completeButton: AppCompatButton by lazy {
        findViewById(R.id.completeButton)
    }
    val titleEditText: EditText by lazy {
        findViewById(R.id.titleEditText)
    }
    val contentEditText: EditText by lazy {
        findViewById(R.id.contentEditText)
    }

    private val pref by lazy{
        getSharedPreferences(PostActivity.USER_INFO, MODE_PRIVATE)
    }
    var userId = 1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_post)
        // TODO
//        val userId = pref.getLong("user id", -1)

        val intent = intent
        // TODO
//        val boardId = intent.getLongExtra("boardId", 0)
        val boardId = 1L
        if (boardId == 0L) {
            Toast.makeText(this, "게시판 조회에 실패했습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
        initCompleteButton(boardId)
        initBackImageView()
        initNavigation("board")
    }

    private fun getInputTitle() : String {
        return titleEditText.text.toString()
    }

    private fun getInputContent() : String {
        return contentEditText.text.toString()
    }

    @SuppressLint("NewApi")
    private fun initCompleteButton(boardId : Long){
        completeButton.setOnClickListener {
            val title=getInputTitle()
            val content=getInputContent()
            val postData = "{\"title\" : \"${title}\", \"content\" : \"${content}\"}"
            val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postData)
            val postWriteRequest = Request.Builder().url("http://52.78.137.155:8080/post/boards/$boardId/post?userId=$userId").post(body).build()

            client.newCall(postWriteRequest).enqueue(object : Callback {
                override fun onFailure(call : Call, e: IOException) {
                    Log.d("connection error", "인터넷 연결이 불안정합니다.")
                    runOnUiThread {
                        Toast.makeText(
                            this@WriteANewPostActivity,
                            "인터넷 연결이 불안정합니다. 다시 시도해주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                override fun onResponse(call: Call, response: Response) {
                    if(response.code()==200){

                        Log.d("post write", "success")
                        runOnUiThread {
                            Toast.makeText(
                                this@WriteANewPostActivity,
                                "게시글이 정상적으로 등록되었습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        finish()
                    } else{
                        Log.d("fail", "boardId : $boardId / response code : ${response.code()} / post 도중 인터넷 연결 불안정")
                        runOnUiThread {
                            Toast.makeText(
                                this@WriteANewPostActivity,
                                "게시글 작성에 실패하였습니다. 다시 시도해주세요.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
            })

        }
    }

    private fun initBackImageView(){
        backImageView.setOnClickListener{
            finish()
        }
    }


}