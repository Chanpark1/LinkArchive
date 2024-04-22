package com.chanyoung.jack.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chanyoung.jack.databinding.ActivityGroupDetailBinding
import com.chanyoung.jack.ui.activity.base.JMainBasicActivity
import com.chanyoung.jack.ui.adapter.recycler.AllLinkListAdapter
import com.chanyoung.jack.ui.viewmodel.GroupViewModel
import com.chanyoung.jack.ui.viewmodel.paging.LinkInGroupPagingViewModel
import com.chanyoung.jack.ui.viewmodel.paging.LinkSearchInGroupPagingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupDetailActivity : JMainBasicActivity<ActivityGroupDetailBinding>() {
     companion object {
        const val GROUP_ID = "gid"
    }

    private val groupViewModel: GroupViewModel by viewModels()
    private val linkPagingViewModel : LinkInGroupPagingViewModel by viewModels()
    private val searchPagingViewModel : LinkSearchInGroupPagingViewModel by viewModels()

    private lateinit var adapter : AllLinkListAdapter
    override fun viewBindingInflate(inflater: LayoutInflater): ActivityGroupDetailBinding = ActivityGroupDetailBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObserver()

        initRecyclerView()

        setupSearch()
    }

    private fun getGroupId() : Int {
        return intent.getIntExtra(GROUP_ID, 0)
    }

    private fun setupBinding() {
        if(getGroupId() != 0) {
            groupViewModel.getGroup(getGroupId())
            linkPagingViewModel.initGroupId(getGroupId())
            searchPagingViewModel.initGroupId(getGroupId())
        }
    }

    private fun setupObserver() {

        groupViewModel.linkGroup.observe(this) {linkGroup ->
            binding.linkGroup = linkGroup
        }

        linkPagingViewModel.items.observe(this) {links ->
            adapter.submitData(this.lifecycle, links)
        }

        searchPagingViewModel.items.observe(this) {links ->
            adapter.submitData(this.lifecycle, links)
        }
    }

    private fun initRecyclerView() {
        adapter = AllLinkListAdapter { linkId -> onSelectItem(linkId) }

        binding.groupDetailRv.layoutManager = LinearLayoutManager(this)
        binding.groupDetailRv.adapter = adapter
    }

    private fun onSelectItem(lid : Int) {
        val intent = Intent(this, LinkDetailActivity::class.java)
        intent.putExtra("lid", lid)
        startActivity(intent)
    }

    private fun setupSearch() {
        binding.groupDetailSearchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchPagingViewModel.updateQueryData(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

    }

    override fun onResume() {
        super.onResume()
        setupBinding()
        linkPagingViewModel.refreshData()
    }
}