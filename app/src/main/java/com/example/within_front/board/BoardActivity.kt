package com.example.within_front.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.board.PostAdapter
import com.example.within_front.R
import com.example.within_front.base.BaseActivity
import com.example.within_front.login.SignupActivity
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
//import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class BoardActivity : BaseActivity() {

    val client = OkHttpClient()

    val backImageButton: ImageButton by lazy {
        findViewById(R.id.arrowImg)
    }

    val pencilImageButton: ImageButton by lazy {
        findViewById(R.id.write_button)
    }

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

    val category : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        initRecyclerView()
        initBackImageButton()
        initPencilImageButton(initView())

        initNavigation("board")
    }

    private fun initRecyclerView(){
        postContainer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        postContainer.setHasFixedSize(true)
        postContainer.adapter = PostAdapter(this, postList)
    }

    private fun initBackImageButton(){
        backImageButton.setOnClickListener{
            finish()
        }
    }

    private fun initView() : String{
        val intent=intent
        val postId=intent.getStringExtra("category")
        return category!!
    }

    private fun getPosts(postId : Long){
        val getPostRequest = Request.Builder().addHeader("Content-Type", "http://52.78.137.155:8080/boards/$category")
    }

    private fun initPencilImageButton(category : String) {
        pencilImageButton.setOnClickListener {
            val intent = Intent(this, WriteANewPostActivity::class.java)
            intent.putExtra("category", category)
            startActivity(intent)
        }
    }
}

