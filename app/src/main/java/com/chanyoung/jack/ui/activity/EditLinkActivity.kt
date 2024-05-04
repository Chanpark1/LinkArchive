package com.chanyoung.jack.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.chanyoung.jack.R
import com.chanyoung.jack.application.ErrorMessages
import com.chanyoung.jack.application.WebUrlUtil
import com.chanyoung.jack.databinding.ActivityEditLinkBinding
import com.chanyoung.jack.ui.activity.base.JMainBasicActivity
import com.chanyoung.jack.ui.viewmodel.ClipBoardViewModel
import com.chanyoung.jack.ui.viewmodel.LinkViewModel
import com.chanyoung.jack.ui.viewmodel.networking.WebScraperViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditLinkActivity : JMainBasicActivity<ActivityEditLinkBinding>() {

    private val linkViewModel : LinkViewModel by viewModels()
    private val clipBoardViewModel: ClipBoardViewModel by viewModels()
    private val webScrapperViewModel : WebScraperViewModel by viewModels()

    private var clipData: String = ""
    private  var imagePath: String = ""

    override fun viewBindingInflate(inflater: LayoutInflater): ActivityEditLinkBinding = ActivityEditLinkBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditLinkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBinding()

        setupSubmit()

        setUrlInput()

        getClipData()

    }
    private fun getLinkId() : Int {
        return intent.getIntExtra("lid",0)
    }

    private fun setupBinding() {
        linkViewModel.getLink(getLinkId())

        binding.apply {
            linkViewModel.link.observe(this@EditLinkActivity) {link ->
                setupThumbnail(link.image_path)
                binding.editLinkTitleInput.setText(link.title)
                binding.editLinkUrlInput.setText(link.url)
                binding.editLinkMemoInput.setText(link.memo)

                imagePath = link.image_path.toString()
            }
        }

    }

    private fun setupThumbnail(image_path : String?) {
        Glide.with(this)
            .load(image_path)
            .error(R.drawable.link_place_holder)
            .centerCrop()
            .into(binding.editLinkThumbnail)
    }

    private fun setupSubmit() {
        binding.editLinkSave.setOnClickListener {
            val title = binding.editLinkTitleInput.text.toString()
            val url = binding.editLinkUrlInput.text.toString()
            val memo = binding.editLinkMemoInput.text.toString()

            if (title.isEmpty()) {
                binding.editLinkTitleInput.error = ErrorMessages.TITLE_EMPTY
            } else if (url.isEmpty()) {
                binding.editLinkUrlInput.error = ErrorMessages.URL_EMPTY
            } else if(!WebUrlUtil.checkUrlPrefix(url)) {
                binding.editLinkUrlInput.error = ErrorMessages.INVALID_URL
            } else {
                linkViewModel.editLink(getLinkId(), title, url, memo, imagePath)

                val intent = Intent(this, LinkDetailActivity::class.java)
                intent.putExtra("lid", getLinkId())
                startActivity(intent)
                finish()
            }

        }
    }
    private fun setScrapper(url: String) {
        webScrapperViewModel.scrapUrl(url)

        webScrapperViewModel.title.observe(this) { title ->
            title?.let {
                if(it.isNotBlank()) {
                    binding.editLinkTitleInput.setText(it)
                }
            }
        }

        webScrapperViewModel.imageUrl.observe(this) {
            imagePath = if (it.isNotEmpty()) {
                Glide.with(this)
                    .load(it)
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .encodeQuality(100)
                    .error(R.drawable.link_place_holder)
                    .into(binding.editLinkThumbnail)

                it
            } else {
                binding.editLinkThumbnail.setImageResource(R.drawable.link_place_holder)
                ""
            }
        }
    }

    private fun setUrlInput() {
        binding.editLinkUrlInput.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    val text = binding.editLinkUrlInput.text.toString().trim()
                    setScrapper(text)
                }
            }

        binding.editLinkUrlInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { webScrapperViewModel.resetLiveData() }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun getClipData() {
        binding.editLinkClipboard.setOnClickListener {
            clipBoardViewModel.handlerPrimaryClip(this, 100)

            clipBoardViewModel.clipData.observe(this) { newData ->
                if(newData != binding.editLinkUrlInput.text.toString() && newData != null) {
                    binding.editLinkUrlInput.setText(newData)
                    setScrapper(newData)
                    clipData = newData
                } else if(newData == null) {
                    Snackbar.make(binding.root, "Empty Clipboard", Snackbar.LENGTH_SHORT).show()
                }

            }
        }

    }

}