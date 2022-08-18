package com.example.within_front.menu

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.R
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class MenuActivity : AppCompatActivity() {

    private val client = OkHttpClient()

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
        val intent = intent
        val date = intent.getStringExtra("date")
        getMenu("breakfast", date!!, containerMap)
        getMenu("lunch", date, containerMap)
        getMenu("dinner", date, containerMap)
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