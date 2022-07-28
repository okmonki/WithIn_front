package com.example.within_front.menu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.R

class MenuActivity : AppCompatActivity() {

    private val breakfastContainer : RecyclerView by lazy{
        findViewById(R.id.breakfast_container)
    }

    private val lunchContainer : RecyclerView by lazy{
        findViewById(R.id.lunch_container)
    }

    private val dinnerContainer : RecyclerView by lazy{
        findViewById(R.id.dinner_container)
    }

    private val breakfastList = mutableListOf<Menu>()
    private val lunchList = mutableListOf<Menu>()
    private val dinnerList = mutableListOf<Menu>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        initRecyclerView(breakfastContainer, breakfastList)
        initRecyclerView(lunchContainer, lunchList)
        initRecyclerView(dinnerContainer, dinnerList)
    }

    private fun initRecyclerView(MenuContainer : RecyclerView, menuList : MutableList<Menu>){
        MenuContainer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        MenuContainer.setHasFixedSize(true)

        MenuContainer.adapter = MenuAdapter(this, menuList)
    }
}