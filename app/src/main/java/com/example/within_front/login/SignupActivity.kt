package com.example.within_front.login

import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.within_front.R
import com.google.firebase.auth.FirebaseAuth


class SignupActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

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
        auth=FirebaseAuth.getInstance()

        initBackButton()
        focusEditText(editTextEmail)
        focusEditText(editTextNickname)
        focusEditText(editTextPassword)
        focusEditText(editTextPasswordReconfirm)
        setPasswordShowingState(editTextPassword, eyeClosePassword, eyeOpenPassword)
        setPasswordShowingState(editTextPasswordReconfirm, eyeClosePasswordReconfirm, eyeOpenPasswordReconfirm)
    }

    private fun isValidEmail(email: String): Boolean {
        isEmailValid = !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        changeVisibility(warningEmailInvalid, isEmailValid)
        return isEmailValid
    }
    private fun isValidNickname(nickname: String): Boolean {
        isNicknameValid = nickname.length in 4..10
        changeVisibility(warningNicknameInvalid, isNicknameValid)
        return isNicknameValid
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
                var check = false
                when (get_inform.id) {
                    R.id.get_email -> check = isValidEmail(value)
                    R.id.get_nickname -> check = isValidNickname(value)
                    R.id.get_password -> check = isValidPassword(value)
                    R.id.get_password_check -> check = isSamePassword(value)
                }

                if (check) {
                    view.background = getDrawable(R.drawable.edittext_basic)
                } else {
                    view.background = getDrawable(R.drawable.edittext_invaild)
                }

                submitActivation()
            }
        }
    }

    private fun submitActivation() {
        if (isEmailValid && isNicknameValid && isPasswordValid && isPasswordReconfirm) {
            btnSubmit.background = getDrawable(R.drawable.button_valid)
        } else {
            btnSubmit.background = getDrawable(R.drawable.button_basic)
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

}