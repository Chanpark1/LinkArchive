package com.chanyoung.jack.ui.fragment

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chanyoung.jack.R
import com.chanyoung.jack.databinding.FragmentSearchBinding
import com.chanyoung.jack.ui.activity.LinkDetailActivity
import com.chanyoung.jack.ui.adapter.recycler.AllLinkListAdapter
import com.chanyoung.jack.ui.fragment.basic.JBasicFragment
import com.chanyoung.jack.ui.viewmodel.paging.LinkPagingViewModel
import com.chanyoung.jack.ui.viewmodel.paging.LinkSearchPagingViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment @Inject constructor() :
    JBasicFragment<FragmentSearchBinding>() {

    private val linkPagingViewModel : LinkPagingViewModel by activityViewModels()
    private val searchPagingViewModel : LinkSearchPagingViewModel by activityViewModels()

    private lateinit var adapter : AllLinkListAdapter
    override fun layoutId(): Int = R.layout.fragment_search
    override fun onCreateView() {

        initRecyclerView()

        setupObserver()

        setupSearch()
    }

    override fun initRecyclerView() {
        adapter = AllLinkListAdapter { linkId -> onSelectItem(linkId) }

        binding.fragSearchRv.layoutManager = LinearLayoutManager(requireContext())
        binding.fragSearchRv.adapter = adapter
    }

    private fun setupObserver() {
        linkPagingViewModel.items.observe(viewLifecycleOwner) {links ->
            adapter.submitData(viewLifecycleOwner.lifecycle, links)
        }

        searchPagingViewModel.items.observe(viewLifecycleOwner) {links ->
            adapter.submitData(viewLifecycleOwner.lifecycle, links)
        }
    }

    private fun setupSearch() {
        binding.fragSearchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchPagingViewModel.updateQueryData(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun onSelectItem(lid : Int) {
        val intent = Intent(requireContext(), LinkDetailActivity::class.java)
        intent.putExtra("lid", lid)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        linkPagingViewModel.refreshData()
    }

}