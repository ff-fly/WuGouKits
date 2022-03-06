package com.wugou.utils

import android.content.Context
import android.util.TypedValue

fun dp2px(context: Context, dpValue: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.resources.displayMetrics);
}

fun sp2px(context: Context, spValue: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.resources.displayMetrics);
}