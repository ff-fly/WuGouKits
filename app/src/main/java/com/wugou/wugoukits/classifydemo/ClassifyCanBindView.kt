package com.wugou.wugoukits.classifydemo

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.TextView
import com.wugou.wugoukits.R

class ClassifyCanBindView : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun bindData(position: Int) {
        findViewById<TextView>(R.id.tv_item_text).text = "WUGOU->$position"
    }
}