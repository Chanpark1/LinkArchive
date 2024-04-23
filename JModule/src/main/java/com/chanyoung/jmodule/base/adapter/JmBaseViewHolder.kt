package com.chanyoung.jmodule.base.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class JmBaseViewHolder<VB : ViewDataBinding>(protected val binding:VB) : RecyclerView.ViewHolder(binding.root){
}