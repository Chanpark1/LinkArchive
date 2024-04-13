package com.chanyoung.jack.ui.fragment

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.chanyoung.jack.R
import com.chanyoung.jack.application.ErrorMessages
import com.chanyoung.jack.application.WebUrlUtil
import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.model.LinkGroup
import com.chanyoung.jack.databinding.FragmentAddLinkBinding
import com.chanyoung.jack.ui.adapter.recycler.AddLinkGroupItemAdapter
import com.chanyoung.jack.ui.component.clipboard.JClipboardManager
import com.chanyoung.jack.ui.component.dialog.CreateGroupDialog
import com.chanyoung.jack.ui.component.dialog.PasteLinkDialog
import com.chanyoung.jack.ui.fragment.basic.JBasicFragment
import com.chanyoung.jack.ui.viewmodel.fragment.AddLinkViewModel
import com.chanyoung.jack.ui.viewmodel.networking.WebScraperViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddLinkFragment @Inject constructor() : JBasicFragment<FragmentAddLinkBinding>() {

    private val addLinkViewModel: AddLinkViewModel by activityViewModels()
    private val webScrapperViewModel: WebScraperViewModel by activityViewModels()

    private val createGroupDialog: CreateGroupDialog by lazy { CreateGroupDialog(::createGroup) }
    private val clipboardDialog: PasteLinkDialog by lazy { PasteLinkDialog(::setClipData) }

    private lateinit var adapter: AddLinkGroupItemAdapter

    private lateinit var clipData: String

    private var groupId: Int? = null

    private var imagePath: String? = null
    override fun layoutId(): Int { return R.layout.fragment_add_link }
    override fun onCreateView() {

        initRecyclerView()

        setCreateGroupBottomSheet()

        setUrlInput()

        createLink()

    }

    override fun initRecyclerView() {
        adapter = AddLinkGroupItemAdapter { groupId -> addLinkViewModel.onGroupItemSelected(groupId) }

        binding.fragAddLinkGroupRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.fragAddLinkGroupRv.adapter = adapter

        addLinkViewModel.setGroupList()

        addLinkViewModel.linkGroups.observe(viewLifecycleOwner) { linkGroups -> adapter.setList(linkGroups) }

        addLinkViewModel.selectedGroupId.observe(viewLifecycleOwner) { selectedGroupId ->
            adapter.updateSelectedGroupId(selectedGroupId)
            groupId = selectedGroupId
        }
    }

    private fun setCreateGroupBottomSheet() {
        binding.fragAddLinkGroupBtn.setOnClickListener { createGroupDialog.show(childFragmentManager, "CreateDialog") }
    }

    private fun createGroup(groupName: String) {
        val linkGroup = LinkGroup(name =  groupName)
        addLinkViewModel.insertGroup(linkGroup)
    }

    private fun setClipData(url: String) {
        clipData = url
        binding.fragAddLinkUrlInput.setText(url)
        setScrapper(url)
    }

    private fun setScrapper(url: String) {
        webScrapperViewModel.scrapUrl(url)

        webScrapperViewModel.title.observe(viewLifecycleOwner) { title ->
            title?.let {
                binding.fragAddLinkTitleInput.setText(it)
            }
        }

        webScrapperViewModel.imageUrl.observe(viewLifecycleOwner) {
            imagePath = if (it.isNotEmpty()) {
                Glide.with(requireActivity())
                    .load(it)
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .encodeQuality(100)
                    .error(R.drawable.link_place_holder)
                    .into(binding.fragAddLinkThumbnail)

                it
            } else {
                binding.fragAddLinkThumbnail.setImageResource(R.drawable.link_place_holder)
                ""
            }
        }
    }

    private fun createLink() {
        binding.fragAddLinkSave.setOnClickListener {
            val title = binding.fragAddLinkTitleInput.text.toString()
            val link = binding.fragAddLinkUrlInput.text.toString()
            val memo = binding.fragAddLinkMemoInput.text.toString()

            if(groupId == null) {
                Snackbar.make(binding.root, ErrorMessages.GROUP_EMPTY, Snackbar.LENGTH_SHORT).show()
            } else if (title.isEmpty()) {
                binding.fragAddLinkTitleInput.error = ErrorMessages.TITLE_EMPTY
            } else if (link.isEmpty()) {
                binding.fragAddLinkUrlInput.error = ErrorMessages.URL_EMPTY
            } else if(!WebUrlUtil.checkUrlPrefix(link)) {
                binding.fragAddLinkUrlInput.error = ErrorMessages.INVALID_URL
            } else {
                addLinkViewModel.insertLink(Link(lid = 0, title = title, url = link, memo = memo, image_path = imagePath, groupId = groupId))
                navigateToHomeFragment()
            }
        }
    }

    private fun setUrlInput() {
        binding.fragAddLinkUrlInput.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    val text = binding.fragAddLinkUrlInput.text.toString().trim()
                    setScrapper(text)
                }
            }

        binding.fragAddLinkUrlInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { webScrapperViewModel.resetLiveData() }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
    private fun navigateToHomeFragment() {
        findNavController().popBackStack(R.id.homeFragment, false)
    }

    override fun onResume() {
        super.onResume()
        if (binding.fragAddLinkUrlInput.text.isNotEmpty() && WebUrlUtil.checkUrlPrefix(binding.fragAddLinkUrlInput.text.toString())) {
            setScrapper(binding.fragAddLinkUrlInput.text.toString().trim())
        }
        val clipboardManager = JClipboardManager.getClipboardManager(requireContext())

        if (clipboardManager.hasPrimaryClip()) {
            val clip = clipboardManager.primaryClip

            try {
                val item = clip!!.getItemAt(0)
                val text = item.text.toString()
                if (binding.fragAddLinkUrlInput.text.toString() != text && WebUrlUtil.checkUrlPrefix(text)) {
                    clipboardDialog.updateUrl(text)
                    clipboardDialog.show(childFragmentManager, "TAG")
                    clipData = text
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}