package com.example.myandroidtest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myandroidtest.R

class MyPagerAdapter(val isInternal:Boolean?=null) : RecyclerView.Adapter<MyPagerAdapter.ViewHolder>() {

    class ViewHolder(  itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv: TextView = itemView.findViewById(R.id.tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_simple, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv.text = position.toString()
    }

    override fun getItemCount(): Int {
        return  2
    }
}
