package com.chanyoung.jack.ui.component.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.chanyoung.jack.application.ErrorMessages
import com.chanyoung.jack.databinding.DialogEditGroupBinding
import com.chanyoung.jack.ui.component.dialog.basic.JBasicBottomSheetDialog
import com.chanyoung.jack.ui.viewmodel.GroupViewModel
import com.google.android.material.snackbar.Snackbar

class EditGroupDialog : JBasicBottomSheetDialog<DialogEditGroupBinding>() {

    private var name: String = ""

    private var gid : Int = 0

    private val groupViewModel : GroupViewModel by activityViewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogEditGroupBinding = DialogEditGroupBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dialogEditTitleInput.error = null
        setupListener()
        setupObserver()
    }

    private fun setupListener() {
        binding.dialogEditBtn.setOnClickListener {
            if(binding.dialogEditTitleInput.text.toString() != "") {
                groupViewModel.setGroupName(binding.dialogEditTitleInput.text.toString())
                groupViewModel.checkDuplicateName(binding.dialogEditTitleInput.text.toString())
                groupViewModel.editGroup(name, gid)
            } else {
                binding.dialogEditTitleInput.error = ErrorMessages.TITLE_EMPTY
            }

        }
    }

    private fun setupObserver() {

        groupViewModel.groupName.observe(viewLifecycleOwner) {name ->
            this.name = name
        }

        groupViewModel.selectedGroupId.observe(viewLifecycleOwner) {gid ->
            this.gid = gid
        }

        groupViewModel.checkDuplicate.observe(viewLifecycleOwner) { result ->
            if (result.isFailure) {
                binding.dialogEditTitleInput.error = ErrorMessages.GROUP_EXISTS
            } else {
                binding.dialogEditTitleInput.error = null
            }
        }

        groupViewModel.editResult.observe(viewLifecycleOwner) { result ->
            if (result.isSuccess) {
                dismiss()
                binding.dialogEditTitleInput.setText("")
            } else if (result.isFailure) {
                Snackbar.make(binding.root, "Edit Failed", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

}