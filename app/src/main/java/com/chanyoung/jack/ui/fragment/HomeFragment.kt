package com.chanyoung.jack.ui.fragment

import androidx.navigation.fragment.findNavController
import com.chanyoung.jack.R
import com.chanyoung.jack.databinding.FragmentHomeBinding
import com.chanyoung.jack.ui.fragment.basic.JBasicFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor() :
    JBasicFragment<FragmentHomeBinding>() {

    override fun onCreateView() {

        initRecyclerView()

        setAddLinkButton()

    }

    private fun setAddLinkButton() {
        val addLinkButton = binding.fragHomeAddBtn
        addLinkButton?.setOnClickListener {
            navigateToAddLinkFragment()
        }
    }

    private fun navigateToAddLinkFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToAddLinkFragment()
        findNavController().navigate(action)
    }

    override fun layoutId(): Int {
        return R.layout.fragment_home
    }

}