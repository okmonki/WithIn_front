package com.example.within_front.menu

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.R
import com.example.within_front.base.BaseActivity
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*

class MenuActivity : BaseActivity() {

    private val client = OkHttpClient()

    private var dateText : String = ""

    private val date : TextView by lazy{
        findViewById(R.id.date)
    }

    private val backButton : ImageButton by lazy{
        findViewById(R.id.back_button)
    }

    private val breakfastContainer : RecyclerView by lazy{
        findViewById(R.id.breakfast_container)
    }

    private val lunchContainer : RecyclerView by lazy{
        findViewById(R.id.lunch_container)
    }

    private val dinnerContainer : RecyclerView by lazy{
        findViewById(R.id.dinner_container)
    }

    private var breakfastList = mutableListOf<Menu>()
    private var lunchList = mutableListOf<Menu>()
    private var dinnerList = mutableListOf<Menu>()

    private val menuListMap = mutableMapOf("breakfast" to breakfastList, "lunch" to lunchList, "dinner" to dinnerList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val containerMap = mutableMapOf("breakfast" to breakfastContainer, "lunch" to lunchContainer, "dinner" to dinnerContainer)

        initDate()
        initBackButton()

        getMenu("breakfast", dateText, containerMap)
        getMenu("lunch", dateText, containerMap)
        getMenu("dinner", dateText, containerMap)

        initNavigation("menu")
    }

    private fun initBackButton(){
        backButton.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun initDate(){
        val intent = intent
        dateText = if(intent.getStringExtra("date") != null){
            intent.getStringExtra("date").toString()
        } else {
            Date(System.currentTimeMillis()).toString().modifyMonth().substring(0..9)
        }

        dateText = dateText.modifyDayOfWeek()
        Log.d("date", dateText)

        date.text = "${dateText.substring(4..5)}월 ${dateText.substring(7..8)}일 ${dateText.substring(0..2)}"
    }

    private fun initRecyclerView(MenuContainer : RecyclerView, menuList : MutableList<Menu>){
        MenuContainer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        MenuContainer.setHasFixedSize(true)

        MenuContainer.adapter = MenuAdapter(this, menuList)
    }

    private fun getMenu(bld : String, date : String, containerMap : MutableMap<String, RecyclerView>){
        val getMenuRequest = Request.Builder().addHeader("Content-Type", "application/json").url("http://localhost:8080/menu/$date/$bld").build()

        client.newCall(getMenuRequest).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("fail", "메뉴 조회 실패")
                runOnUiThread{
                    Toast.makeText(
                        this@MenuActivity,
                        "메뉴 조회에 실패했습니다. 다시 시도해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.code() == 200){
                    menuListMap[bld] = mutableListOf()

                    val jsonArray = JSONArray(response.body()!!.string())

                    for(idx in 0 until jsonArray.length()){
                        val tempMenu = jsonArray[idx] as JSONObject
                        val name = tempMenu.getString("menu")

                        val menu = Menu(name = name)
                        menuListMap[bld]!!.add(menu)
                    }
                    Log.d("MenuList", menuListMap[bld]!!.size.toString())
                    runOnUiThread {
                        initRecyclerView(containerMap[bld]!!, menuListMap[bld]!!)
                    }
                }
            }
        })
    }
}

fun String.modifyMonth() : String{
    val month = this.substring(4..6)
    val newMonth = when(month){
        "Jan" -> "01"
        "Feb" -> "02"
        "Mar" -> "03"
        "Apr" -> "04"
        "May" -> "05"
        "Jun" -> "06"
        "Jul" -> "07"
        "Aug" -> "08"
        "Sep" -> "09"
        "Oct" -> "10"
        "Nov" -> "11"
        else -> "12"
    }
    return this.replace(month, newMonth)
}

fun String.modifyDayOfWeek() : String{
    val dayOfWeek = this.substring(0..2)
    val newDayOfWeek = when(dayOfWeek){
        "Mon" -> "월요일"
        "Tue" -> "화요일"
        "Wed" -> "수요일"
        "Thu" -> "목요일"
        "Fri" -> "금요일"
        "Sat" -> "토요일"
        else -> "일요일"
    }
    return this.replace(dayOfWeek, newDayOfWeek)
}