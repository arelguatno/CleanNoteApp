package com.example.noteapp.cleannoteapp.presentation.notelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.cleannoteapp.databinding.ListRowBinding
import com.example.noteapp.cleannoteapp.models.NoteModel

class NoteListAdapterTwo(private val list: List<NoteModel>) :
    RecyclerView.Adapter<NoteListAdapterTwo.MyViewHolder>() {

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
        val item = list[position]
        holder.binding.header.text = item!!.header
        holder.binding.date.text = item.date
        holder.binding.body.text = item.body
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

    override fun getItemCount(): Int {
        return list.size
    }
}