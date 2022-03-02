package com.wugou.classifyview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wugou.classifyview.R
import com.wugou.classifyview.entity.ClassifyItem


class ClassifyRecyclerAdapter : RecyclerView.Adapter<ClassifyRecyclerAdapter.ClassifyViewHolder>() {
    private var dataList = ArrayList<ClassifyItem>()

    fun setData(list: List<ClassifyItem>) {
        dataList.clear()
        dataList.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassifyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
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
    }

    class ClassifyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTextView: TextView = itemView.findViewById(R.id.tv_item_text)
    }
}