package com.chanyoung.jack.ui.fragment

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chanyoung.jack.R
import com.chanyoung.jack.databinding.FragmentLibraryBinding
import com.chanyoung.jack.ui.activity.GroupDetailActivity
import com.chanyoung.jack.ui.adapter.recycler.AllGroupListAdapter
import com.chanyoung.jack.ui.component.dialog.CreateGroupDialog
import com.chanyoung.jack.ui.component.dialog.EditGroupDialog
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

    private val editGroupDialog: EditGroupDialog by lazy { EditGroupDialog() }

    private val createGroupDialog: CreateGroupDialog by lazy { CreateGroupDialog() }

    override fun layoutId(): Int = R.layout.fragment_library
    override fun onCreateView() {
        initRecyclerView()

        setupObserver()

        onCreateGroup()
    }

    override fun initRecyclerView() {
        adapter = AllGroupListAdapter(
            onSelectItem = { groupId -> onSelectItem(groupId) },
            onSelectMenu = { groupId -> onSelectMenu(groupId) }
        )

        binding.fragLibRv.layoutManager = LinearLayoutManager(requireContext())
        binding.fragLibRv.adapter = adapter
    }

    private fun setupObserver() {
        groupViewModel.selectedGroupId.observe(viewLifecycleOwner) {groupId ->
            this.groupId = groupId
        }

        pagingViewModel.items.observe(viewLifecycleOwner) { groups ->
            adapter.submitData(viewLifecycleOwner.lifecycle, groups)
        }

        groupViewModel.deleteResult.observe(viewLifecycleOwner) {result ->
            if(result.isSuccess) {
                pagingViewModel.refreshData()
                groupViewModel.createDefaultLinkGroup()
            }
        }

        groupViewModel.editResult.observe(viewLifecycleOwner) {result ->
            if(result.isSuccess) {
                pagingViewModel.refreshData()
            }
        }

        groupViewModel.insertGroupResult.observe(viewLifecycleOwner) {result ->
            if(result.isSuccess) {
                pagingViewModel.refreshData()
            }
        }

    }

    private fun onSelectItem(gid: Int) {
        groupViewModel.onGroupItemSelected(gid)
        val intent = Intent(requireContext(), GroupDetailActivity::class.java)
        intent.putExtra("gid",gid)

        startActivity(intent)
    }

    private fun onSelectMenu(gid: Int) {
        groupOptionDialog.show(parentFragmentManager, "GroupOption")
        groupViewModel.onGroupItemSelected(gid)
    }

    private fun onCreateGroup() {
        binding.fragLibAdd.setOnClickListener {
            createGroupDialog.show(childFragmentManager, "CreateDialog")
        }
    }

    private fun onDelete() {
        groupViewModel.deleteGroup(groupId)
    }

    private fun onEdit() {
        editGroupDialog.show(childFragmentManager, "EditDialog")
    }
    override fun onResume() {
        super.onResume()
        pagingViewModel.refreshData()
        groupViewModel.createDefaultLinkGroup()
    }
}