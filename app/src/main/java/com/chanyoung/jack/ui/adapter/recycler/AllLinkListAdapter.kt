package com.chanyoung.jack.ui.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.chanyoung.jack.R
import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.databinding.ItemAllLinkBinding
import com.chanyoung.jmodule.adapter.JmBaseViewHolder

class AllLinkListAdapter(
    private val onSelect : (Int) -> Unit
) : PagingDataAdapter<Link, LinkListItemViewHolder>(LinkDiffCallback()) {

    override fun onBindViewHolder(holder: LinkListItemViewHolder, position: Int) {
        val link = getItem(position)
        link?.let {
            holder.setItem(link, onSelect)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkListItemViewHolder {
        val binding = ItemAllLinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LinkListItemViewHolder(binding)
    }
}

class LinkListItemViewHolder(binding : ItemAllLinkBinding) : JmBaseViewHolder<ItemAllLinkBinding>(binding) {

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view : ImageView, url : String?) {
            url?.let {
                Glide.with(view.context)
                    .load(it)
                    .error(R.drawable.link_place_holder)
                    .centerCrop()
                    .into(view)
            }
        }
    }

    fun setItem(link : Link, onSelect: (Int) -> Unit) {

        binding.link = link

        binding.root.setOnClickListener {
            onSelect(link.lid)
        }
    }

}

class LinkDiffCallback : DiffUtil.ItemCallback<Link>() {
    override fun areItemsTheSame(oldItem: Link, newItem: Link): Boolean {
        return oldItem.lid == newItem.lid
    }

    override fun areContentsTheSame(oldItem: Link, newItem: Link): Boolean {
        return oldItem == newItem
    }
}