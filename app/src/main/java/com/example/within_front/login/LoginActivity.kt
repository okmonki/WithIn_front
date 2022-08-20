package com.example.within_front.login

import android.content.Intent
import android.graphics.Rect
import android.media.Image
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.within_front.R
import com.example.within_front.myPage.MyPageActivity
import com.google.firebase.auth.FirebaseAuth
import okhttp3.*
import java.io.IOException
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private val client = OkHttpClient()
    private val pref by lazy{
        getSharedPreferences(USER_INFO, MODE_PRIVATE)
    }

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

    private val warningText : TextView by lazy{
        findViewById(R.id.warning)
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

        if(pref.getLong("user id", -1) != -1L){
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }

        auth=FirebaseAuth.getInstance()

        initEmailAndPasswordEnable()
        setPasswordShowingState()
        checkEmailTextWatcher()
        initLoginButton()
        initSignUpButton()

    }

    private fun initSignUpButton(){
        signUpButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
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
    private fun initLoginButton() {
        //login 버튼이 눌린다면
        loginButton.setOnClickListener {
            val email=getInputEmail()
            val password=getInputPassword()

            if(!checkEmail()){ //틀린 경우
                //Log.d("test", "fail")
                //Toast.makeText(this,"이메일 형식에 맞게 입력하세요!",Toast.LENGTH_SHORT).show()
                warningText.visibility = View.VISIBLE
            }

            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show()
                        saveUserId(getUid())
                        finish()
                        val intent = Intent(this, MyPageActivity::class.java)
                        startActivity(intent)
                    } else {
                        //Toast.makeText(this,"로그인에 실패하였습니다. 이메일 또는 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show()
                        warningText.visibility = View.VISIBLE
                    }
                }
        }
    }

    private fun getUid() : String{
        return auth.currentUser?.uid ?: ""
    }

    private fun saveUserId(uid : String){
        val getUserIdRequest = Request.Builder().url("http:52.78.137.155:8080/user/user/$uid").build()

        client.newCall(getUserIdRequest).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("fail", "유저 아이디 조회 실패")
                runOnUiThread{
                    Toast.makeText(
                        this@LoginActivity,
                        "유저 아이디 조회에 실패했습니다. 다시 시도해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.code() == 200){
                    val userId = response.body()!!.string().toLong()
                    val editor = pref.edit()
                    editor.putLong("user id", userId)
                    editor.apply()
                    Log.d("user id save", userId.toString())
                } else{
                    Log.d("fail", "유저 아이디 조회 실패, ${response.code()}")
                    runOnUiThread{
                        Toast.makeText(
                            this@LoginActivity,
                            "유저 아이디 조회에 실패했습니다. 다시 시도해주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
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
            eyeCloseImageButton.visibility = View.GONE
            eyeOpenImageButton.visibility = View.VISIBLE
            passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }
        eyeOpenImageButton.setOnClickListener{
            eyeOpenImageButton.visibility = View.GONE
            eyeCloseImageButton.visibility = View.VISIBLE
            passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()

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

    companion object{
        const val USER_INFO = "user info"
    }
}


