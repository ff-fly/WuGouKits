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
            var isScrollLeft = false
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.i(TAG, "onScrolled dx:$dx, dy:$dy")
                isScrollLeft = dx > 0
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_IDLE) {
                    val selectorLocationX = selectorView.left
                    firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition()
                    lastVisibleItemPos = layoutManager.findLastVisibleItemPosition()
                    val midPos = (firstVisibleItemPos + lastVisibleItemPos) / 2
                    var itemView =
                        dateRecyclerView.findViewHolderForAdapterPosition(midPos)!!.itemView
                    val itemLocation = IntArray(2)
                    itemView.getLocationOnScreen(itemLocation)
                    var itemLocationX = itemLocation[0]

                    if (isScrollLeft && itemLocationX < selectorLocationX) {
                        var targetPos = midPos + 1
                        if (midPos >= dateAdapter.itemCount - dateAdapter.headerCount - 1) {
                            targetPos = dateAdapter.itemCount - dateAdapter.headerCount - 1
                        }
                        itemView =
                            dateRecyclerView.findViewHolderForAdapterPosition(targetPos)!!.itemView
                        itemView.getLocationOnScreen(itemLocation)
                        itemLocationX = itemLocation[0]
                    }
                    if (!isScrollLeft && itemLocationX > selectorLocationX) {
                        var targetPos = midPos - 1
                        if (midPos <= dateAdapter.headerCount) {
                            targetPos = dateAdapter.headerCount
                        }
                        itemView =
                            dateRecyclerView.findViewHolderForAdapterPosition(targetPos)!!.itemView
                        itemView.getLocationOnScreen(itemLocation)
                        itemLocationX = itemLocation[0]
                    }

                    itemView.post {
                        dateRecyclerView.smoothScrollBy(itemLocationX - selectorLocationX, 0)
                    }
                    Log.i(
                        TAG,
                        "onScrollStateChanged, " +
                                "firstVisibleItemPos:$firstVisibleItemPos, " +
                                "lastVisibleItemPos:$lastVisibleItemPos, midPos:$midPos, " +
                                "selectorLocationX:$selectorLocationX, itemLocationX:$itemLocationX"
                    )
                }
            }
        })
    }

    fun setYear(year: Int) {
        dateAdapter.setYear(year)
    }

    fun setDate(year: Int, month: Int, day: Int) {
        dateAdapter.setDate(year, month, day)
    }
}