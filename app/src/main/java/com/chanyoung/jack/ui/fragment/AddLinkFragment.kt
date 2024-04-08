package com.chanyoung.jack.ui.fragment

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.chanyoung.jack.R
import com.chanyoung.jack.application.WebUrlUtil
import com.chanyoung.jack.data.model.LinkGroup
import com.chanyoung.jack.databinding.FragmentAddLinkBinding
import com.chanyoung.jack.ui.adapter.recycler.AddLinkGroupItemAdapter
import com.chanyoung.jack.ui.component.clipboard.JClipboardManager
import com.chanyoung.jack.ui.component.dialog.CreateGroupDialog
import com.chanyoung.jack.ui.component.dialog.PasteLinkDialog
import com.chanyoung.jack.ui.fragment.basic.JBasicFragment
import com.chanyoung.jack.ui.viewmodel.fragment.AddLinkViewModel
import com.chanyoung.jack.ui.viewmodel.networking.WebScraperViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddLinkFragment @Inject constructor() : JBasicFragment<FragmentAddLinkBinding>() {

    private val addLinViewModel: AddLinkViewModel by activityViewModels()
    private val webScrapperViewModel: WebScraperViewModel by activityViewModels()

    private val createGroupDialog: CreateGroupDialog by lazy { CreateGroupDialog(::createGroup) }
    private val clipboardDialog: PasteLinkDialog by lazy { PasteLinkDialog(::setClipData) }

    override fun layoutId(): Int { return R.layout.fragment_add_link }

    private lateinit var adapter: AddLinkGroupItemAdapter

    private lateinit var clipData: String


    override fun onCreateView() {

        initRecyclerView()

        setCreateGroupBottomSheet()

        setUrlInput()

    }

    override fun initRecyclerView() {
        adapter = AddLinkGroupItemAdapter { groupId -> addLinViewModel.onGroupItemSelected(groupId) }

        binding.fragAddLinkGroupRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.fragAddLinkGroupRv.adapter = adapter

        addLinViewModel.setGroupList()

        addLinViewModel.linkGroups.observe(viewLifecycleOwner) { linkGroups -> adapter.setList(linkGroups) }

        addLinViewModel.selectedGroupId.observe(viewLifecycleOwner) { selectedGroupId -> adapter.updateSelectedGroupId(selectedGroupId) }

    }

    private fun setCreateGroupBottomSheet() {
        binding.fragAddLinkGroupBtn.setOnClickListener {
            createGroupDialog.show(childFragmentManager, "CreateDialog")
        }
    }

    private fun createGroup(groupName: String) {
        val linkGroup = LinkGroup(0, groupName)
        addLinViewModel.insertGroup(linkGroup)
    }

//    private fun createLink() {
//        binding.fragAddLinkSave.setOnClickListener {
//            var title = binding.fragAddLinkTitleInput.text.toString()
//            var link = binding.fragAddLinkUrlInput
//            var memo = binding.fragAddLinkMemoInput.text.toString()
//
//        }
//    }

    private fun setClipData(url: String) {

        clipData = url
        binding.fragAddLinkUrlInput.setText(url)
        setScrapper(WebUrlUtil.addHttpPrefix(url))
    }

    private fun setScrapper(url : String) {
        webScrapperViewModel.scrapUrl(WebUrlUtil.addHttpPrefix(url))

        webScrapperViewModel.title.observe(viewLifecycleOwner) {title ->
            title?.let {
                binding.fragAddLinkTitleInput.setText(it)
            }
        }

        webScrapperViewModel.imageUrl.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Glide.with(requireActivity())
                    .load(it)
                    .into(binding.fragAddLinkThumbnail)
            } else {
                binding.fragAddLinkThumbnail.setImageResource(R.drawable.app_logo_blue)
            }
        }

    }

    private fun setUrlInput() {
        binding.fragAddLinkUrlInput.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                val text = binding.fragAddLinkUrlInput.text.toString().trim()

                binding.fragAddLinkTitleInput.setText("")
                setScrapper(WebUrlUtil.addHttpPrefix(text))

            }
        }
    }



    override fun onResume() {
        super.onResume()
        if(binding.fragAddLinkUrlInput.text.isNotEmpty()) {
            setScrapper(WebUrlUtil.addHttpPrefix(binding.fragAddLinkUrlInput.text.toString().trim()))
        }
        val clipboardManager = JClipboardManager.getClipboardManager(requireContext())

        if (clipboardManager.hasPrimaryClip() ) {
            val clip = clipboardManager.primaryClip

            val item = clip!!.getItemAt(0)
            val text = item.text.toString()
            if (binding.fragAddLinkUrlInput.text.toString() != text && WebUrlUtil.isValidUrl(text)) {
                clipboardDialog.updateUrl(text)
                clipboardDialog.show(childFragmentManager,"TAG")
                clipData = text
            }
        }
    }

}

