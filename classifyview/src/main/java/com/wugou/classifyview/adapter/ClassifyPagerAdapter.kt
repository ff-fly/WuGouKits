package com.wugou.classifyview.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wugou.classifyview.ContentViewListener
import com.wugou.classifyview.R


class ClassifyPagerAdapter : RecyclerView.Adapter<ClassifyPagerAdapter.ClassifyViewHolder>() {
    companion object {
        private const val TAG = "ClassifyPagerAdapter"
    }

    var listener: ContentViewListener? = null
        set(value) {
            Log.i(TAG, "set listener:$value")
            field = value
        }

    var itemSize = 0
        set(value) {
            Log.i(TAG, "setSize:$value")
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassifyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_content_item, parent, false)
        listener?.getContentViewResId()?.let { it ->
            LayoutInflater.from(parent.context).inflate(it, itemView as ViewGroup?)
        }
        return ClassifyViewHolder(itemView, listener)
    }

    override fun getItemCount(): Int {
        return itemSize
    }

    override fun onBindViewHolder(holder: ClassifyViewHolder, position: Int) {
        holder.bindData(position)
    }

    class ClassifyViewHolder(itemView: View, private val listener: ContentViewListener?) : RecyclerView.ViewHolder(itemView) {
        private var contentView: View? = null

        init {
            val cc = (itemView as ViewGroup).childCount
            if (cc > 0) {
                contentView = itemView.getChildAt(0)
            }
        }

        fun bindData(position: Int) {
            listener?.bindData(position, contentView)
        }
    }
}