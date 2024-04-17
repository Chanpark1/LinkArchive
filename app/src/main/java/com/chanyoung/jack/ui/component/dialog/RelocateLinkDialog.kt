package com.chanyoung.jack.ui.component.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chanyoung.jack.application.ErrorMessages
import com.chanyoung.jack.databinding.DialogRelocateLinkGroupBinding
import com.chanyoung.jack.ui.adapter.recycler.GroupItemAdapter
import com.chanyoung.jack.ui.component.dialog.basic.JBasicBottomSheetDialog
import com.chanyoung.jack.ui.viewmodel.LinkViewModel
import com.google.android.material.snackbar.Snackbar

class RelocateLinkDialog: JBasicBottomSheetDialog<DialogRelocateLinkGroupBinding>() {

    private lateinit var adapter: GroupItemAdapter

    private val linkViewModel : LinkViewModel by activityViewModels()

    private var groupId: Int = 0

    private var linkId: Int = 0

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?
    ): DialogRelocateLinkGroupBinding = DialogRelocateLinkGroupBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerview()

        setupSubmit()

        setupCancel()
    }

    private fun initRecyclerview() {
        adapter = GroupItemAdapter {groupId -> linkViewModel.onGroupItemSelected(groupId) }

        binding.dialogRelocateRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.dialogRelocateRv.adapter = adapter

        linkViewModel.setGroupList()

        linkViewModel.linkGroups.observe(viewLifecycleOwner) { linkGroups -> adapter.setList(linkGroups)  }

        linkViewModel.selectedGroupId.observe(viewLifecycleOwner) { selectedGroupId ->
            adapter.updateSelectedGroupId(selectedGroupId)
            groupId = selectedGroupId

        }
    }

    private fun setupSubmit() {
        binding.dialogRelocateSubmit.setOnClickListener {
            if(groupId == 0) {
                Snackbar.make(binding.root, ErrorMessages.GROUP_EMPTY, Snackbar.LENGTH_SHORT).show()
            } else if(linkId == 0) {
                Snackbar.make(binding.root, "System Error", Snackbar.LENGTH_SHORT).show()
            } else {
                linkViewModel.relocateLink(linkId, groupId)
                dismiss()
            }
        }
    }

    private fun setupCancel() {
        binding.dialogRelocateCancel.setOnClickListener {
            dismiss()
        }
    }

    fun updateLinkId(lid : Int) {
        this.linkId = lid
    }

}