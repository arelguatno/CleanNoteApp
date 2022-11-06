package com.example.noteapp.cleannoteapp.presentation.archive

import android.content.Context
import androidx.navigation.findNavController
import com.example.noteapp.cleannoteapp.presentation.notelist.ListFragment
import com.example.noteapp.cleannoteapp.presentation.notelist.state.NoteListScreenState.ArchiveView
import com.example.noteapp.cleannoteapp.presentation.notelist.state.NoteListScreenState.MainListView
import com.example.noteapp.cleannoteapp.presentation.notelist.state.NoteListToolbarState
import com.example.noteapp.cleannoteapp.presentation.notelist.state.NoteListToolbarState.*
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteModel

class ArchiveFragment : ListFragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.setListScreenState(ArchiveView)
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.setListScreenState(MainListView)
        viewModel.clearSelectedNotes()
    }

    override fun appBarBackButton() {
        binding.appBar.setNavigationOnClickListener {
            if (isMultiSelectionModeEnabled()) {
                viewModel.setToolbarState(ListViewState)
                viewModel.setListScreenState(ArchiveView)
            } else {
                it.findNavController().popBackStack()
            }
        }
    }

    override fun appBarBackButtonState() {
        viewModel.setToolbarState(ListViewState)
        viewModel.setListScreenState(ArchiveView)
    }

    override fun onItemSelected(position: Int, item: NoteModel) {
        if (isMultiSelectionModeEnabled()) {
            viewModel.addOrRemoveNoteFromSelectedList(item)
        }
    }
}