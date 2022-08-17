package com.example.within_front.myPage

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.*
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
import java.lang.Thread.sleep
import java.time.LocalDateTime

class MessageChatActivity : AppCompatActivity() {

    val client = OkHttpClient()

    private var messageChatList = mutableListOf<Message>()

    private val backButton : ImageButton by lazy{
        findViewById(R.id.back_button)
    }

    private val userName : TextView by lazy{
        findViewById(R.id.user_name)
    }

    private val messageChatContainer: RecyclerView by lazy{
        findViewById(R.id.message_chat_container)
    }

    private val inputBox : EditText by lazy{
        findViewById(R.id.input_box)
    }

    private val submitButton: Button by lazy{
        findViewById(R.id.submit_button)
    }

    private val messageChatAdapter : MessageChatAdapter by lazy{
        MessageChatAdapter(this, messageChatList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_chat)
        //val intent = intent
        // TODO val userId
        //val partnerId = intent.getLongExtra("partnerId", 0)
        //val partnerNickname = intent.getStringExtra("partnerNickname")
//        if(partnerId == 0L){
//            Toast.makeText(this, "메세지 조회에 실패했습니다.", Toast.LENGTH_SHORT).show()
//            finish()
//        }
        initBackButton()
        initUserName("test") // TODO
        //initInputBox()
        initSubmitButton(1, 2) // TODO
        getMessageChat(1, 2) // TODO
    }

    private fun initUserName(name : String){
        userName.text = name
    }

    private fun initBackButton(){
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun initRecyclerView(){
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        messageChatContainer.layoutManager = layoutManager
        messageChatContainer.setHasFixedSize(true)

        messageChatContainer.adapter = messageChatAdapter
        messageChatContainer.smoothScrollToPosition(messageChatList.size)
    }

    private fun initInputBox(){
        TODO("recycler view 내려주기")
    }

    private fun initSubmitButton(userId: Long, partnerId: Long){
        submitButton.setOnClickListener {
            if(inputBox.text.isEmpty()){
                // do nothing
            } else{
                val messageData = "{\"content\": \"${inputBox.text}\"}"
                val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), messageData)
                val sendMessageRequest = Request.Builder().addHeader("Content-Type", "application/json").url("http:52.78.137.155:8080/user/$userId/messages/$partnerId").post(body).build()

                client.newCall(sendMessageRequest).enqueue(object : Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        Log.d("fail", "메세지 전송 실패")
                        runOnUiThread{
                            Toast.makeText(
                                this@MessageChatActivity,
                                "메세지 전송에 실패하였습니다. 다시 시도해주세요.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    @SuppressLint("NewApi")
                    override fun onResponse(call: Call, response: Response) {
                        if(response.code() == 200){
                            Log.d("success", "메세지 전송 성공")
                            messageChatList.add(Message(nickname = "", content = inputBox.text.toString(), dateTime = LocalDateTime.now().toString().substring(11..15)))
                            Log.d("list", messageChatList.lastOrNull().toString())
                            runOnUiThread {
                                messageChatAdapter.notifyItemInserted(messageChatList.size)
                                messageChatContainer.smoothScrollToPosition(messageChatList.size)
                                inputBox.setText("")
                            }
                        }
                    }
                })
            }
        }
    }

    private fun getMessageChat(userId : Long, partnerId : Long){
        val getMessageChat = Request.Builder().addHeader("Content-Type", "application/json").url("http:52.78.137.155:8080/user/$userId/messages/$partnerId").build()

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
                        val partner = tempMessage.getString("userNickname")
                        val content = tempMessage.getString("content")
                        val createdAt = tempMessage.getString("createdAt").substring(11..15)

                        val message = Message(nickname = partner, content = content, dateTime = createdAt)
                        messageChatList.add(message)
                    }
                    Log.d("messageChatList", messageChatList.size.toString())
                    runOnUiThread{
                        initRecyclerView()
                    }
                } else{
                    Log.d("connection error", "인터넷 연결 불안정, ${response.code()}")
                    runOnUiThread{
                        Toast.makeText(
                            this@MessageChatActivity,
                            "메세지 조회에 실패했습니다. 다시 시도해주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    /**
     * 현재 포커스된 뷰의 영역이 아닌 다른 곳을 클릭 시 키보드를 내리고 포커스 해제
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val focusView = currentFocus
        if (focusView != null) {
            val rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = ev.x.toInt()
            val y = ev.y.toInt()
            if (!rect.contains(x, y)) {
                val imm: InputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                if (imm != null) imm.hideSoftInputFromWindow(focusView.windowToken, 0)
                focusView.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}