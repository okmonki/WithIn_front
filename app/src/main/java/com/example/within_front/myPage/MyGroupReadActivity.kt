package com.example.within_front.myPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.within_front.R
import com.example.within_front.base.BaseActivity
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.time.format.TextStyle
import java.util.*

class MyGroupReadActivity : BaseActivity() {

    private val client = OkHttpClient()
    private val pref by lazy{
        getSharedPreferences(USER_INFO, MODE_PRIVATE)
    }
    private var hobbyList = mutableListOf<Hobby>()

    private val backButton : ImageButton by lazy{
        findViewById(R.id.back_button)
    }
    private val createButton : ImageButton by lazy{
        findViewById(R.id.modify_button)
    }
    private val unitText : TextView by lazy{
        findViewById(R.id.my_unit)
    }
    private val positionText : TextView by lazy{
        findViewById(R.id.my_position)
    }
    private val mbtiText : TextView by lazy{
        findViewById(R.id.my_MBTI)
    }
    private val hobbyText : TextView by lazy{
        findViewById(R.id.my_hobby)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_group_read)
        val userId = pref.getLong("user id", -1)

        initBackButton()
        initCreateButton()
        getMyGroup(userId)

        initNavigation("myPage")
    }

    private fun initBackButton(){
        backButton.setOnClickListener{
            finish()
        }
    }
    private fun initCreateButton() {
        createButton.setOnClickListener {
            val intent = Intent(this, MyGroupUpdateActivity::class.java)

            intent.putExtra("unit", unitText.text.toString())
            intent.putExtra("position", positionText.text.toString())
            intent.putExtra("mbti", mbtiText.text.toString())
            intent.putExtra("hobby", hobbyText.text.toString())

            finish()
            startActivity(intent)
        }
    }

    private fun getMyGroup(userId : Long) {
        val getGroupRequest = Request.Builder().addHeader("Content-Type", "application/json").url("http://52.78.137.155:8080/user/$userId/myGroup").build()
        client.newCall(getGroupRequest).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("fail", "내 그룹 조회 실패")
                runOnUiThread{
                    Toast.makeText(
                        this@MyGroupReadActivity,
                        "내 그룹 조회에 실패했습니다. 다시 시도해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            override fun onResponse(call: Call, response: Response) {
                if(response.code() == 200) {
                    hobbyList = mutableListOf()

                    val jsonArray = JSONArray(response.body()!!.string())

                    for(idx in 0 until jsonArray.length()){
                        val tempGroup = jsonArray[idx] as JSONObject
                        val category = tempGroup.getString("category")
                        val describe = tempGroup.getString("describe")
                        hobbyList.add(Hobby(category = category, describe = describe))
                    }
                    runOnUiThread{
                        for (item in hobbyList) {
                            val category = item.category
                            when(item.describe){
                                "hobby" -> {
                                    hobbyText.append(category)
                                    hobbyText.append("\n")
                                }
                                "army" -> unitText.text = category
                                "position" -> positionText.text = category
                                "mbti" -> mbtiText.text = category
                            }
                        }
                    }
                }
            }
        })
    }
    companion object{
        const val USER_INFO = "user info"
    }
}