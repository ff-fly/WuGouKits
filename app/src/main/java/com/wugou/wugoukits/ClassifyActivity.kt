package com.wugou.wugoukits

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wugou.classifyview.IWgClassifyViewEntry
import com.wugou.classifyview.WgClassifyViewEntry
import com.wugou.classifyview.entity.ClassifyItem

class ClassifyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classify)

        val classifyEntry: IWgClassifyViewEntry = WgClassifyViewEntry(findViewById(R.id.fl_classify_root))
        val list = mutableListOf<ClassifyItem>()
        list.add(ClassifyItem("周一"))
        list.add(ClassifyItem("周二"))
        list.add(ClassifyItem("周三"))
        list.add(ClassifyItem("周四"))
        list.add(ClassifyItem("周五"))
        list.add(ClassifyItem("周六"))
        list.add(ClassifyItem("周日"))
        classifyEntry.setClassifyList(list)
    }
}