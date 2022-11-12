package com.example.noteapp.cleannoteapp.presentation.archive_and_bin

import android.content.Context
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.noteapp.cleannoteapp.presentation.notelist.ListFragment
import com.example.noteapp.cleannoteapp.presentation.notelist.state.NoteListScreenState
import com.example.noteapp.cleannoteapp.presentation.notelist.state.NoteListScreenState.*
import com.example.noteapp.cleannoteapp.presentation.notelist.state.NoteListToolbarState.*
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteModel
import com.example.noteapp.cleannoteapp.util.PreferenceKeys.Companion.BIN_ARCHIVE_VIEW
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BinFragment : ListFragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.setListScreenState(BinView)
    }

    override fun appBarBackButton() {
        binding.appBar.setNavigationOnClickListener {
            if (isMultiSelectionModeEnabled()) {
                viewModel.setToolbarState(ListViewState)
                viewModel.setListScreenState(BinView)
            } else {
                setFragmentResult(BIN_ARCHIVE_VIEW, bundleOf(BIN_ARCHIVE_VIEW to true))
                it.findNavController().popBackStack()
            }
        }
    }

    override fun appBarBackButtonState() {
        viewModel.setToolbarState(ListViewState)
        viewModel.setListScreenState(BinView)
    }

    override fun onItemSelected(position: Int, item: NoteModel) {
        if (isMultiSelectionModeEnabled()) {
            viewModel.addOrRemoveNoteFromSelectedList(item)
        }
    }

    override fun fetchData() {
        lifecycleScope.launch {
            viewModel.combineObserver.collectLatest {
                crudViewModel.fetchListViewRecords(it.colorCategory, it.sortBy, BinView)
                    .collectLatest { data ->
                        noteListAdapter?.submitData(data)
                    }
            }
        }
    }
}