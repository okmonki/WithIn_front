package com.example.within_front.board

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.R

class BoardAdapter(val context: Context, private var boardList : MutableList<Board>) : RecyclerView.Adapter<BoardAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val boardName = itemView.findViewById<TextView>(R.id.board_name)
        private val boardExplanation = itemView.findViewById<TextView>(R.id.board_explanation)

        fun bind(board : Board){
            boardName.text = board.boardName
            boardExplanation.text = board.boardExplanation

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.board_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val board = boardList[position]
        holder.apply{
            bind(board)
        }
    }

    override fun getItemCount(): Int {
        return boardList.size
    }
}
