package com.chanyoung.jack.ui.component.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.chanyoung.jack.application.ErrorMessages
import com.chanyoung.jack.data.model.LinkGroup
import com.chanyoung.jack.databinding.DialogCreateGroupBinding
import com.chanyoung.jack.ui.component.dialog.basic.JBasicBottomSheetDialog
import com.chanyoung.jack.ui.viewmodel.GroupViewModel
import com.google.android.material.snackbar.Snackbar

class CreateGroupDialog : JBasicBottomSheetDialog<DialogCreateGroupBinding>() {

    private val groupViewModel: GroupViewModel by activityViewModels()
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogCreateGroupBinding {
        return DialogCreateGroupBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.dialogCreateTitleInput.error = null

        setupListener()

        setupObserver()

    }
    private fun setupListener() {
        binding.dialogCreateBtn.setOnClickListener {
            if(binding.dialogCreateTitleInput.text.toString() != "") {
                groupViewModel.checkDuplicateName(binding.dialogCreateTitleInput.text.toString())
                groupViewModel.insertGroup(LinkGroup(name = binding.dialogCreateTitleInput.text.toString()))
            } else {
                binding.dialogCreateTitleInput.error = ErrorMessages.TITLE_EMPTY
            }
        }
    }

    private fun setupObserver() {
        groupViewModel.checkDuplicate.observe(viewLifecycleOwner) {result ->
            if(result.isFailure) {
                binding.dialogCreateTitleInput.error = ErrorMessages.GROUP_EXISTS
            } else {

                binding.dialogCreateTitleInput.error = null
            }
        }

        groupViewModel.insertGroupResult.observe(viewLifecycleOwner) {result ->
            if(result.isSuccess) {
                dismiss()
                binding.dialogCreateTitleInput.setText("")
            } else {
                Snackbar.make(binding.root, "Insert Failed", Snackbar.LENGTH_SHORT).show()
            }
        }


    }

}