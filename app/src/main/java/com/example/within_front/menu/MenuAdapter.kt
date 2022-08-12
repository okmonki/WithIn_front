package com.example.within_front.menu

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.within_front.R

class MenuAdapter(val mContext: Context, val menuList : MutableList<Menu>) : RecyclerView.Adapter<MenuAdapter.CustomViewHolder>(){

    class CustomViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        private val name = itemView.findViewById<TextView>(R.id.name)

        fun bind(menu : Menu){
            name.text = menu.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val menu = menuList[position]
        holder.apply{
            bind(menu)
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }
}