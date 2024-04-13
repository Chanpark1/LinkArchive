package com.chanyoung.jack.ui.fragment

import android.content.Intent
import android.net.Uri
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.chanyoung.jack.R
import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.databinding.FragmentLinkDetailBinding
import com.chanyoung.jack.ui.fragment.basic.JBasicFragment
import com.chanyoung.jack.ui.viewmodel.LinkDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LinkDetailFragment @Inject constructor(): JBasicFragment<FragmentLinkDetailBinding>() {

    private val viewModel : LinkDetailViewModel by activityViewModels()

    private lateinit var _link : Link

    override fun layoutId(): Int = R.layout.fragment_link_detail


    override fun onCreateView() {

        viewModel.getLinkInfo(getArgs())

        setData()

        navigateToLink()

    }

    private fun setData() {
        viewModel.link.observe(viewLifecycleOwner) { link ->
            if(link != null) {
                _link = link
                Glide.with(requireContext())
                    .load(link.image_path)
                    .error(R.drawable.link_place_holder)
                    .centerCrop()
                    .into(binding.fragLinkDetailThumbnail)

                binding.fragLinkDetailMemo.text = link.memo
                binding.fragLinkDetailUrl.text = link.url
                binding.fragLinkDetailTitle.text = link.title


            }
        }
    }

    private fun getArgs(): Int {
        val safeArgs = LinkDetailFragmentArgs.fromBundle(requireArguments())

        return safeArgs.lid
    }

    private fun navigateToLink() {
        binding.fragLinkDetailCv.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(_link.url))
            context?.startActivity(intent)
        }

    }





}