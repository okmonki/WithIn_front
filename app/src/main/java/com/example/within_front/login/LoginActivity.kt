package com.example.within_front.login

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.within_front.R
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {


    val logoImageView: ImageView by lazy {
        findViewById(R.id.logoImageView)
    }
    val emailEditText: EditText by lazy {
        findViewById(R.id.loginEmailEditText)
    }
    val passwordEditText: EditText by lazy {
        findViewById(R.id.loginPasswordEditText)
    }
    val eyeCloseImageButton: ImageButton by lazy {
        findViewById(R.id.check_password_1)
    }

    val eyeOpenImageButton: ImageButton by lazy {
        findViewById(R.id.eyeOpenImageButton)
    }

    val loginButton: AppCompatButton by lazy {
        findViewById(R.id.loginButton)
    }

    val signUpButton: AppCompatButton by lazy {
        findViewById(R.id.SignUpButton)
    }

    private var isPasswordShowing = false
    val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initEmailAndPasswordEnable()
        setPasswordShowingState()
        checkEmailTextWatcher()
        initLoginButton()
        //initSignupButton()

    }

    private fun getInputEmail() : String {
        return emailEditText.text.toString()
    }

    private fun getInputPassword() : String {
        return passwordEditText.text.toString()
    }

    private fun checkEmail():Boolean{
        val email = getInputEmail().trim() //공백제거
        val p = Pattern.matches(emailValidation, email) // 서로 패턴이 맞는지 확인
        return if (p) {
            //이메일 형태가 정상일 경우
            emailEditText.setTextColor(R.color.black.toInt())
            true
        } else {
            emailEditText.setTextColor(R.color.red.toInt())
            false
        }
    }

    private fun checkEmailTextWatcher(){
        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // text가 변경된 후 호출
                // s에는 변경 후의 문자열이 담겨 있다.
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // text가 변경되기 전 호출
                // s에는 변경 전 문자열이 담겨 있다.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // text가 바뀔 때마다 호출된다.
                // 우린 이 함수를 사용한다.
                checkEmail()
            }
        })
    }

//    private fun initSignupButton() {
//        아직 SignUp 페이지랑 merge 안해서 빨간색 글씨
//        signUpButton.setOnClickListener {
//            val intent = Intent(this, SignUp.SignupActivity::class.java)
//            startActivity(intent)
//        }
//    }

    private fun initLoginButton() {
        //login 버튼이 눌린다면
        loginButton.setOnClickListener {
            val email=getInputEmail()
            val password=getInputPassword()
            if(!checkEmail()){ //틀린 경우
                Log.d("test", "fail")
                Toast.makeText(this,"이메일 형식에 맞게 입력하세요!",Toast.LENGTH_SHORT).show()
            }
            else{ //맞는 경우
                Toast.makeText(this,"로그인 됐습니다.",Toast.LENGTH_SHORT).show()
                initLoginButton()
            }
        }
    }
    private fun initEmailAndPasswordEnable() {
        emailEditText.addTextChangedListener {
            val enable=emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
            loginButton.isEnabled=enable
            signUpButton.isEnabled=enable
        }
        passwordEditText.addTextChangedListener {
            val enable=emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
            loginButton.isEnabled=enable
            signUpButton.isEnabled=enable
        }
    }

//    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//        // TODO("Not yet implemented")
//    }


    private fun setPasswordShowingState() {
        eyeCloseImageButton.setOnClickListener {
            // 비밀번호가 보이지 않는 상태. eye_open
            if (!isPasswordShowing) {
                isPasswordShowing = true
                eyeCloseImageButton.visibility = View.VISIBLE
                eyeOpenImageButton.visibility = View.GONE
                passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else{
                isPasswordShowing = false
                eyeCloseImageButton.visibility = View.GONE
                eyeOpenImageButton.visibility = View.VISIBLE
                passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
        eyeOpenImageButton.setOnClickListener(){
            if (!isPasswordShowing) {
                isPasswordShowing = true
                eyeOpenImageButton.visibility = View.GONE
                eyeCloseImageButton.visibility = View.VISIBLE
                passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                isPasswordShowing = false
                eyeOpenImageButton.visibility = View.VISIBLE
                eyeCloseImageButton.visibility = View.GONE
                passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
    }

//    private fun initAlertDialog() { // 로그인 실패 시 alertDialog 띄워주는 함수
//        val dialogView = View.inflate(this, R.layout.alertdialog_view, null)
//        val builder = AlertDialog.Builder(this)
//        builder.setView(dialogView)
//        val dialog = builder.create()
//        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//        val dialogOkButton: AppCompatButton = dialogView.findViewById(R.id.okButton)
//        dialogOkButton.setOnClickListener {
//            dialog.dismiss()
//        }
//        dialog.show()
//    }




}

