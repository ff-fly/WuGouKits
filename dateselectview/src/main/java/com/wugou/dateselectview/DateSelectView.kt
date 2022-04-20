package com.wugou.dateselectview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.wugou.dateselectview.callback.DateSelectListener
import java.util.*

class DateSelectView : FrameLayout {
    companion object {
        const val TAG = "DateSelectView"
    }

    private var dateRecyclerView: RecyclerView
    private var weekDayTextView: TextView
    private var monthTextView: TextView
    private var yearTextView: TextView
    private var selectorView: ImageView
    private var dateAdapter: DateRecyclerAdapter

    private var firstVisibleItemPos = 0
    private var lastVisibleItemPos = 0
    private var isScrollLeft = false

    var dateSelectListener: DateSelectListener? = null

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
        selectorView = root.findViewById(R.id.iv_date_selector)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        dateRecyclerView.layoutManager = layoutManager
        dateAdapter = DateRecyclerAdapter(context)
        dateRecyclerView.adapter = dateAdapter

        dateRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                isScrollLeft = dx > 0
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_IDLE) {
                    adjustItemPosition()
                }
            }
        })
    }

    fun setYear(year: Int) {
        setDate(year, 12, 31)
    }

    fun setDate(year: Int, month: Int, day: Int) {
        val pos = dateAdapter.setDate(year, month, day)
        dateRecyclerView.post {
            toPosition(pos)
        }
    }

    private fun toPosition(pos: Int) {
        val targetPos = pos + dateAdapter.headerCount
        dateRecyclerView.scrollToPosition(targetPos)
        dateRecyclerView.post {
            val selectorLocationX = selectorView.left
            val itemView =
                dateRecyclerView.findViewHolderForAdapterPosition(targetPos)!!.itemView
            val itemLocation = IntArray(2)
            itemView.getLocationOnScreen(itemLocation)
            val itemLocationX = itemLocation[0]
            dateRecyclerView.smoothScrollBy(itemLocationX - selectorLocationX, 0)
        }
    }

    private fun adjustItemPosition() {
        val selectorLocationX = selectorView.left
        val layoutManager = dateRecyclerView.layoutManager as LinearLayoutManager
        firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition()
        lastVisibleItemPos = layoutManager.findLastVisibleItemPosition()
        val midPos = (firstVisibleItemPos + lastVisibleItemPos) / 2
        var itemView =
            dateRecyclerView.findViewHolderForAdapterPosition(midPos)!!.itemView
        val itemLocation = IntArray(2)
        itemView.getLocationOnScreen(itemLocation)
        var itemLocationX = itemLocation[0]

        var targetPos: Int = midPos
        if (isScrollLeft && itemLocationX < selectorLocationX) {
            targetPos = midPos + 1
            if (midPos >= dateAdapter.itemCount - dateAdapter.headerCount - 1) {
                targetPos = dateAdapter.itemCount - dateAdapter.headerCount - 1
            }
            itemView =
                dateRecyclerView.findViewHolderForAdapterPosition(targetPos)!!.itemView
            itemView.getLocationOnScreen(itemLocation)
            itemLocationX = itemLocation[0]
        }
        if (!isScrollLeft && itemLocationX > selectorLocationX) {
            targetPos = midPos - 1
            if (midPos <= dateAdapter.headerCount) {
                targetPos = dateAdapter.headerCount
            }
            itemView =
                dateRecyclerView.findViewHolderForAdapterPosition(targetPos)!!.itemView
            itemView.getLocationOnScreen(itemLocation)
            itemLocationX = itemLocation[0]
        }

        dateRecyclerView.smoothScrollBy(itemLocationX - selectorLocationX, 0)

        callbackSelectedDate(targetPos - dateAdapter.headerCount)
        Log.d(
            TAG,
            "onScrollStateChanged, " +
                    "firstVisibleItemPos:$firstVisibleItemPos, " +
                    "lastVisibleItemPos:$lastVisibleItemPos, midPos:$midPos, " +
                    "selectorLocationX:$selectorLocationX, itemLocationX:$itemLocationX"
        )
    }

    private fun callbackSelectedDate(targetPos: Int) {
        val triple = dateAdapter.getDateByPos(targetPos)
        Log.i(TAG, "callbackSelectedDate pos:$targetPos, date:$triple")
        yearTextView.text = triple.first.toString()
        monthTextView.text = triple.second.toString()
        val calendar = Calendar.getInstance()
        calendar.set(triple.first, triple.second - 1, triple.third)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        weekDayTextView.text = getDayOfWeek(dayOfWeek)
        dateSelectListener?.onDateSelected(triple.first, triple.second, triple.third)
    }

    private fun getDayOfWeek(dayOfWeek: Int): String {
        return when (dayOfWeek) {
            Calendar.SUNDAY -> "周日"
            Calendar.MONDAY -> "周一"
            Calendar.TUESDAY -> "周二"
            Calendar.WEDNESDAY -> "周三"
            Calendar.THURSDAY -> "周四"
            Calendar.FRIDAY -> "周五"
            Calendar.SATURDAY -> "周六"
            else -> "ERROR"
        }
    }
}