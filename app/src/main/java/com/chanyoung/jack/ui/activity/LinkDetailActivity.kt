package com.chanyoung.jack.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.chanyoung.jack.R
import com.chanyoung.jack.databinding.ActivityLinkDetailBinding
import com.chanyoung.jack.ui.activity.base.JMainBasicActivity
import com.chanyoung.jack.ui.viewmodel.LinkDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LinkDetailActivity : JMainBasicActivity<ActivityLinkDetailBinding>() {

    private val viewModel : LinkDetailViewModel by viewModels()

    override fun viewBindingInflate(inflater: LayoutInflater): ActivityLinkDetailBinding = ActivityLinkDetailBinding.inflate(layoutInflater)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLinkDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getLinkInfo(getLinkId())

        setupBinding()

        setupLinkNavigation()
    }


    private fun getLinkId() : Int {
        return intent.getIntExtra("lid",0)
    }

    private fun navigateToLink(url : String?) {
        url?.let {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    private fun setupLinkNavigation() {
        binding.linkDetailCv.setOnClickListener {
            viewModel.link.value?.let {
                navigateToLink(it.url)
            }
        }
    }

    private fun setupBinding() {
        binding.apply {
            viewModel.link.observe(this@LinkDetailActivity) {
                loadThumbnail(linkDetailThumbnail, it.image_path)
                linkDetailMemo.text = it.memo
                linkDetailUrl.text = it.url
                linkDetailTitle.text = it.title
            }
        }
    }

    private fun loadThumbnail(view : ImageView, url : String?) {
        Glide.with(view.context)
            .load(url)
            .error(R.drawable.link_place_holder)
            .centerCrop()
            .into(view)
    }


}

