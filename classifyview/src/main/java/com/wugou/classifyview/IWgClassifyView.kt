package com.wugou.classifyview

import android.graphics.drawable.Drawable
import com.wugou.classifyview.entity.ClassifyItem

interface IWgClassifyView {
    /**
     * 设置item数据，设置之后ui就会更新
     */
    fun setClassifyList(list: List<ClassifyItem>?)

    /**
     * 在setClassifyList之前调用
     */
    fun setContentViewListener(listener: ContentViewListener?)

    /**
     * 设置当前选中item左侧的drawable
     */
    fun setSelectedDrawable(drawable: Drawable?)
}