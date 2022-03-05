package com.wugou.classifyview

import android.view.View
import androidx.annotation.LayoutRes

interface ContentViewListener {
    @LayoutRes
    fun getContentViewResId(): Int

    fun bindData(position: Int, view: View?)
}