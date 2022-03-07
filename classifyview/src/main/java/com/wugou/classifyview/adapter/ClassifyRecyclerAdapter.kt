package com.wugou.classifyview.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.RECTANGLE
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wugou.classifyview.R
import com.wugou.classifyview.entity.ClassifyItem
import com.wugou.classifyview.entity.DEF_ITEM_HEIGHT_DP
import com.wugou.classifyview.entity.DEF_NORMAL_BG_COLOR
import com.wugou.classifyview.entity.DEF_NORMAL_TEXT_COLOR
import com.wugou.classifyview.entity.DEF_NORMAL_TEXT_SP
import com.wugou.classifyview.entity.DEF_SELECTED_BG_COLOR
import com.wugou.classifyview.entity.DEF_SELECTED_CORNER_DP
import com.wugou.classifyview.entity.DEF_SELECTED_TEXT_COLOR
import com.wugou.classifyview.entity.DEF_SELECT_TEXT_SP
import com.wugou.utils.dp2px
import com.wugou.utils.sp2px


class ClassifyRecyclerAdapter(private val context: Context) : RecyclerView.Adapter<ClassifyRecyclerAdapter.ClassifyViewHolder>(), View.OnClickListener {
    companion object {
        const val TAG = "ClassifyRecyclerAdapter"
    }

    //ui configs
    private var normalTextPix = sp2px(context, DEF_NORMAL_TEXT_SP.toFloat()).toInt()
    private var normalTextColor = DEF_NORMAL_TEXT_COLOR
    private var normalBgColor = DEF_NORMAL_BG_COLOR
    private var selectedTextPix = sp2px(context, DEF_SELECT_TEXT_SP.toFloat()).toInt()
    private var selectedTextColor = DEF_SELECTED_TEXT_COLOR
    private var selectedBgColor = DEF_SELECTED_BG_COLOR
    private var heightPix = dp2px(context, DEF_ITEM_HEIGHT_DP.toFloat()).toInt()
    private var selectedCornerPix = dp2px(context, DEF_SELECTED_CORNER_DP.toFloat())

    private var dataList = ArrayList<ClassifyItem>()
    private var itemClickListener: ListItemClickListener? = null
    private var selectedPos = 0

    fun setConfigs(heightPix: Int, normalTextColor: Int, selectedTextColor: Int,
                   normalBgColor: Int, selectedBgColor: Int, normalTextPix: Int,
                   selectedTextPix: Int, selectedCorner: Float) {
        this.heightPix = heightPix
        this.normalTextColor = normalTextColor
        this.selectedTextColor = selectedTextColor
        this.normalBgColor = normalBgColor
        this.selectedBgColor = selectedBgColor
        this.normalTextPix = normalTextPix
        this.selectedTextPix = selectedTextPix
        this.selectedCornerPix = selectedCorner
    }

    fun setData(list: List<ClassifyItem>) {
        dataList.clear()
        dataList.addAll(list)
    }

    fun setSelected(pos: Int) {
        Log.i(TAG, "setSelected pos:$pos, itemCount:$itemCount")
        if (pos in 0 until itemCount) {
            selectedPos = pos
            notifyDataSetChanged()
        }
    }

    fun setItemClickListener(listener: ListItemClickListener?) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassifyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.layout_list_item, parent, false)
        val params = itemView.layoutParams
        params.height = heightPix
        itemView.layoutParams = params
        return ClassifyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ClassifyViewHolder, position: Int) {
        val data = dataList[position]
        holder.itemTextView.text = data.itemName
        holder.itemView.tag = position
        holder.itemView.setOnClickListener(this)
        when (position) {
            selectedPos -> {
                holder.itemView.setBackgroundColor(selectedBgColor)
                holder.itemTextView.setTextColor(selectedTextColor)
                holder.itemTextView.textSize = selectedTextPix.toFloat()
            }
            selectedPos - 1 -> {
                setNormalItemStyle(position, selectedCornerPix.toInt(), holder)
            }
            selectedPos + 1 -> {
                setNormalItemStyle(position, selectedCornerPix.toInt(), holder)
            }
            else -> {
                setNormalItemStyle(position, 0, holder)
            }
        }
    }

    override fun onClick(v: View?) {
        v?.tag?.let { pos ->
            setSelected(pos as Int)
            itemClickListener?.onItemClick(pos)
        }
    }

    private fun setNormalItemStyle(position: Int, corner: Int, holder: ClassifyViewHolder) {
        var posPair: Pair<Int, Int>? = null
        if (corner > 0) {
            if (position == selectedPos - 1) {
                posPair = Pair(4, 5)
            }
            if (position == selectedPos + 1) {
                posPair = Pair(2, 3)
            }
        }

        posPair?.apply {
            val drawable = GradientDrawable()
            drawable.shape = RECTANGLE
            drawable.setColor(normalBgColor)
            drawable.cornerRadii = FloatArray(8).apply {
                set(first, selectedCornerPix)
                set(second, selectedCornerPix)
            }
            holder.itemView.background = drawable
        } ?: holder.itemView.setBackgroundColor(normalBgColor)

        holder.itemTextView.setTextColor(normalTextColor)
        holder.itemTextView.textSize = normalTextPix.toFloat()
    }

    class ClassifyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTextView: TextView = itemView.findViewById(R.id.tv_item_text)
    }
}