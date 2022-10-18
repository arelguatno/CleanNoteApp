package com.example.noteapp.cleannoteapp.presentation.notelist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.databinding.*
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.models.enums.ViewBy
import com.example.noteapp.cleannoteapp.presentation.common.BaseFragment
import com.example.noteapp.cleannoteapp.presentation.data_binding.BindingAdapters
import com.example.noteapp.cleannoteapp.presentation.data_binding.ColorCategoryBinding
import com.example.noteapp.cleannoteapp.presentation.notedetail.AddUpdateActivity
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteViewModel
import com.example.noteapp.cleannoteapp.util.Constants.GRID_SPAN_COUNT
import com.example.noteapp.cleannoteapp.util.ScrollAwareFABBehavior
import com.example.noteapp.cleannoteapp.util.printLogD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : BaseFragment() {
    private lateinit var binding: FragmentListBinding
    private val noteListAdapter: NoteListAdapter by lazy { NoteListAdapter() }
    private val crudViewModel: NoteViewModel by viewModels()
    private val mainViewModel: ListViewModel by activityViewModels()
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

        initScrollBehaviour()
        initMenuState()
        initMenu()
        initList()

        binding.floatingActionButton.setOnClickListener {
            lunchChoice()
        }
    }

    override fun onStart() {
        super.onStart()
        initColorSelectedListener()
    }

    private fun initColorSelectedListener() {
        BindingAdapters.setItemOnClickListener(object : ColorCategoryBinding {
            override fun userSelectedColor(colorBinding: ColorCategory) {
                bottomSheetDialog.dismiss()
                mainViewModel.setByColorCategory(colorBinding)
            }
        })
    }

    private fun initMenuState() {
        mainViewModel.viewByMenuInteractionState.observe(viewLifecycleOwner) {
            when (it) {
                ViewBy.List -> {
                    //TODO
                }
                ViewBy.Grid -> {
                    binding.recyclerView.layoutManager = getGridLayoutManager()
                }
                ViewBy.Details -> {
                    binding.recyclerView.layoutManager = getDetailsLayoutManger()
                }
                ViewBy.Default -> {
                    binding.recyclerView.layoutManager = getGridLayoutManager()
                }
            }
            noteListAdapter.notifyDataSetChanged()
            bottomSheetDialog.dismiss()
        }
    }

    private fun getGridLayoutManager(): RecyclerView.LayoutManager {
        return StaggeredGridLayoutManager(GRID_SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun getDetailsLayoutManger(): LinearLayoutManager {
        return LinearLayoutManager(requireContext())
    }

    private fun initScrollBehaviour() {
        ScrollAwareFABBehavior(
            recyclerView = binding.recyclerView,
            floatingActionButton = binding.floatingActionButton,
            requireActivity().findViewById(R.id.bottomNavigationView)
        ).start()
    }


    private fun initList() {
        binding.recyclerView.itemAnimator = null
        binding.recyclerView.adapter = noteListAdapter

        mainViewModel.viewByColorInteractionState.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                if (it.equals(ColorCategory.DEFAULT)) {
                    crudViewModel.fetchRecordData().collectLatest {
                        noteListAdapter.submitData(it)
                    }
                } else {
                    crudViewModel.fetchNotesPerCategory(it).collectLatest {
                        noteListAdapter.submitData(it)
                    }
                }
            }
        }
    }

    private fun initMenu() {
        binding.appBar.inflateMenu(R.menu.list_fragment_menu)
        binding.appBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_filter_by_color -> {
                    lunchColorSelectMenu()
                }
                R.id.menu_sortBy -> {
                    lunchSortByMenu()
                }

                R.id.menu_view_page -> {
                    lunchViewByMenu()
                }
            }
            true
        }
        //  binding.appBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
    }

    private fun lunchViewByMenu() {
        val view = ViewBySheetDialogBinding.inflate(layoutInflater)

        view.details.setOnClickListener {
            mainViewModel.setViewByMenuState(ViewBy.Details)
        }

        view.grid.setOnClickListener {
            mainViewModel.setViewByMenuState(ViewBy.Grid)
        }
        lunchBottomSheet(view.root)
    }

    private fun lunchColorSelectMenu() {
        val view = LayoutChangeColorBinding.inflate(layoutInflater)
        view.allNotes.isVisible = true
        view.viewText.text = "Filter"
        lunchBottomSheet(view.root)
    }

    private fun lunchSortByMenu() {
        val view = SortBySheetDialogBinding.inflate(layoutInflater)
        lunchBottomSheet(view.root)
    }

    private fun lunchChoice() {
        val layout = AddBottomSheetDialogBinding.inflate(layoutInflater)

        layout.txt.setOnClickListener {
            val intent = Intent(requireContext(), AddUpdateActivity::class.java)
            startActivity(intent)
            bottomSheetDialog.dismiss()
        }

        layout.checklist.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        lunchBottomSheet(layout.root)
    }
}