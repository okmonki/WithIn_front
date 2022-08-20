package com.example.within_front.board

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.R
import com.example.within_front.base.BaseActivity
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class BelongActivity : BaseActivity() {

    private val client = OkHttpClient()

    private val belong : TextView by lazy {
        findViewById(R.id.belong)
    }

    private val belongContainer : RecyclerView by lazy{
        findViewById(R.id.boards)
    }

    private var boardList = mutableListOf<Board>()


    private val pref by lazy{
        getSharedPreferences(PostActivity.USER_INFO, MODE_PRIVATE)
    }
    private var userId = 1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_belong)
        pref.getLong("user id", -1)
        setUnit(userId)
        getBoard(userId)
        initNavigation("board")
    }
    private fun initRecyclerView(){
        belongContainer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        belongContainer.setHasFixedSize(true)
        belongContainer.adapter = BoardAdapter(this, boardList)
    }


    private fun getBoard(userId : Long){
        val getBoardRequest = Request.Builder().addHeader("Content-Type", "application/json").url("http:52.78.137.155:8080/post/$userId/boards").build()
        client.newCall(getBoardRequest).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("fail", "게시판 조회 실패")
                runOnUiThread{
                    Toast.makeText(
                        this@BelongActivity,
                        "게시판 조회에 실패했습니다. 다시 시도해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("good?", response.code().toString())
                Log.d("userId", userId.toString())
                if(response.code() == 200){
                    boardList = mutableListOf()
                    val jsonArray = JSONArray(response.body()!!.string())
                    for(idx in 0 until jsonArray.length()){
                        val tempBoard = jsonArray[idx] as JSONObject
                        val boardName = tempBoard.getString("category")
                        val boardExplanation = tempBoard.getString("explanation")
                        val boardId = tempBoard.getLong("boardId")
                        val board = Board(boardName, boardExplanation, boardId)
                        boardList.add(board)
                    }

                    Log.d("boardList", boardList.size.toString())
                    runOnUiThread{
                        initRecyclerView()
                    }
                }
            }
        })
    }

    private fun setUnit(userId : Long){
        val getUnitRequest = Request.Builder().addHeader("Content-Type", "application/json").url("http:52.78.137.155:8080/user/$userId/unit").build()

        client.newCall(getUnitRequest).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("fail", "소속 부대 조회 실패")
                runOnUiThread{
                    Toast.makeText(
                        this@BelongActivity,
                        "소속 부대 조회에 실패했습니다. 다시 시도해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.code() == 200){
                    val tempUnit = JSONObject(response.body()!!.string())
                    belong.text = tempUnit.getString("unitName")

                }
            }
        })
    }

    companion object{
        const val USER_INFO = "user info"
    }
}