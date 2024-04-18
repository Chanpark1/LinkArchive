package com.chanyoung.jack.ui.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.chanyoung.jack.data.model.LinkGroup
import com.chanyoung.jack.databinding.ItemLinkGroupListBinding
import com.chanyoung.jmodule.adapter.JmBaseViewHolder

class AllGroupListAdapter(
    private val onSelectItem: (Int) -> Unit,
    private val onSelectMenu: (Int) -> Unit
) : PagingDataAdapter<LinkGroup, LinkGroupListViewHolder>(GroupDiffCallback()) {

    override fun onBindViewHolder(holder: LinkGroupListViewHolder, position: Int) {
        val linkGroup = getItem(position)

        linkGroup?.let {
            holder.setItem(linkGroup, onSelectItem, onSelectMenu)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkGroupListViewHolder {
        val binding =
            ItemLinkGroupListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LinkGroupListViewHolder(binding)
    }

}

class LinkGroupListViewHolder(binding: ItemLinkGroupListBinding) :
    JmBaseViewHolder<ItemLinkGroupListBinding>(binding) {

    fun setItem(linkGroup: LinkGroup, onSelectItem: (Int) -> Unit, onSelectMenu: (Int) -> Unit) {
        binding.linkGroup = linkGroup

        binding.root.setOnClickListener {
            onSelectItem(linkGroup.gid)
        }

        binding.groupListMenu.setOnClickListener {
            onSelectMenu(linkGroup.gid)
        }
    }

}

class GroupDiffCallback : DiffUtil.ItemCallback<LinkGroup>() {
    override fun areItemsTheSame(oldItem: LinkGroup, newItem: LinkGroup): Boolean {
        return oldItem.gid == newItem.gid
    }

    override fun areContentsTheSame(oldItem: LinkGroup, newItem: LinkGroup): Boolean {
        return oldItem == newItem
    }
}