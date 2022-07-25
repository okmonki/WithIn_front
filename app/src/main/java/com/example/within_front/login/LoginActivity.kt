package com.example.within_front.login

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.within_front.R
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    val logoImageView : ImageView by lazy{
        findViewById(R.id.logoImageView)
    }
    val emailEditText : EditText by lazy{
        findViewById(R.id.loginEmailEditText)
    }
    val passwordEditText : EditText by lazy{
        findViewById(R.id.loginPasswordEditText)
    }
    val eyeImageButton : ImageButton by lazy{
        findViewById(R.id.check_password_1)
    }

    val loginButton : AppCompatButton by lazy{
        findViewById(R.id.loginButton)
    }

    val signUpButton : AppCompatButton by lazy{
        findViewById(R.id.SignUpButton)
    }

    private var isPasswordShowing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        fun isEmail(email: String?): Boolean {
            var returnValue = false
            val regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$"
            val p: Pattern = Pattern.compile(regex)
            val m: Matcher = p.matcher(email)
            if (m.matches()) {
                returnValue = true
            }
            return returnValue
        }
        //  Log.d("LoginActivity", "returnvalue$")

        fun checkEmail(email: String): Boolean = email.contains("@")
        // given
        val email = "test@test.com"
        // when
        val actual = checkEmail(email)
        // then
        assertTrue(actual)
    }

    private fun getInputEmail() : String {
        return emailEditText.text.toString()
    }

    private fun getInputPassword() : String {
        return passwordEditText.text.toString()
    }


    private fun initSignupButton() {
        //아직 SignUp페이지랑 merge 안해서 빨간 글씨
        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUp.SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initLoginButton() {
        //login 버튼이 눌린다면
        loginButton.setOnClickListener {
            val email=getInputEmail()
            val password=getInputPassword()
        }
    }

    private fun setPasswordShowingState() {
        eyeImageButton.setOnClickListener {
            // 비밀번호가 보이지 않는 상태. eye_open
            if (!isPasswordShowing) {
                isPasswordShowing = true
                eyeImageButton.background = getDrawable(R.drawable.check_password)
                passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                // 비밀번호가 보이는 상태. eye_close
                isPasswordShowing = false
                eyeImageButton.background = getDrawable(R.drawable.eye_open)
                passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
    }

    private fun initAlertDialog() { // 로그인 실패 시 alertDialog 띄워주는 함수
        val dialogView = View.inflate(this, R.layout.alertdialog_view, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val dialogOkButton: AppCompatButton = dialogView.findViewById(R.id.okButton)
        dialogOkButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


}