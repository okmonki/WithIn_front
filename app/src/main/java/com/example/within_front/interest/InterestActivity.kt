package com.example.within_front.interest

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.within_front.R

class InterestActivity : AppCompatActivity() {
    val interestGroupSpinner: Spinner by lazy{
        findViewById(R.id.interestSpinner0)
    }
    val interestJobSpinner: Spinner by lazy{
        findViewById(R.id.interestSpinner1)}

    val interestMBTISpinner: Spinner by lazy{
        findViewById(R.id.interestSpinner2)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interest) //view 연결

        val interestGroupList= listOf("1사단","25사단","28사단","5사단","6사단","3사단","15사단","7사단","21사단","12사단","22사단","23사단","27사단","9사단","수도기계화사단","8사단","11사단","2신속대응사단","60사단","66사단","72사단","73사단","75사단","52사단","56사단","51사단","55사단","31사단","32사단","35사단","36사단","37사단","39사단","50사단","53사단")
        var interestGroupAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, interestGroupList)
        interestGroupSpinner.adapter=interestGroupAdapter
        interestGroupSpinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val group=interestGroupList.get(position)
                Log.d("LoginActivity", "group: $group")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val interestJobList= listOf("기술행정병","어학병","전문특기병","임기제부사관","연고지복무병","최전방수호병")
        var interestJobAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, interestJobList)
        interestJobSpinner.adapter=interestJobAdapter
        interestJobSpinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val job=interestJobList.get(position)
                Log.d("LoginActivity", "job: $job")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val interestMBTIList= listOf("INFJ","INFP","ENFJ","ENFP","ISTJ","ISFJ","ESTJ","ESFJ","ISTP","ISFP","ESTP","ESFP")
        var interestMBTIAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, interestMBTIList)
        interestMBTISpinner.adapter=interestMBTIAdapter
        interestMBTISpinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val MBTI =interestMBTIList.get(position)
                Log.d("LoginActivity", "MBTI: $MBTI")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }
}