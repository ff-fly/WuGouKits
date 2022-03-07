package com.wugou.classifyview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.wugou.classifyview.adapter.ClassifyPagerAdapter
import com.wugou.classifyview.adapter.ClassifyRecyclerAdapter
import com.wugou.classifyview.adapter.ListItemClickListener
import com.wugou.classifyview.entity.ClassifyItem
import com.wugou.classifyview.entity.DEF_ITEM_HEIGHT_DP
import com.wugou.classifyview.entity.DEF_ITEM_WIDTH_DP
import com.wugou.classifyview.entity.DEF_NORMAL_BG_COLOR
import com.wugou.classifyview.entity.DEF_NORMAL_TEXT_COLOR
import com.wugou.classifyview.entity.DEF_NORMAL_TEXT_SP
import com.wugou.classifyview.entity.DEF_SELECTED_BG_COLOR
import com.wugou.classifyview.entity.DEF_SELECTED_CORNER_DP
import com.wugou.classifyview.entity.DEF_SELECTED_TEXT_COLOR
import com.wugou.classifyview.entity.DEF_SELECT_TEXT_SP
import com.wugou.utils.dp2px

class WgClassifyView : RelativeLayout, IWgClassifyView {
    companion object {
        private const val TAG = "WgClassifyViewEntry"
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var contentViewPager: ViewPager2

    private val listAdapter = ClassifyRecyclerAdapter(context)
    private val pageAdapter = ClassifyPagerAdapter()

    private val classifyItemList = ArrayList<ClassifyItem>()
    private var currentPos = 0

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        if (context == null) return

        val root = LayoutInflater.from(context).inflate(R.layout.layout_classify_container, this)
        recyclerView = root.findViewById(R.id.rc_classify)
        contentViewPager = root.findViewById(R.id.vp_content_container)

        setItemUiConfigs(context, attrs)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
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

    private fun setItemUiConfigs(context: Context?, attrs: AttributeSet?) {
        if (context == null) return
        val ta = context.obtainStyledAttributes(attrs, R.styleable.WgClassifyView)
        val heightPix = ta.getDimensionPixelSize(R.styleable.WgClassifyView_item_height_dp, dp2px(context, DEF_ITEM_HEIGHT_DP.toFloat()).toInt())
        val normalItemTextColor = ta.getColor(R.styleable.WgClassifyView_normal_item_text_color, DEF_NORMAL_TEXT_COLOR)
        val selectedItemTextColor = ta.getColor(R.styleable.WgClassifyView_selected_item_text_color, DEF_SELECTED_TEXT_COLOR)
        val normalItemBgColor = ta.getColor(R.styleable.WgClassifyView_normal_item_bg_color, DEF_NORMAL_BG_COLOR)
        val selectedItemBgColor = ta.getColor(R.styleable.WgClassifyView_selected_item_bg_color, DEF_SELECTED_BG_COLOR)
        val normalTextPix = ta.getDimensionPixelSize(R.styleable.WgClassifyView_normal_text_sp, DEF_NORMAL_TEXT_SP)
        val selectedTextPix = ta.getDimensionPixelSize(R.styleable.WgClassifyView_selected_text_sp, DEF_SELECT_TEXT_SP)
        val selectedCorner = ta.getDimension(R.styleable.WgClassifyView_selected_item_corner, dp2px(context, DEF_SELECTED_CORNER_DP.toFloat()))
        val defaultItemWidth = ta.getDimensionPixelSize(R.styleable.WgClassifyView_list_item_width, dp2px(context, DEF_ITEM_WIDTH_DP.toFloat()).toInt())
        ta.recycle()

        val params = recyclerView.layoutParams
        params.width = defaultItemWidth
        recyclerView.layoutParams = params
        listAdapter.setConfigs(heightPix, normalItemTextColor, selectedItemTextColor,
                normalItemBgColor, selectedItemBgColor, normalTextPix, selectedTextPix, selectedCorner)
    }
}