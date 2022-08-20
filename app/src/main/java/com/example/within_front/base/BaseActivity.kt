package com.example.within_front.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.within_front.R
import com.example.within_front.board.BelongActivity
import com.example.within_front.menu.MenuActivity
import com.example.within_front.myPage.MyPageActivity

open class BaseActivity : AppCompatActivity() {
    private val menuIcon : ImageButton by lazy{
        findViewById(R.id.menu_icon)
    }

    private val boardIcon : ImageButton by lazy{
        findViewById(R.id.board_icon)
    }

    private val myPageIcon : ImageButton by lazy{
        findViewById(R.id.mypage_icon)
    }

    fun initNavigation(temp : String){
        menuIcon.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        boardIcon.setOnClickListener {
            val intent = Intent(this, BelongActivity::class.java)
            startActivity(intent)
        }

        myPageIcon.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }

        when(temp){
            "menu" -> menuIcon.isEnabled = false
            "board" -> boardIcon.isEnabled = false
            "myPage" -> myPageIcon.isEnabled = false
        }
    }
}