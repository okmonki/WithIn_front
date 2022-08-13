package com.example.within_front.myPage

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

class MessageBoxActivity : AppCompatActivity() {

    private val client = OkHttpClient()

    private val messageBoxContainer : RecyclerView by lazy{
        findViewById(R.id.messagebox_container)
    }

    private var messageList = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypost)
        // TODO getMyMessages(userId)
    }

    private fun initRecyclerView(){
        messageBoxContainer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        messageBoxContainer.setHasFixedSize(true)

        messageBoxContainer.adapter = MessageBoxAdapter(this, messageList)
    }

    private fun getMyMessages(userId : Long){
        val getMessageRequest = Request.Builder().addHeader("Content-Type", "application/json").url("http://52.78.137.155:8080/user/$userId/messages").build()
        client.newCall(getMessageRequest).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("fail", "내 메세지 조회 실패")
                runOnUiThread{
                    Toast.makeText(
                        this@MessageBoxActivity,
                        "내 메세지 조회에 실패했습니다. 다시 시도해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.code() == 200){
                    messageList = mutableListOf()

                    val jsonArray = JSONArray(response.body()!!.string())

                    for(idx in 0 until jsonArray.length()){
                        val tempMessage = jsonArray[idx] as JSONObject
                        val partner = tempMessage.getString("partnerNickname")
                        val partnerId = tempMessage.getLong("partnerId")
                        val content = tempMessage.getString("content")
                        val createdAt = tempMessage.getString("createdAt")

                        val message = Message(nickname = partner, userId = partnerId, content = content, dateTime = createdAt)
                        messageList.add(message)
                    }
                    Log.d("messageList", messageList.size.toString())
                    runOnUiThread{
                        initRecyclerView()
                    }
                }
            }
        })
    }
}