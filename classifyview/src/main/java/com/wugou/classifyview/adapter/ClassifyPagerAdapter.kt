package com.wugou.classifyview.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wugou.classifyview.R


class ClassifyPagerAdapter : RecyclerView.Adapter<ClassifyPagerAdapter.ClassifyViewHolder>() {
    companion object {
        private const val TAG = "ClassifyPagerAdapter"
    }

    private var count = 0

    fun setCount(count: Int) {
        Log.i(TAG, "setCount:$count")
        this.count = count
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassifyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_content_item, parent, false)
        return ClassifyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return count
    }

    override fun onBindViewHolder(holder: ClassifyViewHolder, position: Int) {
        holder.itemTextView.text = "Model y $position"
    }

    class ClassifyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTextView: TextView = itemView.findViewById(R.id.tv_item_text)
    }
}