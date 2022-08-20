package com.example.within_front.board

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
    val category : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_post)
        initCompleteButton(initView())

        initNavigation("board")
    }

    private fun getInputTitle() : String {
        return titleEditText.text.toString()
    }

    private fun getInputContent() : String {
        return contentEditText.text.toString()
    }

    private fun initView() : String{
        val intent=intent
        val category=intent.getStringExtra("category")
        return category!!
    }

    private fun initCompleteButton(category: String){
        completeButton.setOnClickListener {
            val title=getInputTitle()
            val content=getInputContent()
            val postData = "{\"title\" : \"${title}\", \"content\" : \"${content}\"}"
            val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postData)
            val postWriteRequest = Request.Builder().url("http://52.78.137.155:8080/boards/$category").post(body).build()

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
                    } else{
                        Log.d("connection error", "category : $category / response code : ${response.code()} / post 도중 인터넷 연결 불안정")
                        runOnUiThread {
                            Toast.makeText(
                                this@WriteANewPostActivity,
                                "인터넷 연결이 불안정합니다. 다시 시도해주세요.",
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