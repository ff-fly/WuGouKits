package com.wugou.classifyview.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.RECTANGLE
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wugou.classifyview.R
import com.wugou.classifyview.entity.ClassifyItem
import com.wugou.classifyview.entity.UiConfigs


class ClassifyRecyclerAdapter(private val context: Context, private val uiConfigs: UiConfigs) :
        RecyclerView.Adapter<ClassifyRecyclerAdapter.ClassifyViewHolder>(), View.OnClickListener {
    companion object {
        const val TAG = "ClassifyRecyclerAdapter"
    }

    private var dataList = ArrayList<ClassifyItem>()
    private var itemClickListener: ListItemClickListener? = null
    private var selectedPos = 0
    private var selectedDrawable: Drawable? = null

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

    fun setSelectedDrawable(drawable: Drawable?) {
        selectedDrawable = drawable
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassifyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.layout_list_item, parent, false)

        val itemParams = itemView.layoutParams
        itemParams.height = uiConfigs.heightPix
        itemView.layoutParams = itemParams
        val holder = ClassifyViewHolder(itemView)

        val iconParams = holder.selectedIcon.layoutParams as ViewGroup.MarginLayoutParams
        iconParams.width = uiConfigs.selectedIconSizePix
        iconParams.height = uiConfigs.selectedIconSizePix
        iconParams.leftMargin = uiConfigs.selectedIconMarginLeftPix
        iconParams.rightMargin = uiConfigs.selectedIconMarginRightPix
        return holder
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
                holder.itemView.setBackgroundColor(uiConfigs.selectedBgColor)
                holder.itemTextView.setTextColor(uiConfigs.selectedTextColor)
                holder.itemTextView.textSize = uiConfigs.selectedTextPix.toFloat()
                holder.selectedIcon.visibility = View.VISIBLE
                holder.selectedIcon.setImageDrawable(selectedDrawable)
            }
            selectedPos - 1 -> {
                setNormalItemStyle(position, uiConfigs.selectedCorner.toInt(), holder)
            }
            selectedPos + 1 -> {
                setNormalItemStyle(position, uiConfigs.selectedCorner.toInt(), holder)
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
            drawable.setColor(uiConfigs.normalBgColor)
            drawable.cornerRadii = FloatArray(8).apply {
                set(first, uiConfigs.selectedCorner)
                set(second, uiConfigs.selectedCorner)
            }
            holder.itemView.background = drawable
        } ?: holder.itemView.setBackgroundColor(uiConfigs.normalBgColor)

        holder.itemTextView.setTextColor(uiConfigs.normalTextColor)
        holder.itemTextView.textSize = uiConfigs.normalTextPix.toFloat()
        holder.selectedIcon.visibility = View.INVISIBLE
    }

    class ClassifyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTextView: TextView = itemView.findViewById(R.id.tv_item_text)
        val selectedIcon: ImageView = itemView.findViewById(R.id.iv_selected)
    }
}