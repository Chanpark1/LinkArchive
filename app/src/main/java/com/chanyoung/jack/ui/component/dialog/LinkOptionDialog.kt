package com.chanyoung.jack.ui.component.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chanyoung.jack.databinding.DialogLinkOptionBinding
import com.chanyoung.jack.ui.component.dialog.basic.JBasicBottomSheetDialog

class LinkOptionDialog(
    private val onEdit : () -> Unit,
    private val onDelete : () -> Unit,
    private val onRelocate : () -> Unit
) : JBasicBottomSheetDialog<DialogLinkOptionBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogLinkOptionBinding {
        return DialogLinkOptionBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
    }

    private fun setupListeners() {
        binding.dialogOptionEdit.setOnClickListener {
            onEdit.invoke()
            dismiss()
        }
        binding.dialogOptionDelete.setOnClickListener {
            onDelete.invoke()
            dismiss()
        }
        binding.dialogOptionRelocate.setOnClickListener {
            onRelocate.invoke()
            dismiss()
        }
        binding.dialogOptionCancel.setOnClickListener {
            dismiss()
        }
    }
}