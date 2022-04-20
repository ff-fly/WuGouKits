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
import com.wugou.utils.getDaysOfMonth

class DateRecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TAG = "DateRecyclerAdapter"
        private const val ITEM_TYPE_HEADER = 0
        private const val ITEM_TYPE_CONTENT = 1
        private const val ITEM_TYPE_BOTTOM = 2
    }

    var headerCount: Int
    private val dataList = mutableListOf<Int>()
    private var curYear: Int = 0

    init {
        val itemSize = dp2px(context, 40f)
        val screenWidth = context.resources.displayMetrics.widthPixels
        headerCount = (screenWidth / (itemSize * 2)).toInt() + 1
        Log.i(TAG, "init headerCount:$headerCount")
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDate(year: Int, month: Int, day: Int): Int {
        Log.i(TAG, "setYear:$year, month:$month, day:$day, curYear:$curYear")
        if (year == curYear) {
            return getPositionOfDay(month, day)
        }

        curYear = year
        dataList.clear()
        for (m in 1..12) {
            val days = getDaysOfMonth(year, m)
            for (d in 1..days) {
                dataList.add(d)
            }
        }
        notifyDataSetChanged()

        return getPositionOfDay(month, day)
    }

    fun getDateByPos(targetPos: Int): Triple<Int, Int, Int> {
        var totalDays = 0
        val targetDate = targetPos + 1
        for (m in 1..12) {
            val days = getDaysOfMonth(curYear, m)
            totalDays += days
            if (targetDate <= totalDays) {
                return Triple(curYear, m, days - (totalDays - targetDate))
            }
        }
        return Triple(curYear, 12, 31)
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

    private fun getPositionOfDay(month: Int, day: Int): Int {
        var finalDay = day
        val maxDay = getDaysOfMonth(curYear, month)
        if (finalDay > maxDay) {
            finalDay = maxDay
        }
        var totalDays = 0
        for (m in 1 until month) {
            totalDays += getDaysOfMonth(curYear, m)
        }
        return totalDays + finalDay - 1
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.tv_date)
    }
}