package com.example.noteapp.cleannoteapp.presentation.notelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.databinding.RecyclerViewItemRowBinding
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteModel

class NoteListAdapter(
    private val interaction: Interaction? = null,
    private val lifecycleOwner: LifecycleOwner,
    private val selectedNotes: LiveData<ArrayList<NoteModel>>,
) : PagingDataAdapter<NoteModel, NoteListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    inner class MyViewHolder constructor(
        private val binding: RecyclerViewItemRowBinding,
        private val interaction: Interaction?,
        private val lifecycleOwner: LifecycleOwner,
        private val selectedNotes: LiveData<ArrayList<NoteModel>>
    ) : RecyclerView.ViewHolder(binding.root) {

        private val COLOR_GREY = R.color.app_background_color
        private val COLOR_PRIMARY = R.color.colorPrimary
        private lateinit var note: NoteModel

        fun bind(item: NoteModel) = with(itemView) {
            setOnClickListener {
                interaction?.onItemSelected(absoluteAdapterPosition, note)
            }

            setOnLongClickListener {
                interaction?.activateMultiSelectionMode()
                interaction?.onItemSelected(absoluteAdapterPosition, note)
                true
            }
            note = item

            binding.header.text = item.header
            binding.date.text = item.dates!!.dateModifiedStringValue
            binding.body.text = item.body
            colorCategory(item.category, binding)

            binding.imgPin.isVisible = item.pinned

            selectedNotes.observe(lifecycleOwner) { notes ->
                if (notes != null) {
                    binding.check.isVisible = notes.contains(note)
                } else {
                    binding.check.isVisible = false
                }
            }

        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NoteModel>() {
            override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
                return false  //set to false to avoid recyclerview pos change
            }

            override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        when (holder) {
            is MyViewHolder -> {
                getItem(position)?.let { holder.bind(it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RecyclerViewItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interaction,
            lifecycleOwner,
            selectedNotes,
        )
    }

    private fun colorCategory(item: ColorCategory?, holder: RecyclerViewItemRowBinding) {
        when (item) {
            ColorCategory.OPTION_ONE -> {
                widgets(holder, R.color.color_one_primary, R.color.color_one_secondary)
            }
            ColorCategory.OPTION_TWO -> {
                widgets(holder, R.color.color_two_primary, R.color.color_two_secondary)
            }
            ColorCategory.OPTION_THREE -> {
                widgets(holder, R.color.color_three_primary, R.color.color_three_secondary)
            }
            ColorCategory.OPTION_FOUR -> {
                widgets(holder, R.color.color_four_primary, R.color.color_four_secondary)
            }
            ColorCategory.OPTION_FIVE -> {
                widgets(holder, R.color.color_five_primary, R.color.color_five_secondary)
            }
            ColorCategory.OPTION_SIX -> {
                widgets(holder, R.color.color_six_primary, R.color.color_six_secondary)
            }
            ColorCategory.OPTION_SEVEN -> {
                widgets(holder, R.color.color_seven_primary, R.color.color_seven_secondary)
            }
            ColorCategory.OPTION_EIGHT -> {
                widgets(holder, R.color.color_eight_primary, R.color.color_eight_secondary)
            }
            else -> {
                widgets(holder, R.color.color_eight_primary, R.color.color_eight_secondary)
            }
        }
    }

    private fun widgets(
        holder: RecyclerViewItemRowBinding,
        primaryColor: Int,
        secondaryColor: Int
    ) {
        holder.notePrimaryColor.setBackgroundResource(primaryColor)
        holder.noteSecondaryColor.setBackgroundResource(secondaryColor)
    }

    interface Interaction {

        fun onItemSelected(position: Int, item: NoteModel)

        fun restoreListPosition()

        fun isMultiSelectionModeEnabled(): Boolean

        fun activateMultiSelectionMode()

        fun isNoteSelected(note: NoteModel): Boolean
    }
}