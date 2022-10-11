package com.example.noteapp.cleannoteapp.presentation.notelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.databinding.ListRowBinding
import com.example.noteapp.cleannoteapp.models.NoteModel
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory

class NoteListAdapter : PagingDataAdapter<NoteModel, NoteListAdapter.MyViewHolder>(DIFF_CALLBACK) {
    inner class MyViewHolder(val binding: ListRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NoteModel>() {
            override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
                return oldItem.body == newItem.body
            }

            override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.header.text = item!!.header
        holder.binding.date.text = item.date
        holder.binding.body.text = item.date
        colorCategory(item.category, holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ListRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private fun colorCategory(item: ColorCategory?, holder: MyViewHolder) {
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

    private fun widgets(holder: MyViewHolder, primaryColor: Int, secondaryColor: Int) {
        holder.binding.notePrimaryColor.setBackgroundResource(primaryColor)
        holder.binding.noteSecondaryColor.setBackgroundResource(secondaryColor)
    }
}