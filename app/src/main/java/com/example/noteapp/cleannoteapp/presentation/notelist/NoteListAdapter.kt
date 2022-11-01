package com.example.noteapp.cleannoteapp.presentation.notelist

import android.content.res.ColorStateList
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
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
    private val grid: Boolean,
) : PagingDataAdapter<NoteModel, NoteListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    inner class MyViewHolder constructor(
        private val binding: RecyclerViewItemRowBinding,
        private val interaction: Interaction?,
        private val lifecycleOwner: LifecycleOwner,
        private val selectedNotes: LiveData<ArrayList<NoteModel>>,
        private val grid: Boolean,
    ) : RecyclerView.ViewHolder(binding.root) {
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


            if (item.header.toString().isNotEmpty()) {
                binding.header.text = item.header
            } else {
                binding.header.text = item.body
            }

            binding.date.text = item.dates!!.dateModifiedStringValue
            binding.body.text = item.body

            if (grid) {
                binding.body.setLines(7)
            } else {
                binding.body.setLines(3)
            }
            colorCategory(item.category, binding)

            binding.imgPin.isVisible = item.pinned

            selectedNotes.observe(lifecycleOwner) { notes ->
                if (notes != null) {
                    if (notes.contains(note)) {
                        binding.main.setBackgroundResource(R.drawable.list_item_selector)
                    } else {
                        binding.main.setBackgroundResource(R.drawable.list_item_default)
                    }
                } else {
                    binding.main.setBackgroundResource(R.drawable.list_item_default)
                }
            }

        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NoteModel>() {
            override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
                return oldItem.id == newItem.id
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
            grid
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
        holder.notePrimaryColor.backgroundTintList =
            ColorStateList.valueOf(holder.main.resources.getColor(primaryColor, null))
        holder.noteSecondaryColor.backgroundTintList =
            ColorStateList.valueOf(holder.main.resources.getColor(secondaryColor, null))
    }

    interface Interaction {

        fun onItemSelected(position: Int, item: NoteModel)

        fun restoreListPosition()

        fun isMultiSelectionModeEnabled(): Boolean

        fun activateMultiSelectionMode()

        fun isNoteSelected(note: NoteModel): Boolean
    }
}