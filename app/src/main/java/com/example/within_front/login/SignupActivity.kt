package com.example.within_front.login

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.within_front.R
import com.google.firebase.auth.FirebaseAuth
import okhttp3.*
import java.io.IOException


class SignupActivity : AppCompatActivity() {

    private val client = OkHttpClient()

    private val btnBack : ImageButton by lazy {
        findViewById(R.id.arrowImg)
    }
    private val btnSubmit : androidx.appcompat.widget.AppCompatButton by lazy {
        findViewById(R.id.btn_submit)
    }

    // check Password (eye image)
    private val eyeClosePassword: ImageButton by lazy {
        findViewById(R.id.check_password_1)
    }
    private val eyeClosePasswordReconfirm: ImageButton by lazy {
        findViewById(R.id.check_password_2)
    }
    private val eyeOpenPassword: ImageButton by lazy {
        findViewById(R.id.eye_open_1)
    }
    private val eyeOpenPasswordReconfirm: ImageButton by lazy {
        findViewById(R.id.eye_open_2)
    }
    // editText
    private val editTextEmail : EditText by lazy {
        findViewById(R.id.get_email)
    }
    private val editTextNickname : EditText by lazy {
        findViewById(R.id.get_nickname)
    }
    private val editTextPassword : EditText by lazy {
        findViewById(R.id.get_password)
    }
    private val editTextPasswordReconfirm : EditText by lazy {
        findViewById(R.id.get_password_check)
    }

    // warning
    private val warningEmailRepeat : TextView by lazy {
        findViewById(R.id.warning_email_repeat)
    }
    private val warningEmailInvalid : TextView by lazy {
        findViewById(R.id.warning_email_invalid)
    }
    private val warningNicknameRepeat : TextView by lazy {
        findViewById(R.id.warning_nickname_repeat)
    }
    private val warningNicknameInvalid : TextView by lazy {
        findViewById(R.id.warning_nickname_invalid)
    }
    private val warningPassword : TextView by lazy {
        findViewById(R.id.warning_password)
    }
    private val warningPasswordReconfirm : TextView by lazy {
        findViewById(R.id.warning_password_reconfirm)
    }

    // validation
    private var isEmailValid = false
    private var isNicknameValid = false
    private var isPasswordValid = false
    private var isPasswordReconfirm = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        initBackButton()
        focusEditText(editTextEmail)
        focusEditText(editTextNickname)
        focusEditText(editTextPassword)
        focusEditText(editTextPasswordReconfirm)
        setPasswordShowingState(editTextPassword, eyeClosePassword, eyeOpenPassword)
        setPasswordShowingState(editTextPasswordReconfirm, eyeClosePasswordReconfirm, eyeOpenPasswordReconfirm)
        initSubmitButton()
    }

    private fun isValidEmail(email: String){
        isEmailValid = !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        changeVisibility(warningEmailInvalid, isEmailValid)
        if(!isEmailValid){
            editTextEmail.background = getDrawable(R.drawable.edittext_invaild)
            return
        }

        val emailRepeatCheck = Request.Builder().addHeader("Content-Type", "application/json").url("http:52.78.137.155:8080/user/user/emailList?email=$email").build()

        client.newCall(emailRepeatCheck).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("fail", "이메일 중복 확인 실패")
                runOnUiThread{
                    Toast.makeText(
                        this@SignupActivity,
                        "이메일 중복 확인에 실패했습니다. 다시 시도해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.code() == 200){
                    isEmailValid = response.body()!!.string().toBoolean().not()
                    Log.d("isEmailRepeat Result", isEmailValid.toString())
                    runOnUiThread {
                        changeVisibility(warningEmailRepeat, isEmailValid)
                        if(isEmailValid){
                            editTextEmail.background = getDrawable(R.drawable.edittext_basic)
                        } else{
                            editTextEmail.background = getDrawable(R.drawable.edittext_invaild)
                        }
                    }
                }
            }
        })
        submitActivation()
    }

    private fun isValidNickname(nickname: String) {
        isNicknameValid = nickname.length in 4..10
        changeVisibility(warningNicknameInvalid, isNicknameValid)
        if(!isNicknameValid){
            editTextNickname.background = getDrawable(R.drawable.edittext_invaild)
            return
        }

        val nicknameRepeatCheck = Request.Builder().addHeader("Content-Type", "application/json").url("http:52.78.137.155:8080/user/user/nicknameList?nickname=$nickname").build()

        client.newCall(nicknameRepeatCheck).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("fail", "닉네임 중복 확인 실패")
                runOnUiThread{
                    Toast.makeText(
                        this@SignupActivity,
                        "닉네임 중복 확인에 실패했습니다. 다시 시도해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.code() == 200){
                    isNicknameValid = response.body()!!.string().toBoolean().not()
                    Log.d("isNicknameRepeat Result", isNicknameValid.toString())
                    runOnUiThread {
                        changeVisibility(warningNicknameRepeat, isNicknameValid)
                        if(isNicknameValid){
                            editTextNickname.background = getDrawable(R.drawable.edittext_basic)
                        } else{
                            editTextNickname.background = getDrawable(R.drawable.edittext_invaild)
                        }
                    }
                }
            }
        })
        submitActivation()
    }
    private fun isValidPassword(password: String): Boolean {
        isPasswordValid = password.matches("^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#\$%^&*.])(?=.*[0-9!@#\$%^&*.]).{6,15}\$".toRegex())
        changeVisibility(warningPassword, isPasswordValid)
        val passwordReconfirm = editTextPasswordReconfirm.text.toString()
        if (password != passwordReconfirm && passwordReconfirm.isNotEmpty()) {
            editTextPasswordReconfirm.background = getDrawable(R.drawable.edittext_invaild)
            changeVisibility(warningPasswordReconfirm, false)
            isPasswordReconfirm = false
        } else if (password == passwordReconfirm) {
            editTextPasswordReconfirm.background = getDrawable(R.drawable.edittext_basic)
            changeVisibility(warningPasswordReconfirm, true)
            isPasswordReconfirm = true
        }
        return isPasswordValid
    }
    private fun isSamePassword(reconfirmPassword: String): Boolean {


        val password = editTextPassword.text.toString()
        isPasswordReconfirm = (password == reconfirmPassword)
        changeVisibility(warningPasswordReconfirm, isPasswordReconfirm)
        return isPasswordReconfirm
    }

    private fun changeVisibility(view: TextView, boolean: Boolean) {
        if (boolean) {
            view.visibility = View.INVISIBLE
        } else {
            view.visibility = View.VISIBLE
        }
    }

    private fun initBackButton(){
        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun focusEditText(get_inform: EditText) {
        get_inform.setOnFocusChangeListener { view, hasFocus ->
            val value = get_inform.text.toString()
            if (hasFocus) {
                view.background = getDrawable(R.drawable.edittext_onfocus)
            } else {
                when (get_inform.id) {
                    R.id.get_email -> isValidEmail(value)
                    R.id.get_nickname -> isValidNickname(value)
                    R.id.get_password -> {
                        if(isValidPassword(value)) view.background = getDrawable(R.drawable.edittext_basic)
                        else view.background = getDrawable(R.drawable.edittext_invaild)
                        submitActivation()
                    }
                    R.id.get_password_check -> {
                        if(isSamePassword(value)) view.background = getDrawable(R.drawable.edittext_basic)
                        else view.background = getDrawable(R.drawable.edittext_invaild)
                        submitActivation()
                    }
                }
            }
        }
    }

    private fun submitActivation() {
        if (isEmailValid && isNicknameValid && isPasswordValid && isPasswordReconfirm) {
            btnSubmit.background = getDrawable(R.drawable.button_valid)
            btnSubmit.isEnabled = true
        } else {
            btnSubmit.background = getDrawable(R.drawable.button_basic)
            btnSubmit.isEnabled = false
        }
    }

    private fun setPasswordShowingState(editText: EditText, show: ImageButton, hide: ImageButton) {
        show.setOnClickListener {
            showPassword(hide, show)
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }
        hide.setOnClickListener {
            showPassword(show, hide)
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

    private fun showPassword(show: ImageButton, hide: ImageButton) {
        show.visibility = View.VISIBLE
        hide.visibility = View.GONE
    }

    private fun initSubmitButton(){
        btnSubmit.setOnClickListener {
            val intent = Intent(this, InterestActivity::class.java)
            intent.putExtra("email", editTextEmail.text.toString())
            intent.putExtra("nickname", editTextNickname.text.toString())
            intent.putExtra("password", editTextPassword.text.toString())
            startActivity(intent)
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

}