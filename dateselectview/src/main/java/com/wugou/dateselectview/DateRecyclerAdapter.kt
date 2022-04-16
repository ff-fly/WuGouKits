package com.wugou.dateselectview

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wugou.utils.getDaysOfMonth

class DateRecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<DateRecyclerAdapter.DateViewHolder>() {
    private val dataList = mutableListOf<Int>()

    @SuppressLint("NotifyDataSetChanged")
    fun setYear(year: Int) {
        dataList.clear()
        for (month in 1..12) {
            val days = getDaysOfMonth(year, month)
            for (day in 1..days) {
                dataList.add(day)
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.layout_date_item, parent, false)
        return DateViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        holder.date.text = dataList[position].toString()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.tv_date)
    }
}