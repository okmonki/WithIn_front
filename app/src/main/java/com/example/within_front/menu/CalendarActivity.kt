package com.example.within_front.menu

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import com.example.within_front.R
import com.example.within_front.base.BaseActivity
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class CalendarActivity : BaseActivity() {

    private val calendar : CalendarView by lazy{
        findViewById(R.id.calendar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        initCalendar()

        initNavigation("menu")
    }

    @SuppressLint("NewApi")
    private fun initCalendar(){
        calendar.date = System.currentTimeMillis()
        calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = LocalDate.of(year, month+1, dayOfMonth)
            val dayOfWeek = date.dayOfWeek

            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("date", "${dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US)} ${(month+1).toString().padStart(2,'0')} $dayOfMonth")
            startActivity(intent)
        }
    }
}