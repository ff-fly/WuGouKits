package com.wugou.wugoukits.classifydemo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.wugou.classifyview.ContentViewListener
import com.wugou.classifyview.WgClassifyView
import com.wugou.classifyview.entity.ClassifyItem
import com.wugou.wugoukits.R

class ClassifyActivity : AppCompatActivity() {
    companion object {
        const val TAG = "ClassifyActivityVIP"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classify)

        val wgClassifyView = findViewById<WgClassifyView>(R.id.wg_classify_view)
        wgClassifyView.setContentViewListener(object : ContentViewListener {
            override fun getContentViewResId(): Int {
                return R.layout.layout_vp_content
            }

            override fun bindData(position: Int, view: View?) {
                (view as? ClassifyCanBindView)?.let {
                    Log.i(TAG, "bindData pos:$position, view:$view")
                    it.bindData(position)
                }
            }
        })
        val drawable = resources.getDrawable(R.drawable.ic_ball)
        wgClassifyView.setSelectedDrawable(drawable)
        val list = mutableListOf<ClassifyItem>()
        list.add(ClassifyItem("周一"))
        list.add(ClassifyItem("周二"))
        list.add(ClassifyItem("周三"))
        list.add(ClassifyItem("周四"))
        list.add(ClassifyItem("周五"))
        list.add(ClassifyItem("周六"))
        list.add(ClassifyItem("周日"))
        wgClassifyView.setClassifyList(list)
    }
}