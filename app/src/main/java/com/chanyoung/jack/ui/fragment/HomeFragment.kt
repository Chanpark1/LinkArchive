package com.chanyoung.jack.ui.fragment

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chanyoung.jack.R
import com.chanyoung.jack.databinding.FragmentHomeBinding
import com.chanyoung.jack.ui.activity.LinkDetailActivity
import com.chanyoung.jack.ui.adapter.recycler.AllLinkListAdapter
import com.chanyoung.jack.ui.fragment.basic.JBasicFragment
import com.chanyoung.jack.ui.viewmodel.paging.LinkPagingViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class HomeFragment @Inject constructor() :
    JBasicFragment<FragmentHomeBinding>() {

    private val viewModel : LinkPagingViewModel by activityViewModels()

    private lateinit var adapter : AllLinkListAdapter
    override fun layoutId(): Int { return R.layout.fragment_home }
    override fun onCreateView() {

        initRecyclerView()
    }

    override fun initRecyclerView() {
        adapter = AllLinkListAdapter { linkId -> onSelectItem(linkId) }

        binding.fragHomeRv.layoutManager = LinearLayoutManager(requireContext())
        binding.fragHomeRv.adapter = adapter

        viewModel.links.observe(viewLifecycleOwner) {links ->
            adapter.submitData(viewLifecycleOwner.lifecycle, links)
        }
    }

    private fun onSelectItem(lid : Int) {
        val intent = Intent(requireContext(), LinkDetailActivity::class.java)
        intent.putExtra("lid", lid)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshData()
    }
}