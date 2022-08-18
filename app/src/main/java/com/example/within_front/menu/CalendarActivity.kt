package com.example.within_front.menu

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import com.example.within_front.R

class CalendarActivity : AppCompatActivity() {

    private val calendar : CalendarView by lazy{
        findViewById(R.id.calendar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        initCalendar()
    }

    private fun initCalendar(){
        calendar.date = System.currentTimeMillis()
        calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("date", "$year-$month-$dayOfMonth")
            startActivity(intent)
        }
    }
}