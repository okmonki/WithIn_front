package com.example.within_front.my_page

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.within_front.R

class MyGroupReadActivity : AppCompatActivity() {
    private val backButton : ImageButton by lazy{
        findViewById(R.id.back_button)
    }
    private val createButton : ImageButton by lazy{
        findViewById(R.id.create_button)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_group_read)

        initBackButton()
        initCreateButton()
    }

    private fun initBackButton(){
        backButton.setOnClickListener{
            finish()
        }
    }
    private fun initCreateButton() {
        createButton.setOnClickListener {
            val intent = Intent(this, MyGroupUpdateActivity::class.java)
            startActivity(intent)
        }
    }
}