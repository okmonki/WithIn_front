package com.example.within_front.myPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.within_front.R
import com.example.within_front.base.BaseActivity
import okhttp3.*
import java.io.IOException

class MyGroupUpdateActivity : BaseActivity() {
    private val client = OkHttpClient()

    private var unitItems = arrayOf("미사일 사령부", "소속 부대 2", "소속 부대 3")
    private var positionItems = arrayOf("운전병", "행정병", "통신병", "의무병")
    private var mbtiItems = arrayOf("ENFJ", "ENTJ", "ENFP", "ENTP", "ESFP", "ESFJ", "ESTP", "ESTJ", "INFP", "INFJ", "INTP", "ISTP", "ISFP", "ISFJ", "ISTJ", "INTJ")

    private val backButton : ImageButton by lazy{
        findViewById(R.id.back_button)
    }
    private val completeButton : AppCompatButton by lazy{
        findViewById(R.id.complete_button)
    }
    private val unitSpinner: Spinner by lazy {
        findViewById(R.id.my_unit)
    }
    private val positionSpinner: Spinner by lazy {
        findViewById(R.id.my_position)
    }
    private val mbtiSpinner: Spinner by lazy {
        findViewById(R.id.my_MBTI)
    }
    private val hobby1CheckBox: CheckBox by lazy {
        findViewById(R.id.my_hobby1)
    }
    private val hobby2CheckBox: CheckBox by lazy {
        findViewById(R.id.my_hobby2)
    }
    private val hobby3CheckBox: CheckBox by lazy {
        findViewById(R.id.my_hobby3)
    }
    private val hobby4CheckBox: CheckBox by lazy {
        findViewById(R.id.my_hobby4)
    }
    private val hobby5CheckBox: CheckBox by lazy {
        findViewById(R.id.my_hobby5)
    }
    private val hobby6CheckBox: CheckBox by lazy {
        findViewById(R.id.my_hobby6)
    }
    private val hobby7CheckBox: CheckBox by lazy {
        findViewById(R.id.my_hobby7)
    }
    private val hobby8CheckBox: CheckBox by lazy {
        findViewById(R.id.my_hobby8)
    }
    private val hobby9CheckBox: CheckBox by lazy {
        findViewById(R.id.my_hobby9)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_group_update)

        initBackButton()
        initCompleteButton(1)

        val unitAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unitItems)
        unitSpinner.adapter = unitAdapter
        unitSpinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
        }
        val positionAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, positionItems)
        positionSpinner.adapter = positionAdapter
        positionSpinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
        }
        val mbtiAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, mbtiItems)
        mbtiSpinner.adapter = mbtiAdapter
        mbtiSpinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
        }

        initNavigation("myPage")
    }
    private fun initBackButton(){
        backButton.setOnClickListener{
            val intent = Intent(this@MyGroupUpdateActivity, MyGroupReadActivity::class.java)
            finish()
            startActivity(intent)
        }
    }
    private fun initCompleteButton(userId : Long){
        //completeButton.isEnabled = false


        completeButton.setOnClickListener{

            var hobbyList =  ArrayList<String>()

            if(hobby1CheckBox.isChecked()) {
                hobbyList.add("\"${hobby1CheckBox.text}\"")
            }
            if(hobby2CheckBox.isChecked()) {
                hobbyList.add("\"${hobby2CheckBox.text}\"")
            }
            if(hobby3CheckBox.isChecked()) {
                hobbyList.add("\"${hobby3CheckBox.text}\"")
            }
            if(hobby4CheckBox.isChecked()) {
                hobbyList.add("\"${hobby4CheckBox.text}\"")
            }
            if(hobby5CheckBox.isChecked()) {
                hobbyList.add("\"${hobby5CheckBox.text}\"")
            }
            if(hobby6CheckBox.isChecked()) {
                hobbyList.add("\"${hobby6CheckBox.text}\"")
            }
            if(hobby7CheckBox.isChecked()) {
                hobbyList.add("\"${hobby7CheckBox.text}\"")
            }
            if(hobby8CheckBox.isChecked()) {
                hobbyList.add("\"${hobby8CheckBox.text}\"")
            }
            if(hobby9CheckBox.isChecked()) {
                hobbyList.add("\"${hobby9CheckBox.text}\"")
            }

            val postData = "{\"army\": \"${unitSpinner.selectedItem}\", \"position\": \"${positionSpinner.selectedItem}\", \"mbti\": \"${mbtiSpinner.selectedItem}\", \"categories\": $hobbyList}"
            Log.d("category", "$hobbyList")
            Log.d("unitSpinner", unitSpinner.selectedItem.toString())
            Log.d("positionSpinner", positionSpinner.selectedItem.toString())
            Log.d("mbtiSpinner", mbtiSpinner.selectedItem.toString())

            val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postData)

            val postGroupRequest = Request.Builder().url("http://52.78.137.155:8080/user/$userId/myGroup").put(body).build()

            client.newCall(postGroupRequest).enqueue(object: Callback{
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("connection error", "인터넷 연결 불안정")
                    runOnUiThread{
                        Toast.makeText(
                            this@MyGroupUpdateActivity,
                            "인터넷 연결이 불안정합니다. 다시 시도해주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                override fun onResponse(call: Call, response: Response) {
                    if(response.code() == 200) {
                        Log.d("update group", "success")
                        val intent = Intent(this@MyGroupUpdateActivity, MyGroupReadActivity::class.java)
                        finish()
                        startActivity(intent)
                    }
                    else {
                        Log.d("connection error", "userId : $userId / response code : ${response.code()} / 인터넷 연결 불안정")
                        runOnUiThread{
                            Toast.makeText(
                                this@MyGroupUpdateActivity,
                                "인터넷 연결이 불안정합니다. 다시 시도해주세요",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
        }
    }
}