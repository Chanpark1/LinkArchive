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
import com.chanyoung.jack.ui.component.dialog.LinkOptionDialog
import com.chanyoung.jack.ui.component.dialog.RelocateLinkDialog
import com.chanyoung.jack.ui.viewmodel.LinkViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LinkDetailActivity : JMainBasicActivity<ActivityLinkDetailBinding>() {

    private val linkViewModel : LinkViewModel by viewModels()

    private val linkOptionDialog : LinkOptionDialog by lazy { LinkOptionDialog(::setEditOption, ::setDeleteOption, ::setRelocateOption) }
    private val relocateLinkDialog : RelocateLinkDialog by lazy { RelocateLinkDialog() }

    override fun viewBindingInflate(inflater: LayoutInflater): ActivityLinkDetailBinding = ActivityLinkDetailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLinkDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        linkViewModel.getLink(getLinkId())

        setupBinding()

        setupLinkNavigation()

        setMenuButton()
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
            linkViewModel.link.value?.let {
                navigateToLink(it.url)
            }
        }
    }

    private fun setupBinding() {
        binding.apply {
            linkViewModel.link.observe(this@LinkDetailActivity) {
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

    private fun setMenuButton() {
        binding.linkDetailMenu.setOnClickListener {
            linkOptionDialog.show(supportFragmentManager,"LinkOption")
        }
    }


    private fun setEditOption() {
        val intent = Intent(this, EditLinkActivity::class.java)
        intent.putExtra("lid",getLinkId())
        startActivity(intent)
        finish()
    }

    private fun setDeleteOption() {
        linkViewModel.deleteLinkById(getLinkId())
        finish()
    }

    private fun setRelocateOption() {
        relocateLinkDialog.updateLinkId(getLinkId())
        relocateLinkDialog.show(supportFragmentManager,"Relocate Dialog")
    }

}

