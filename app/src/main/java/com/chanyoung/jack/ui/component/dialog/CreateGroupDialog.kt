package com.chanyoung.jack.ui.component.dialog

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.activityViewModels
import com.chanyoung.jack.application.ErrorMessages
import com.chanyoung.jack.databinding.DialogCreateGroupBinding
import com.chanyoung.jack.ui.component.dialog.basic.JBasicBottomSheetDialog
import com.chanyoung.jack.ui.viewmodel.AddLinkViewModel

class CreateGroupDialog(
    private val onSave: (String) -> Unit
) : JBasicBottomSheetDialog<DialogCreateGroupBinding>() {

    private val addLinkViewModel: AddLinkViewModel by activityViewModels()
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

        addLinkViewModel.insertGroupResult.observe(viewLifecycleOwner) { result ->
            if(result) {
                dismiss()
            } else {
                binding.dialogCreateTitleInput.error = ErrorMessages.GROUP_EXISTS
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ViewCompat.setOnApplyWindowInsetsListener(requireDialog().window?.decorView!!) { _, insets ->
                    val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom

                    val navigationBarHeight = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom

                    val topPadding = binding.root.paddingTop

                    val startPadding = binding.root.paddingStart

                    val endPadding = binding.root.paddingEnd

                    binding.root.setPadding(
                        startPadding,
                        topPadding,
                        endPadding,
                        imeHeight - navigationBarHeight
                    )
                    insets
                }
            } else {
                setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            }
        }
    }
}




