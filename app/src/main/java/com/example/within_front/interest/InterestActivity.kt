package com.example.within_front.interest

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.within_front.R

class InterestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interest) //view 연결

        val interestGroupSpinner: Spinner by lazy{
            findViewById(R.id.interestSpinner0)
        }
        val interestJobSpinner: Spinner by lazy{
            findViewById(R.id.interestSpinner1)}
        val interestMBTISpinner: Spinner by lazy{
            findViewById(R.id.interestSpinner2)}

        val interestMBTIList= listOf("INFJ","INFP","ENFJ","ENFP","ISTJ","ISFJ","ESTJ","ESFJ","ISTP","ISFP","ESTP","ESFP")
        val interestMBTIAdapter = ArrayAdapter<String>(this,R.layout.activity_interest, interestMBTIList)
        //interestMBTISpinner.adapter=interestMBTIAdapter
        //interestMBTISpinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
          //  override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            //    interestMBT.text=

           // }
       // }

        }
}