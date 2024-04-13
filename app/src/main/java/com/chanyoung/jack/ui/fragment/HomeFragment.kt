package com.chanyoung.jack.ui.fragment

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chanyoung.jack.R
import com.chanyoung.jack.databinding.FragmentHomeBinding
import com.chanyoung.jack.ui.adapter.recycler.AllLinkListAdapter
import com.chanyoung.jack.ui.fragment.basic.JBasicFragment
import com.chanyoung.jack.ui.viewmodel.fragment.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor() :
    JBasicFragment<FragmentHomeBinding>() {

    private val viewModel : HomeFragmentViewModel by activityViewModels()

    private lateinit var adapter : AllLinkListAdapter
    override fun layoutId(): Int { return R.layout.fragment_home }
    override fun onCreateView() {

        initRecyclerView()

        setAddLinkButton()
    }

    private fun setAddLinkButton() {
        binding.fragHomeAddBtn.setOnClickListener { navigateToAddLinkFragment() }
    }

    private fun navigateToAddLinkFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToAddLinkFragment()
        findNavController().navigate(action)
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
        val action = HomeFragmentDirections.actionHomeFragmentToLinkDetailFragment(lid)
        findNavController().navigate(action)
    }
}