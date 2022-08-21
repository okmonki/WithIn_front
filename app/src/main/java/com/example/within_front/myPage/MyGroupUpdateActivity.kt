package com.example.within_front.myPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import com.example.within_front.R
import com.example.within_front.base.BaseActivity
import okhttp3.*
import java.io.IOException
import kotlin.collections.ArrayList

class MyGroupUpdateActivity : BaseActivity() {
    private val client = OkHttpClient()
    private val pref by lazy{
        getSharedPreferences(MyGroupReadActivity.USER_INFO, MODE_PRIVATE)
    }
    private var unit : String = ""
    private var position : String = ""
    private var mbti : String = ""
    private var hobby : String = ""
    private var hobbyList = listOf<String>()

    private var unitItems = arrayOf("1군단","1사단","25사단","28사단","5사단","6사단","3사단","15사단","7사단","21사단","12사단","22사단","23사단","27사단","9사단","수도기계화사단","8사단","11사단","2신속대응사단","60사단","66사단","72사단","73사단","75사단","52사단","56사단","51사단","55사단","31사단","32사단","35사단","36사단","37사단","39사단","50사단","53사단")
    private var positionItems = arrayOf("보병", "기갑", "포병", "방공", "정보", "공병", "정보", "공병", "정보통신", "항공", "화학", "병기", "병참", "수송", "인사행정", "헌병", "재정", "정훈", "의무", "법무", "군종")
    private var mbtiItems = arrayOf("INFJ","INFP","ENFJ","ENFP","ISTJ","ISFJ","ESTJ","ESFJ","ISTP","ISFP","ESTP","ESFP", "INTJ", "INTP", "ENTJ", "ENTP")

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
    private val hobby10CheckBox : CheckBox by lazy{
        findViewById(R.id.my_hobby10)
    }
    private val hobby11CheckBox : CheckBox by lazy{
        findViewById(R.id.my_hobby11)
    }
    private val hobby12CheckBox : CheckBox by lazy{
        findViewById(R.id.my_hobby12)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_group_update)
        val userId = pref.getLong("user id", -1)

        initBackButton()
        initCompleteButton(userId)

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

        if (intent.hasExtra("unit")) {
            unit = intent.getStringExtra("unit").toString()
            Log.d("데이터 가져오기", unit)
        }

        if (intent.hasExtra("position")) {
            position = intent.getStringExtra("position").toString()
            Log.d("데이터 가져오기", position)
        }

        if (intent.hasExtra("mbti")) {
            mbti = intent.getStringExtra("mbti").toString()
            Log.d("데이터 가져오기", mbti)
        }

        if (intent.hasExtra("hobby")) {
            hobby = intent.getStringExtra("hobby").toString()
            Log.d("데이터 가져오기", hobby)
        }

        unitSpinner.setSelection(unitItems.indexOf(unit))
        positionSpinner.setSelection(positionItems.indexOf(position))
        mbtiSpinner.setSelection(mbtiItems.indexOf(mbti))

        hobbyList = hobby.split("\n")
        if (hobby1CheckBox.text in hobbyList) {
            hobby1CheckBox.isChecked = true
        }
        if (hobby2CheckBox.text in hobbyList) {
            hobby2CheckBox.isChecked = true
        }
        if (hobby3CheckBox.text in hobbyList) {
            hobby3CheckBox.isChecked = true
        }
        if (hobby4CheckBox.text in hobbyList) {
            hobby4CheckBox.isChecked = true
        }
        if (hobby5CheckBox.text in hobbyList) {
            hobby5CheckBox.isChecked = true
        }
        if (hobby6CheckBox.text in hobbyList) {
            hobby6CheckBox.isChecked = true
        }
        if (hobby7CheckBox.text in hobbyList) {
            hobby7CheckBox.isChecked = true
        }
        if (hobby8CheckBox.text in hobbyList) {
            hobby8CheckBox.isChecked = true
        }
        if (hobby9CheckBox.text in hobbyList) {
            hobby9CheckBox.isChecked = true
        }
        if (hobby10CheckBox.text in hobbyList) {
            hobby10CheckBox.isChecked = true
        }
        if (hobby11CheckBox.text in hobbyList) {
            hobby11CheckBox.isChecked = true
        }
        if (hobby12CheckBox.text in hobbyList) {
            hobby12CheckBox.isChecked = true
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
            if(hobby10CheckBox.isChecked()) {
                hobbyList.add("\"${hobby10CheckBox.text}\"")
            }
            if(hobby11CheckBox.isChecked()) {
                hobbyList.add("\"${hobby11CheckBox.text}\"")
            }
            if(hobby12CheckBox.isChecked()) {
                hobbyList.add("\"${hobby12CheckBox.text}\"")
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