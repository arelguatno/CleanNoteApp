package com.example.noteapp.cleannoteapp.presentation.notelist

import android.content.Context
import android.content.res.ColorStateList
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.databinding.RecyclerViewItemRowBinding
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.models.enums.ViewBy
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteModel

class RecyclerViewHolder constructor(
    private val binding: RecyclerViewItemRowBinding,
    private val interaction: NoteListAdapter.Interaction?,
    private val lifecycleOwner: LifecycleOwner,
    private val selectedNotes: LiveData<ArrayList<NoteModel>>,
    private val viewState: LiveData<ViewBy>,
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

        colorCategory(item.category, binding, this.context)

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

        viewState.observe(lifecycleOwner) {
            when (it) {
                ViewBy.List -> {
                    binding.divider.isVisible = false
                    binding.body.isVisible = false
                }
                ViewBy.Grid -> {
                    binding.body.setLines(7)
                    binding.divider.isVisible = true
                    binding.body.isVisible = true
                }
                ViewBy.Details -> {
                    binding.body.setLines(3)
                    binding.divider.isVisible = true
                    binding.body.isVisible = true
                }
                ViewBy.Default -> {
                    binding.body.setLines(7)
                }
            }
        }
    }

    private fun colorCategory(
        item: ColorCategory?,
        holder: RecyclerViewItemRowBinding,
        context: Context
    ) {
        when (item) {
            ColorCategory.OPTION_ONE -> {
                widgets(holder, R.color.color_one_primary, R.color.color_one_secondary, context)
            }
            ColorCategory.OPTION_TWO -> {
                widgets(holder, R.color.color_two_primary, R.color.color_two_secondary, context)
            }
            ColorCategory.OPTION_THREE -> {
                widgets(holder, R.color.color_three_primary, R.color.color_three_secondary, context)
            }
            ColorCategory.OPTION_FOUR -> {
                widgets(holder, R.color.color_four_primary, R.color.color_four_secondary, context)
            }
            ColorCategory.OPTION_FIVE -> {
                widgets(holder, R.color.color_five_primary, R.color.color_five_secondary, context)
            }
            ColorCategory.OPTION_SIX -> {
                widgets(holder, R.color.color_six_primary, R.color.color_six_secondary, context)
            }
            ColorCategory.OPTION_SEVEN -> {
                widgets(holder, R.color.color_seven_primary, R.color.color_seven_secondary, context)
            }
            ColorCategory.OPTION_EIGHT -> {
                widgets(holder, R.color.color_eight_primary, R.color.color_eight_secondary, context)
            }
            else -> {
                widgets(holder, R.color.color_eight_primary, R.color.color_eight_secondary, context)
            }
        }
    }

    private fun widgets(
        holder: RecyclerViewItemRowBinding,
        primaryColor: Int,
        secondaryColor: Int,
        context: Context
    ) {
        holder.notePrimaryColor.backgroundTintList =
            ColorStateList.valueOf(context.resources.getColor(primaryColor, null))
        holder.noteSecondaryColor.backgroundTintList =
            ColorStateList.valueOf(context.resources.getColor(secondaryColor, null))
    }
}