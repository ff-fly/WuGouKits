package com.wugou.dateselectview

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wugou.utils.dp2px

class DateRecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TAG = "DateRecyclerAdapter"
        private const val ITEM_TYPE_HEADER = 0
        private const val ITEM_TYPE_CONTENT = 1
        private const val ITEM_TYPE_BOTTOM = 2
    }

    private var headerCount: Int
    private val dataList = mutableListOf<Int>()
    private var curYear: Int = 0

    init {
        val itemSize = dp2px(context, 40f)
        val screenWidth = context.resources.displayMetrics.widthPixels
        headerCount = (screenWidth / (itemSize * 2)).toInt()
        Log.i(TAG, "init headerCount:$headerCount")
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setYear(year: Int) {
        Log.i(TAG, "setYear:$year, curYear:$curYear")
        if (year == curYear) {
            return
        }

        curYear = year
        dataList.clear()
//        for (month in 1..12) {
//            val days = getDaysOfMonth(year, month)
//            for (day in 1..days) {
//                dataList.add(day)
//            }
//        }
        for (month in 0..100) {
            dataList.add(month)
        }
        notifyDataSetChanged()
    }

    fun setDate(year: Int, month: Int, day: Int) {
        Log.i(TAG, "setYear:$year, month:$month, day:$day, curYear:$curYear")
        setYear(year)
    }

    override fun getItemViewType(position: Int): Int {
        if (position < headerCount) {
            return ITEM_TYPE_HEADER
        }
        if (position >= (headerCount + dataList.size)) {
            return ITEM_TYPE_BOTTOM
        }

        return ITEM_TYPE_CONTENT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_CONTENT) {
            val itemView =
                LayoutInflater.from(context).inflate(R.layout.layout_date_item, parent, false)
            DateViewHolder(itemView)
        } else {
            val itemView =
                LayoutInflater.from(context).inflate(R.layout.layout_date_header, parent, false)
            HeaderViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DateViewHolder) {
            holder.date.text = dataList[position - headerCount].toString()
            holder.date.setBackgroundResource(R.drawable.shape_date_background)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size + (2 * headerCount)
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.tv_date)
    }
}