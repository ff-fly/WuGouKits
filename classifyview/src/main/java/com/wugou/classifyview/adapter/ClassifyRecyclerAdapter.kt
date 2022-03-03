package com.wugou.classifyview.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wugou.classifyview.R
import com.wugou.classifyview.entity.ClassifyItem
import com.wugou.utils.sp2px


class ClassifyRecyclerAdapter(private val context: Context) : RecyclerView.Adapter<ClassifyRecyclerAdapter.ClassifyViewHolder>(), View.OnClickListener {
    companion object {
        const val TAG = "ClassifyRecyclerAdapter"
    }

    private var dataList = ArrayList<ClassifyItem>()
    private var itemClickListener: ListItemClickListener? = null
    private var selectedPos = 0

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
        return ClassifyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ClassifyViewHolder, position: Int) {
        val data = dataList[position]
        holder.itemTextView.text = data.itemName
        data.itemIcon?.let {
            holder.itemTextView.setCompoundDrawables(it, null, null, null)
        }
        holder.itemView.tag = position
        holder.itemView.setOnClickListener(this)
        if (position == selectedPos) {
            holder.itemTextView.setTextColor(Color.RED)
            holder.itemTextView.textSize = sp2px(context, 6f)
        } else {
            holder.itemTextView.setTextColor(Color.BLACK)
            holder.itemTextView.textSize = sp2px(context, 4f)
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