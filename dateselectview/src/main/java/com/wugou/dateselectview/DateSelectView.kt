package com.wugou.dateselectview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DateSelectView : FrameLayout {
    companion object {
        const val TAG = "DateSelectView"
    }

    private var dateRecyclerView: RecyclerView
    private var weekDayTextView: TextView
    private var monthTextView: TextView
    private var yearTextView: TextView
    private var dateAdapter: DateRecyclerAdapter

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val root = LayoutInflater.from(context).inflate(R.layout.layout_date_select, this)
        weekDayTextView = root.findViewById(R.id.tv_weekday)
        monthTextView = root.findViewById(R.id.tv_month)
        yearTextView = root.findViewById(R.id.tv_year)
        dateRecyclerView = root.findViewById(R.id.rc_date)
        dateRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        dateAdapter = DateRecyclerAdapter(context)
        dateRecyclerView.adapter = dateAdapter
    }

    fun setYear(year: Int) {
        Log.i(TAG, "setYear:$year")
        dateAdapter.setYear(year)
    }
}