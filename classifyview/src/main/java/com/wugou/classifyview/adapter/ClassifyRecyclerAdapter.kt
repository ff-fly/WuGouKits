package com.wugou.classifyview.adapter

import android.content.Context
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

    private var dataList = ArrayList<ClassifyItem>()
    private var itemClickListener: ListItemClickListener? = null
    private var selectedPos = 0

    fun setConfigs(heightPix: Int, normalTextColor: Int, selectedTextColor: Int,
                   normalBgColor: Int, selectedBgColor: Int, normalTextPix: Int, selectedTextPix: Int) {
        this.heightPix = heightPix
        this.normalTextColor = normalTextColor
        this.selectedTextColor = selectedTextColor
        this.normalBgColor = normalBgColor
        this.selectedBgColor = selectedBgColor
        this.normalTextPix = normalTextPix
        this.selectedTextPix = selectedTextPix
    }

    fun setData(list: List<ClassifyItem>) {
        dataList.clear()
        dataList.addAll(list)
    }

    fun setSelected(pos: Int) {
        Log.i(TAG, "setSelected pos:$pos, itemCount:$itemCount")
        if (pos in 0 until itemCount) {
            notifyItemChanged(selectedPos)
            selectedPos = pos
            notifyItemChanged(pos)
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
        if (position == selectedPos) {
            holder.itemView.setBackgroundColor(selectedBgColor)
            holder.itemTextView.setTextColor(selectedTextColor)
            holder.itemTextView.textSize = selectedTextPix.toFloat()
        } else {
            holder.itemView.setBackgroundColor(normalBgColor)
            holder.itemTextView.setTextColor(normalTextColor)
            holder.itemTextView.textSize = normalTextPix.toFloat()
        }
    }

    override fun onClick(v: View?) {
        v?.tag?.let { pos ->
            setSelected(pos as Int)
            itemClickListener?.onItemClick(pos)
        }
    }

    class ClassifyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTextView: TextView = itemView.findViewById(R.id.tv_item_text)
    }
}