package com.chanyoung.jack.ui.component.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chanyoung.jack.databinding.FragmentPasteLinkDialogBinding
import com.chanyoung.jack.ui.component.dialog.basic.JBasicBottomSheetDialog

class PasteLinkDialog(
    val onPasteLink : (String) -> Unit
) : JBasicBottomSheetDialog<FragmentPasteLinkDialogBinding>() {

    private lateinit var url: String

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPasteLinkDialogBinding {
        return FragmentPasteLinkDialogBinding.inflate(inflater,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkClipboard()
    }

    private fun checkClipboard() {
        binding.dialogCpLink.text = url
        binding.dialogCpBtn.setOnClickListener {
            onPasteLink(binding.dialogCpLink.text.toString())
            dismiss()
        }
    }

    fun updateUrl(url : String) {
        this.url = url
    }
}