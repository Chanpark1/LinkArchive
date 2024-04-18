package com.chanyoung.jack.ui.component.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chanyoung.jack.databinding.DialogGroupOptionBinding
import com.chanyoung.jack.ui.component.dialog.basic.JBasicBottomSheetDialog

class GroupOptionDialog(
    private val onDelete : () -> Unit,
    private val onEdit : () -> Unit
) : JBasicBottomSheetDialog<DialogGroupOptionBinding>() {


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogGroupOptionBinding = DialogGroupOptionBinding.inflate(layoutInflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
    }

    private fun setupListeners() {
        binding.dialogOptionDelete.setOnClickListener {
            onDelete()
            dismiss()
        }

        binding.dialogOptionEdit.setOnClickListener {
            onEdit.invoke()
            dismiss()
        }

        binding.dialogOptionCancel.setOnClickListener {
            dismiss()
        }
    }
}