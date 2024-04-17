package com.chanyoung.jack.ui.component.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.chanyoung.jack.application.ErrorMessages
import com.chanyoung.jack.databinding.DialogCreateGroupBinding
import com.chanyoung.jack.ui.component.dialog.basic.JBasicBottomSheetDialog
import com.chanyoung.jack.ui.viewmodel.LinkViewModel

class CreateGroupDialog(
    private val onSave: (String) -> Unit
) : JBasicBottomSheetDialog<DialogCreateGroupBinding>() {

    private val linkViewModel: LinkViewModel by activityViewModels()
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogCreateGroupBinding {
        return DialogCreateGroupBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.dialogCreateBtn.setOnClickListener {
            if(binding.dialogCreateTitleInput.text.toString() != "") {
                onSave(binding.dialogCreateTitleInput.text.toString())
            } else {
                binding.dialogCreateTitleInput.error = ErrorMessages.TITLE_EMPTY
            }
        }

        linkViewModel.insertGroupResult.observe(viewLifecycleOwner) { result ->
            if(result) {
                dismiss()
            } else {
                binding.dialogCreateTitleInput.error = ErrorMessages.GROUP_EXISTS
            }
        }
    }
}