package com.wugou.classifyview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wugou.classifyview.adapter.ClassifyRecyclerAdapter
import com.wugou.classifyview.entity.ClassifyItem

class WgClassifyViewEntry(private val parent: ViewGroup) : IWgClassifyViewEntry {
    companion object {
        private const val TAG = "WgClassifyViewEntry"
    }

    private val recyclerView: RecyclerView
    private val containerView: FrameLayout
    private val classifyItemList = ArrayList<ClassifyItem>()
    private val classifyAdapter = ClassifyRecyclerAdapter()

    init {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.layout_classify_container, parent)
        recyclerView = root.findViewById(R.id.rc_classify)
        containerView = root.findViewById(R.id.fl_content_container)

        recyclerView.layoutManager = LinearLayoutManager(parent.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(parent.context, DividerItemDecoration.HORIZONTAL))
        recyclerView.adapter = classifyAdapter
    }

    override fun setClassifyList(list: List<ClassifyItem>?) {
        Log.i(TAG, "setClassifyList:$list")
        classifyItemList.clear()
        list?.let {
            classifyItemList.addAll(it)
        }
        classifyAdapter.setData(classifyItemList)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}