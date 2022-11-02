package com.example.noteapp.cleannoteapp.presentation.notelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.noteapp.cleannoteapp.databinding.RecyclerViewItemRowBinding
import com.example.noteapp.cleannoteapp.models.enums.ViewBy
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteModel

class NoteListAdapter(
    private val interaction: Interaction? = null,
    private val lifecycleOwner: LifecycleOwner,
    private val selectedNotes: LiveData<ArrayList<NoteModel>>,
    private val grid: LiveData<ViewBy>,
) : PagingDataAdapter<NoteModel, RecyclerViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NoteModel>() {
            override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
                return oldItem.id == newItem.id
                        && oldItem.header == newItem.header
                        && oldItem.body == newItem.body
                        && oldItem.pinned == newItem.pinned
                        && oldItem.bin == newItem.bin
                        && oldItem.archive == newItem.archive
                        && oldItem.dates!!.dateModifiedStringValue == newItem.dates!!.dateModifiedStringValue
                        && oldItem.dates!!.dateCreated == newItem.dates!!.dateCreated
                        && oldItem.dates == newItem.dates
//                return true
            }

            override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            RecyclerViewItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interaction,
            lifecycleOwner,
            selectedNotes,
            grid
        )
    }

    interface Interaction {

        fun onItemSelected(position: Int, item: NoteModel)

        fun isMultiSelectionModeEnabled(): Boolean

        fun activateMultiSelectionMode()

        fun isNoteSelected(note: NoteModel): Boolean
    }
}