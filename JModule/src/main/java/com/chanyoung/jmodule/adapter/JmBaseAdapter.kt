package com.chanyoung.jmodule.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView

abstract class JmBaseAdapter<R, VH : RecyclerView.ViewHolder>(
    protected var itemList: List<R> = listOf()
) : RecyclerView.Adapter<VH>() {

    fun setList(newList: List<R>) {
        this.itemList = newList

        notifyDataSetChanged()
    }

    final override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, itemList[position], position)
    }

    abstract fun onBindViewHolder(holder : VH, item : R, position: Int)

    final override fun getItemCount(): Int = itemList.size
}