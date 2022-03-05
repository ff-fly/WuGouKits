package com.wugou.classifyview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.wugou.classifyview.adapter.ClassifyPagerAdapter
import com.wugou.classifyview.adapter.ClassifyRecyclerAdapter
import com.wugou.classifyview.adapter.ListItemClickListener
import com.wugou.classifyview.entity.ClassifyItem

class WgClassifyViewEntry(parent: ViewGroup, listener: ContentViewListener?) : IWgClassifyViewEntry {
    companion object {
        private const val TAG = "WgClassifyViewEntry"
    }

    private val recyclerView: RecyclerView
    private val contentViewPager: ViewPager2

    private val listAdapter = ClassifyRecyclerAdapter(parent.context)
    private val pageAdapter = ClassifyPagerAdapter(listener)

    private val classifyItemList = ArrayList<ClassifyItem>()
    private var currentPos = 0

    init {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.layout_classify_container, parent)
        recyclerView = root.findViewById(R.id.rc_classify)
        contentViewPager = root.findViewById(R.id.vp_content_container)

        recyclerView.layoutManager = LinearLayoutManager(parent.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(parent.context, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = listAdapter
        listAdapter.setItemClickListener(object : ListItemClickListener {
            override fun onItemClick(pos: Int) {
                Log.i(TAG, "onItemClick:$pos")
                currentPos = pos
                contentViewPager.currentItem = pos
            }
        })

        contentViewPager.adapter = pageAdapter
        contentViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.i(TAG, "onPageSelected:$position")
                currentPos = position
                listAdapter.setSelected(position)
            }
        })
    }

    override fun setClassifyList(list: List<ClassifyItem>?) {
        Log.i(TAG, "setClassifyList:$list")
        classifyItemList.clear()
        list?.let {
            classifyItemList.addAll(it)
        }
        listAdapter.setData(classifyItemList)
        listAdapter.notifyDataSetChanged()

        pageAdapter.setCount(classifyItemList.size)
        pageAdapter.notifyDataSetChanged()
    }
}