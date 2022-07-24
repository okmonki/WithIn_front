package com.example.within_front.interest

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.within_front.R

class InterestActivity : AppCompatActivity() {
    val interestMBTISpinner: Spinner by lazy{
        findViewById(R.id.interestSpinner2)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interest) //view 연결

        val interestMBTIList= listOf("INFJ","INFP","ENFJ","ENFP","ISTJ","ISFJ","ESTJ","ESFJ","ISTP","ISFP","ESTP","ESFP")
        var interestMBTIAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, interestMBTIList)
        interestMBTISpinner.adapter=interestMBTIAdapter
        interestMBTISpinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }
}