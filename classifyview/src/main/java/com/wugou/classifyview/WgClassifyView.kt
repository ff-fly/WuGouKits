package com.wugou.classifyview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.wugou.classifyview.adapter.ClassifyPagerAdapter
import com.wugou.classifyview.adapter.ClassifyRecyclerAdapter
import com.wugou.classifyview.adapter.ListItemClickListener
import com.wugou.classifyview.entity.ClassifyItem

class WgClassifyView : RelativeLayout, IWgClassifyView {
    companion object {
        private const val TAG = "WgClassifyViewEntry"
    }

    private val recyclerView: RecyclerView
    private val contentViewPager: ViewPager2

    private val listAdapter = ClassifyRecyclerAdapter(context)
    private val pageAdapter = ClassifyPagerAdapter()

    private val classifyItemList = ArrayList<ClassifyItem>()
    private var currentPos = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.layout_classify_container, this)
        recyclerView = root.findViewById(R.id.rc_classify)
        contentViewPager = root.findViewById(R.id.vp_content_container)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
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

    override fun setContentViewListener(listener: ContentViewListener?) {
        Log.i(TAG, "setContentViewListener:$listener")
        pageAdapter.listener = listener
    }

    override fun setClassifyList(list: List<ClassifyItem>?) {
        Log.i(TAG, "setClassifyList:$list")
        classifyItemList.clear()
        list?.let {
            classifyItemList.addAll(it)
        }
        listAdapter.setData(classifyItemList)
        listAdapter.notifyDataSetChanged()

        pageAdapter.itemSize = classifyItemList.size
        pageAdapter.notifyDataSetChanged()
    }
}