package com.example.within_front.my_page

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.within_front.R

class MyGroupUpdateActivity : AppCompatActivity() {
    private var unitItems = arrayOf("미사일 사령부", "소속 부대 2", "소속 부대 3")
    private var positionItems = arrayOf("운전병", "행정병", "통신병", "의무병")
    private var mbtiItems = arrayOf("ENFJ", "ENTJ", "ENFP", "ENTP", "ESFP", "ESFJ", "ESTP", "ESTJ", "INFP", "INFJ", "INTP", "ISTP", "ISFP", "ISFJ", "ISTJ", "INTJ")

    private val backButton : ImageButton by lazy{
        findViewById(R.id.back_button)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_group_update)

        initBackButton()

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
    }
    private fun initBackButton(){
        backButton.setOnClickListener{
            finish()
        }
    }
}