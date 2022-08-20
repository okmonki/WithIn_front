package com.example.within_front.board

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.R
import com.example.within_front.base.BaseActivity
//import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class BelongActivity : BaseActivity() {

//    private val client = OkHttpClient()

    private val belongContainer : RecyclerView by lazy{
        findViewById(R.id.boards)
    }

    private var boardList = mutableListOf<Board>(
        Board("칭찬 게시판", "부대 미담 관련 글"),
        Board("건의 게시판", "운전병 게시판"),
        Board("칭찬 게시판", "부대 미담 관련 글"),
        Board("건의 게시판", "운전병 게시판"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_belong)
        initRecyclerView()
        initNavigation("board")
    }
    private fun initRecyclerView(){
        belongContainer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        belongContainer.setHasFixedSize(true)
        belongContainer.adapter = BoardAdapter(this, boardList)
    }
}