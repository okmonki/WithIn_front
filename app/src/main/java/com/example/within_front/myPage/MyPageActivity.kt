package com.example.within_front.myPage

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.within_front.login.LoginActivity
import com.example.within_front.R
import com.example.within_front.base.BaseActivity
import com.google.firebase.auth.FirebaseAuth

class MyPageActivity : BaseActivity() {
    private val pref by lazy{
        getSharedPreferences(USER_INFO, MODE_PRIVATE)
    }

    private val myGroupButton: AppCompatButton by lazy {
        findViewById(R.id.my_group_button)
    }
    private val myPostButton: AppCompatButton by lazy {
        findViewById(R.id.my_post_button)
    }
    private val messageBoxButton: AppCompatButton by lazy {
        findViewById(R.id.message_box_button)
    }
    private val logoutButton: AppCompatButton by lazy {
        findViewById(R.id.log_out_button)
    }
    private var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_page)

        //Log.d("saved user id", pref.getLong("user id", -1).toString())
        initMyGroupButton()
        initMyPostButton()
        initMessageBoxButton()
        initLogoutButton()

        initNavigation("myPage")
    }

    private fun initMyGroupButton() {
        myGroupButton.setOnClickListener {
            val intent = Intent(this, MyGroupReadActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initMyPostButton() {
        myPostButton.setOnClickListener {
            val intent = Intent(this, MyPostActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initMessageBoxButton() {
        messageBoxButton.setOnClickListener {
            val intent = Intent(this, MessageBoxActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initLogoutButton() { // 로그아웃 버튼
        logoutButton.setOnClickListener {
            initDialog()
        }
    }
    private fun initDialog() {
        val dlg = Dialog(this)
        dlg.setContentView(R.layout.logout)
        dlg.setCancelable(false)
        dlg.window?.setBackgroundDrawable(Drawable.createFromPath("@drawable/alert_dialog_background"))

        var title: TextView = dlg.findViewById(R.id.logout)
        var message : TextView = dlg.findViewById(R.id.message)
        // 취소 버튼 클릭
        var btnCancel : AppCompatButton = dlg.findViewById(R.id.cancel_button)
        btnCancel.setOnClickListener {
            dlg.dismiss()
        }
        // 로그아웃 버튼 클릭
        var btnLogout : AppCompatButton = dlg.findViewById(R.id.logout_button)
        btnLogout.setOnClickListener {
            dlg.dismiss()
            auth.signOut()

            val editor = pref.edit()
            editor.remove("user id").apply()

            //Log.d("preference clear test", pref.getLong("user id", -1).toString())

            val intent = Intent(this, LoginActivity::class.java)
            Toast.makeText(
                this@MyPageActivity,
                "로그아웃 되었습니다.",
                Toast.LENGTH_SHORT
            ).show()
            finish()
            startActivity(intent)
        }
        dlg.show()
    }

    companion object{
        const val USER_INFO = "user info"
    }
}