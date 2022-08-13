package com.example.within_front.board

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.within_front.R

class BoardActivity : AppCompatActivity() {
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_post)
    }

    private fun getInputTitle() : String {
        return titleEditText.text.toString()
    }

    private fun getInputContent() : String {
        return contentEditText.text.toString()
    }

    private fun initCompleteButton(){
        completeButton.setOnClickListener {
            val title=getInputTitle()
            val content=getInputContent()
            val postData = "{\"title\" : \"${title}\", \"content\" : \"${content}\"}"

        }

    }

    }