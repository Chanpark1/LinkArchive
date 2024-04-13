package com.chanyoung.jack.ui.fragment

import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.chanyoung.jack.R
import com.chanyoung.jack.databinding.FragmentLinkDetailBinding
import com.chanyoung.jack.ui.fragment.basic.JBasicFragment
import com.chanyoung.jack.ui.viewmodel.LinkDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LinkDetailFragment @Inject constructor(): JBasicFragment<FragmentLinkDetailBinding>() {

    private val viewModel : LinkDetailViewModel by activityViewModels()

    override fun layoutId(): Int = R.layout.fragment_link_detail


    override fun onCreateView() {

        viewModel.getLinkInfo(getLinkIdFromArgs())

        setUpBinding()

        setupLinkNavigation()

    }

    private fun setUpBinding() {
        binding.apply {
            viewModel.link.observe(viewLifecycleOwner) {link ->
                loadThumbnail(fragLinkDetailThumbnail, link.image_path)
                fragLinkDetailMemo.text = link.memo
                fragLinkDetailUrl.text = link.url
                fragLinkDetailTitle.text = link.title
            }
        }
    }

    private fun loadThumbnail(imageView: ImageView, url : String?) {
        Glide.with(imageView.context)
            .load(url)
            .error(R.drawable.link_place_holder)
            .centerCrop()
            .into(imageView)
    }

    private fun getLinkIdFromArgs(): Int {
        return LinkDetailFragmentArgs.fromBundle(requireArguments()).lid

    }

    private fun navigateToLink(url : String?) {
        url?.let {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context?.startActivity(intent)
        }
    }

    private fun setupLinkNavigation() {
        binding.fragLinkDetailCv.setOnClickListener {
            viewModel.link.value?.let {
                navigateToLink(it.url)
            }
        }
    }





}