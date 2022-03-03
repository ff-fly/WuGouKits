package com.wugou.classifyview

import android.view.View

interface ShowViewListener {
    fun getContentView(position: Int): View
}