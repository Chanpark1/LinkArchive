package com.chanyoung.jack.ui.adapter.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chanyoung.jack.R
import com.chanyoung.jack.data.model.LinkGroup
import com.chanyoung.jack.databinding.ItemLinkgroupBinding
import com.chanyoung.jmodule.adapter.JmBaseAdapter
import com.chanyoung.jmodule.adapter.JmBaseViewHolder

class AddLinkGroupItemAdapter(
    private val selectOperation : (Int) -> Unit
    )
    : JmBaseAdapter<LinkGroup, LinkGroupItemViewHolder>() {


    private var selectedGroupId: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkGroupItemViewHolder {
        val binding = ItemLinkgroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LinkGroupItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LinkGroupItemViewHolder, item: LinkGroup, position: Int) {
        holder.setItem(item, selectOperation, selectedGroupId)
    }

    fun updateSelectedGroupId(groupId : Int) {
        selectedGroupId = groupId
        notifyDataSetChanged()
    }
}

class LinkGroupItemViewHolder(binding : ItemLinkgroupBinding) : JmBaseViewHolder<ItemLinkgroupBinding>(binding) {

    fun setItem(group : LinkGroup, selectOperation: (Int) -> Unit, selectedGroupId: Int) {
        binding.linkGroup = group
        binding.root.setOnClickListener{
            selectOperation(group.gid)
            updateBackground(selectedGroupId)
        }
        updateBackground(selectedGroupId)
    }


    private fun updateBackground(selectedGroupId: Int) {
        val backgroundColorResId = if (binding.linkGroup?.gid == selectedGroupId) R.drawable.link_group_item_selected else R.drawable.link_group_item_unselected
        binding.itemLinkGroupConst.setBackgroundResource(backgroundColorResId)
    }

}