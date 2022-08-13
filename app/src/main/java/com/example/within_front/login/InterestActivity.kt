package com.example.within_front.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.within_front.R
import com.google.firebase.auth.FirebaseAuth
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class InterestActivity : AppCompatActivity() {

    private val client = OkHttpClient()

    private lateinit var auth : FirebaseAuth

    private val interestGroupSpinner: Spinner by lazy{
        findViewById(R.id.interestSpinner0)
    }
    private val interestJobSpinner: Spinner by lazy{
        findViewById(R.id.interestSpinner1)}

    private val interestMBTISpinner: Spinner by lazy{
        findViewById(R.id.interestSpinner2)}

    private val submitButton : AppCompatButton by lazy {
        findViewById(R.id.interestCompleteButton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interest) //view 연결
        initSpinner()

        val intent = intent
        val email = intent.getStringExtra("email")
        val nickname = intent.getStringExtra("nickname")
        val password = intent.getStringExtra("password")

        Log.d("email", email.toString())
        Log.d("nickname", nickname.toString())
        Log.d("password", password.toString())

        initSubmitButton(email!!, nickname!!, password!!)
    }

    private fun initSpinner(){
        val interestGroupList= listOf("1사단","25사단","28사단","5사단","6사단","3사단","15사단","7사단","21사단","12사단","22사단","23사단","27사단","9사단","수도기계화사단","8사단","11사단","2신속대응사단","60사단","66사단","72사단","73사단","75사단","52사단","56사단","51사단","55사단","31사단","32사단","35사단","36사단","37사단","39사단","50사단","53사단")
        val interestGroupAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, interestGroupList)
        interestGroupSpinner.adapter=interestGroupAdapter
        interestGroupSpinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val group=interestGroupList.get(position)
                Log.d("LoginActivity", "group: $group")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val interestJobList= listOf("기술행정병","어학병","전문특기병","임기제부사관","연고지복무병","최전방수호병")
        val interestJobAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, interestJobList)
        interestJobSpinner.adapter=interestJobAdapter
        interestJobSpinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val job=interestJobList.get(position)
                Log.d("LoginActivity", "job: $job")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val interestMBTIList= listOf("INFJ","INFP","ENFJ","ENFP","ISTJ","ISFJ","ESTJ","ESFJ","ISTP","ISFP","ESTP","ESFP")
        val interestMBTIAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, interestMBTIList)
        interestMBTISpinner.adapter=interestMBTIAdapter
        interestMBTISpinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val MBTI =interestMBTIList.get(position)
                Log.d("LoginActivity", "MBTI: $MBTI")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun initSubmitButton(email : String, nickname : String, password : String){
        submitButton.setOnClickListener {
            Log.d("submit button", "clicked")
            auth=FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    val uid = task.result.user!!.uid
                    Log.d("uid", uid)

                    val postData = "{\"army\": \"${interestGroupSpinner.selectedItem}\", \"email\": \"$email\", \"mbti\": \"${interestMBTISpinner.selectedItem}\", \"nickname\": \"$nickname\", \"position\": \"${interestJobSpinner.selectedItem}\", \"uid\": \"$uid\"}"
                    Log.d("postData", postData)
                    val body = RequestBody.create(MediaType.parse("application/json; charset=uft-8"), postData)
                    val createUserRequest = Request.Builder().addHeader("Content-Type", "application/json").url("http:52.78.137.155:8080/user/create").post(body).build()

                    client.newCall(createUserRequest).enqueue(object: Callback{
                        override fun onFailure(call: Call, e: IOException) {
                            Log.d("fail", "회원가입 실패")
                            runOnUiThread{
                                Toast.makeText(
                                    this@InterestActivity,
                                    "회원가입에 실패하였습니다. 다시 시도해주세요.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onResponse(call: Call, response: Response) {
                            if(response.code() == 200){
                                runOnUiThread {
                                    Toast.makeText(
                                        this@InterestActivity,
                                        "회원가입이 성공적으로 완료되었습니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent = Intent(this@InterestActivity, LoginActivity::class.java)
                                    finish()
                                    startActivity(intent)
                                }
                            } else{
                                runOnUiThread {
                                    Toast.makeText(
                                        this@InterestActivity,
                                        "회원가입에 실패하였습니다.\n" +
                                                "${response.code()}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    })
                } else{
                    Log.d("create user", "fail")
                }
            }
        }
    }
}