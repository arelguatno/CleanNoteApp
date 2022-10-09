package com.example.noteapp.cleannoteapp.presentation.notelist

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.presentation.common.BaseFragment
import com.example.noteapp.cleannoteapp.databinding.FragmentListBinding
import com.example.noteapp.cleannoteapp.models.NoteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class ListFragment : BaseFragment() {
    private lateinit var binding: FragmentListBinding
    private val noteListAdapter: NoteListAdapter by lazy { NoteListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater)
        menuInit()
        initList()
        return binding.root
    }

    private fun initList() {
        val items = NoteListAdapterTwo(data())
        binding.recyclerView.itemAnimator = null
        binding.recyclerView.adapter = items
        //binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun menuInit() {
        binding.appBar.inflateMenu(R.menu.list_fragment_menu)
        //  binding.appBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
    }

    private fun data(): List<NoteModel> {
        val entry_one = NoteModel(
            header = "Sample 1",
            date = "09:58",
            body = "The quick brown fox jump over the lazy dog. The quick brown fox jump over the lazy dog. The quick brown fox jump over the lazy dog. The quick brown fox jump over the lazy dog. The quick brown fox jump over the lazy dog The quick brown fox jump over the lazy dogThe quick brown fox jump over the lazy dog The quick brown fox jump over the lazy dog The quick brown fox jump over the lazy dog\" />\n"
        )

        val entry_two = NoteModel(
            header = "Sample 2",
            date = "09:58",
            body = "The quick brown fox jump over the lazy dog. The quick brown fox jump over the lazy dog. The quick brown fox jump over the lazy dog. The quick brown fox jump over the lazy dog. The quick brown fox jump over the lazy dog The quick brown fox jump over the lazy dogThe quick brown fox jump over the lazy dog The quick brown fox jump over the lazy dog The quick brown fox jump over the lazy dog\" />\n"
        )

        val entry_three = NoteModel(
            header = "Sample 3",
            date = "09:58",
            body = "The quick brown fox jump over the lazy dog. The quick brown fox jump over the lazy dog. The quick brown fox jump over the lazy dog. The quick brown fox jump over the lazy dog. The quick brown fox jump over the lazy dog The quick brown fox jump over the lazy dogThe quick brown fox jump over the lazy dog The quick brown fox jump over the lazy dog The quick brown fox jump over the lazy dog\" />\n"
        )

        val expected = listOf(
            entry_one,
            entry_two,
            entry_three
        )
        return expected
    }
}