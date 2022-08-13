package com.example.within_front.board

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.board.PostAdapter
import com.example.within_front.R
//import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class BoardActivity : AppCompatActivity() {

//    private val client = OkHttpClient()

    private val postContainer : RecyclerView by lazy{
        findViewById(R.id.posts)
    }

    private var postList = mutableListOf<Post>(Post("포스트 테스트입니다.","SuperH0ng", "ㅂㄷ제ㅐㅑ겹제ㅐ댜겨베ㅐㄷ쟈겨망ㄴ러;ㅣㄴ아ㅓㄻ;ㅣㄴ아렅크춮,ㅡㅊㅌㅋ푸.,ㅋ틏푸;ㅇㄴ미ㅏ럼ㄴ이;ㅏㅓㄻㅇㄴ;ㅣ라ㅓ", 4, 7),
        Post("포스트2","aaa", "포스트 본문 테스트입니다.", 4, 7),
        Post("포스트3","bbb", "post content test", 4, 7),
        Post("포스트 테스트입니다.","SuperH0ng", "ㅂㄷ제ㅐㅑ겹제ㅐ댜겨베ㅐㄷ쟈겨망ㄴ러;ㅣㄴ아ㅓㄻ;ㅣㄴ아렅크춮,ㅡㅊㅌㅋ푸.,ㅋ틏푸;ㅇㄴ미ㅏ럼ㄴ이;ㅏㅓㄻㅇㄴ;ㅣ라ㅓ", 4, 7),
        Post("포스트2","aaa", "포스트 본문 테스트입니다.", 4, 7),
        Post("포스트3","bbb", "post content test", 4, 7),
        Post("포스트 테스트입니다.","SuperH0ng", "ㅂㄷ제ㅐㅑ겹제ㅐ댜겨베ㅐㄷ쟈겨망ㄴ러;ㅣㄴ아ㅓㄻ;ㅣㄴ아렅크춮,ㅡㅊㅌㅋ푸.,ㅋ틏푸;ㅇㄴ미ㅏ럼ㄴ이;ㅏㅓㄻㅇㄴ;ㅣ라ㅓ", 4, 7),
        Post("포스트2","aaa", "포스트 본문 테스트입니다.", 4, 7),
        Post("포스트3","bbb", "post content test", 4, 7))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        initRecyclerView()
    }
    private fun initRecyclerView(){
        postContainer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        postContainer.setHasFixedSize(true)
        postContainer.adapter = PostAdapter(this, postList)
    }
}

//package com.example.within_front.board
//
//import android.os.Bundle
//import android.util.Log
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.within_front.board.PostAdapter
//import com.example.within_front.R
////import okhttp3.*
//import org.json.JSONArray
//import org.json.JSONObject
//import java.io.IOException
//
//class BoardActivity : AppCompatActivity() {
//
////    private val client = OkHttpClient()
//    lateinit var postAdapter: PostAdapter
//    private var postList = mutableListOf<Post>()
//
//
//    private val postContainer : RecyclerView by lazy{
//        findViewById(R.id.posts)
//    }
//
//
//    //Post("포스트 테스트입니다.","SuperH0ng", "ㅂㄷ제ㅐㅑ겹제ㅐ댜겨베ㅐㄷ쟈겨망ㄴ러;ㅣㄴ아ㅓㄻ;ㅣㄴ아렅크춮,ㅡㅊㅌㅋ푸.,ㅋ틏푸;ㅇㄴ미ㅏ럼ㄴ이;ㅏㅓㄻㅇㄴ;ㅣ라ㅓ", 4, 7),
//    //        Post("포스트2","aaa", "포스트 본문 테스트입니다.", 4, 7),
//    //        Post("포스트3","bbb", "post content test", 4, 7)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_board)
//        initRecyclerView()
//    }
//    private fun initRecyclerView(){
////        postContainer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
////        postContainer.setHasFixedSize(true)
////        postContainer.adapter = PostAdapter(this, postList)
//
//        postAdapter = PostAdapter(this)
//        rv_post.adapter = postAdapter
//    }
//}

