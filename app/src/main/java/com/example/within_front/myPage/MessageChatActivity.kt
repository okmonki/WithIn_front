package com.example.within_front.myPage

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.R
import com.example.within_front.myPage.Message
import com.example.within_front.myPage.MessageBoxAdapter
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class MessageChatActivity : AppCompatActivity() {

    val client = OkHttpClient()

    private var messageChatList = mutableListOf<Message>()

    private val messageChatContainer: RecyclerView by lazy{
        findViewById(R.id.message_chat_container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_chat)
        val intent = intent
        // TODO val userId
        val partnerId = intent.getLongExtra("partnerId", 0)
        if(partnerId == 0L){
            Toast.makeText(this, "메세지 조회에 실패했습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
        // TODO getMessageChat(userId, partnerId)
    }

    private fun initRecyclerView(){
        messageChatContainer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        messageChatContainer.setHasFixedSize(true)

        messageChatContainer.adapter = MessageBoxAdapter(this, messageChatList)
    }

    private fun getMessageChat(userId : Long, partnerId : Long){
        val getMessageChat = Request.Builder().addHeader("Content-Type", "application/json").url("http:52.78.137.155:8080/$userId/messages/$partnerId").build()

        client.newCall(getMessageChat).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("connection error", "인터넷 연결 불안정")
                runOnUiThread{
                    Toast.makeText(
                        this@MessageChatActivity,
                        "메세지 조회에 실패했습니다. 다시 시도해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.code() == 200){
                    messageChatList = mutableListOf()

                    val jsonArray = JSONArray(response.body()!!.string())

                    for(idx in 0 until jsonArray.length()){
                        val tempMessage = jsonArray[idx] as JSONObject
                        val nickname = tempMessage.getString("userNickname")
                        val content = tempMessage.getString("content")
                        val createdAt = tempMessage.getString("createdAt")

                        val message = Message(nickname = nickname, content = content, dateTime = createdAt)
                        messageChatList.add(message)
                    }
                    Log.d("messageChatList", messageChatList.size.toString())
                    runOnUiThread{
                        initRecyclerView()
                    }
                }
            }
        })
    }
}