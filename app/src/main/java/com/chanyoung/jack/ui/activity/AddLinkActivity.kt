package com.chanyoung.jack.ui.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.chanyoung.jack.R
import com.chanyoung.jack.application.ErrorMessages
import com.chanyoung.jack.application.WebUrlUtil
import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.model.LinkGroup
import com.chanyoung.jack.databinding.ActivityAddLinkBinding
import com.chanyoung.jack.ui.activity.base.JMainBasicActivity
import com.chanyoung.jack.ui.adapter.recycler.AddLinkGroupItemAdapter
import com.chanyoung.jack.ui.component.dialog.CreateGroupDialog
import com.chanyoung.jack.ui.viewmodel.AddLinkViewModel
import com.chanyoung.jack.ui.viewmodel.networking.WebScraperViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddLinkActivity : JMainBasicActivity<ActivityAddLinkBinding>() {

    private val addLinkViewModel: AddLinkViewModel by viewModels()
    private val webScrapperViewModel: WebScraperViewModel by viewModels()

    private val createGroupDialog: CreateGroupDialog by lazy { CreateGroupDialog(::createGroup) }

    private lateinit var adapter: AddLinkGroupItemAdapter

    private lateinit var clipData: String

    private var groupId: Int? = null

    private var imagePath: String? = null


    override fun viewBindingInflate(inflater: LayoutInflater): ActivityAddLinkBinding =
        ActivityAddLinkBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddLinkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        setCreateGroupBottomSheet()

        createLink()

        setUrlInput()

        getClipData()

    }

    private fun initRecyclerView() {
        adapter = AddLinkGroupItemAdapter { groupId -> addLinkViewModel.onGroupItemSelected(groupId) }

        binding.addLinkGroupRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.addLinkGroupRv.adapter = adapter

        addLinkViewModel.setGroupList()

        addLinkViewModel.linkGroups.observe(this) { linkGroups -> adapter.setList(linkGroups) }

        addLinkViewModel.selectedGroupId.observe(this) { selectedGroupId ->
            adapter.updateSelectedGroupId(selectedGroupId)
            groupId = selectedGroupId
        }
    }

    private fun setCreateGroupBottomSheet() {
        binding.addLinkGroupBtn.setOnClickListener { createGroupDialog.show(supportFragmentManager, "CreateGroupFragment") }
    }

    private fun createGroup(groupName: String) {
        val linkGroup = LinkGroup(name =  groupName)
        addLinkViewModel.insertGroup(linkGroup)
    }

    private fun setScrapper(url: String) {
        webScrapperViewModel.scrapUrl(url)

        webScrapperViewModel.title.observe(this) { title ->
            title?.let {
                binding.addLinkTitleInput.setText(it)
            }
        }

        webScrapperViewModel.imageUrl.observe(this) {
            imagePath = if (it.isNotEmpty()) {
                Glide.with(this)
                    .load(it)
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .encodeQuality(100)
                    .error(R.drawable.link_place_holder)
                    .into(binding.addLinkThumbnail)

                it
            } else {
                binding.addLinkThumbnail.setImageResource(R.drawable.link_place_holder)
                ""
            }
        }
    }

    private fun createLink() {
        binding.addLinkSave.setOnClickListener {
            val title = binding.addLinkTitleInput.text.toString()
            val link = binding.addLinkUrlInput.text.toString()
            val memo = binding.addLinkMemoInput.text.toString()

            if(groupId == null) {
                Snackbar.make(binding.root, ErrorMessages.GROUP_EMPTY, Snackbar.LENGTH_SHORT).show()
            } else if (title.isEmpty()) {
                binding.addLinkTitleInput.error = ErrorMessages.TITLE_EMPTY
            } else if (link.isEmpty()) {
                binding.addLinkUrlInput.error = ErrorMessages.URL_EMPTY
            } else if(!WebUrlUtil.checkUrlPrefix(link)) {
                binding.addLinkUrlInput.error = ErrorMessages.INVALID_URL
            } else {
                addLinkViewModel.insertLink(Link(lid = 0, title = title, url = link, memo = memo, image_path = imagePath, groupId = groupId))
                finish()
            }
        }
    }

    private fun setUrlInput() {
        binding.addLinkUrlInput.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    val text = binding.addLinkUrlInput.text.toString().trim()
                    setScrapper(text)
                }
            }

        binding.addLinkUrlInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { webScrapperViewModel.resetLiveData() }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun getClipData() {
        binding.addLinkClipboard.setOnClickListener {
            addLinkViewModel.handlerPrimaryClip(this, 100)

            addLinkViewModel.clipData.observe(this) {newData ->
                if(newData != binding.addLinkUrlInput.text.toString() && newData != null) {
                    binding.addLinkUrlInput.setText(newData)
                    setScrapper(newData)
                    clipData = newData
                } else if(newData == null) {
                    Snackbar.make(binding.root, "Empty Clipboard", Snackbar.LENGTH_SHORT).show()
                }

            }
        }

    }
}