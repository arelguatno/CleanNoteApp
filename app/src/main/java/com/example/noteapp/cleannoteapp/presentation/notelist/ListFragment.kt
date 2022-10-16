package com.example.noteapp.cleannoteapp.presentation.notelist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.databinding.AddBottomSheetDialogBinding
import com.example.noteapp.cleannoteapp.databinding.FragmentListBinding
import com.example.noteapp.cleannoteapp.databinding.SortBySheetDialogBinding
import com.example.noteapp.cleannoteapp.presentation.common.BaseFragment
import com.example.noteapp.cleannoteapp.presentation.notedetail.AddUpdateActivity
import com.example.noteapp.cleannoteapp.presentation.notedetail.AddUpdateFragment
import com.example.noteapp.cleannoteapp.presentation.notelist.ListFragmentDirections.actionListFragmentToAddUpdateFragment2
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteViewModel
import com.example.noteapp.cleannoteapp.util.ScrollAwareFABBehavior
import com.example.noteapp.cleannoteapp.util.printLogD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : BaseFragment() {
    private lateinit var binding: FragmentListBinding
    private val noteListAdapter: NoteListAdapter by lazy { NoteListAdapter() }
    private val viewModel: NoteViewModel by viewModels()
    private val className = this.javaClass.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ScrollAwareFABBehavior(
            recyclerView = binding.recyclerView,
            floatingActionButton = binding.floatingActionButton,
            requireActivity().findViewById(R.id.bottomNavigationView)
        ).start()

        menuInit()
        initList()
        binding.floatingActionButton.setOnClickListener {
            lunchChoice()
        }
    }


    private fun initList() {
        binding.recyclerView.itemAnimator = null
        binding.recyclerView.adapter = noteListAdapter
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
       // binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            viewModel.fetchRecordData().collectLatest {
                noteListAdapter.submitData(it)
            }
        }
    }

    private fun menuInit() {
        binding.appBar.inflateMenu(R.menu.list_fragment_menu)
        binding.appBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_sortBy -> {
                    // launchSortBy()
                }
            }
            true
        }
        //  binding.appBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
    }

    private fun launchSortBy() {
        val view = SortBySheetDialogBinding.inflate(layoutInflater)
        bottomSheetDialog.dismissWithAnimation
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.setContentView(view.root)
        bottomSheetDialog.show()
    }

    private fun lunchChoice() {
        val view = AddBottomSheetDialogBinding.inflate(layoutInflater)

        view.txt.setOnClickListener {
            bottomSheetDialog.dismiss()
            val intent = Intent(requireContext(), AddUpdateActivity::class.java)
            startActivity(intent)
            bottomSheetDialog.dismiss()
        }

        view.checklist.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.dismissWithAnimation
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.setContentView(view.root)
        bottomSheetDialog.show()
    }
}