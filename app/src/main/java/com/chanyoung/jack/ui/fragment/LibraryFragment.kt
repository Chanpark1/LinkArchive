package com.chanyoung.jack.ui.fragment

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chanyoung.jack.R
import com.chanyoung.jack.databinding.FragmentLibraryBinding
import com.chanyoung.jack.ui.adapter.recycler.AllGroupListAdapter
import com.chanyoung.jack.ui.component.dialog.GroupOptionDialog
import com.chanyoung.jack.ui.fragment.basic.JBasicFragment
import com.chanyoung.jack.ui.viewmodel.GroupViewModel
import com.chanyoung.jack.ui.viewmodel.paging.GroupPagingViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LibraryFragment @Inject constructor() : JBasicFragment<FragmentLibraryBinding>() {

    private val pagingViewModel: GroupPagingViewModel by activityViewModels()
    private val groupViewModel: GroupViewModel by activityViewModels()

    private lateinit var adapter: AllGroupListAdapter

    private var groupId: Int = 0

    private val groupOptionDialog: GroupOptionDialog by lazy {
        GroupOptionDialog(
            ::onDelete,
            ::onEdit
        )
    }

    override fun layoutId(): Int = R.layout.fragment_library
    override fun onCreateView() {
        initRecyclerView()

        updateGroupId()
    }

    override fun initRecyclerView() {
        adapter = AllGroupListAdapter(
            onSelectItem = { groupId -> onSelectItem(groupId) },
            onSelectMenu = { groupId -> onSelectMenu(groupId) }
        )

        binding.fragLibRv.layoutManager = LinearLayoutManager(requireContext())
        binding.fragLibRv.adapter = adapter

        pagingViewModel.groups.observe(viewLifecycleOwner) { groups ->
            adapter.submitData(viewLifecycleOwner.lifecycle, groups)
        }
    }

    private fun updateGroupId() {
        groupViewModel.selectedGroupId.observe(viewLifecycleOwner) {groupId ->
            this.groupId = groupId
        }
    }

    private fun onSelectItem(gid: Int) {
        groupViewModel.onGroupItemSelected(gid)
    }

    private fun onSelectMenu(gid: Int) {
        groupOptionDialog.show(parentFragmentManager, "GroupOption")
        groupViewModel.onGroupItemSelected(gid)
    }

    private fun onDelete() {
        groupViewModel.deleteGroup(groupId)
        Handler(Looper.myLooper()!!).postDelayed({
            pagingViewModel.refreshData()
        }, 200)
    }

    private fun onEdit() {

    }

    override fun onResume() {
        super.onResume()
        pagingViewModel.refreshData()
    }


}