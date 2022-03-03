package com.wugou.utils

import android.content.Context

fun dp2px(context: Context, dpValue: Float): Float {
    val scale = context.resources.displayMetrics.density
    return dpValue * scale + 0.5f
}

fun sp2px(context: Context, spValue: Float): Float {
    val fontScale = context.resources.displayMetrics.scaledDensity
    return spValue * fontScale + 0.5f
}