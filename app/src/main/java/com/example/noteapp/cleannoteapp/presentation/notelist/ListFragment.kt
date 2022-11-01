package com.example.noteapp.cleannoteapp.presentation.notelist

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.databinding.*
import com.example.noteapp.cleannoteapp.models.ViewStateModel
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.models.enums.SortBy
import com.example.noteapp.cleannoteapp.models.enums.ViewBy
import com.example.noteapp.cleannoteapp.presentation.common.BaseFragment
import com.example.noteapp.cleannoteapp.presentation.data_binding.BindingAdapters
import com.example.noteapp.cleannoteapp.presentation.data_binding.ColorCategoryBinding
import com.example.noteapp.cleannoteapp.presentation.data_binding.SortByBinding
import com.example.noteapp.cleannoteapp.presentation.data_binding.ViewByBinding
import com.example.noteapp.cleannoteapp.presentation.notedetail.AddUpdateActivity
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.ViewState.EditItem
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.ViewState.NewItem
import com.example.noteapp.cleannoteapp.presentation.notelist.state.NoteListToolbarState.ListViewState
import com.example.noteapp.cleannoteapp.presentation.notelist.state.NoteListToolbarState.MultiSelectionState
import com.example.noteapp.cleannoteapp.room_database.note_table.Dates
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteModel
import com.example.noteapp.cleannoteapp.util.extensions.enableListViewToolbarState
import com.example.noteapp.cleannoteapp.util.extensions.enableMultiSelection
import com.example.noteapp.cleannoteapp.util.printLogD
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class ListFragment : BaseFragment(), NoteListAdapter.Interaction {
    private lateinit var binding: FragmentListBinding
    private val viewModel: ListViewModel by activityViewModels()
    private val className = this.javaClass.simpleName
    private var menuItemColorCategory: MenuItem? = null
    private var noteListAdapter: NoteListAdapter? = null
    private var navBottomView: BottomNavigationView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNoteListAdapter()

        viewModel.loadDefaultColor()
        viewModel.loadDefaultSortBy()
        viewModel.loadDefaultViewBy()

        initMenuState()
        initScrollBehaviour()
        subscribeObservers()
        initMenu()
        initFetchList()
        initOnClickListener()

        navBottomView = requireActivity().findViewById(R.id.bottomNavigationView)
    }

    private fun initOnClickListener() {
        binding.floatingActionButton.setOnClickListener {
            lunchChoice()
        }

        binding.listBottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_archive -> {
                    transferItemsToArchive()
                }
                R.id.bottom_color -> {
                    lunchColorSelectMenu()
                }
                R.id.bottom_delete -> {
                    transferItemsToBin()
                }
                else -> {}

            }
            false
        }
    }

    private fun backToListViewState() {
        viewModel.setToolbarState(ListViewState)
        viewModel.clearSelectedNotes()
    }

    private fun updateColorMulti(colorCategory: ColorCategory) {
        if (viewModel.getSelectedNotes().size > 0) {
            crudViewModel.updateMultipleColorItems(
                viewModel.getSelectedNotesID(),
                colorCategory
            )
            backToListViewState()
        }
    }

    private fun transferItemsToBin() {
        if (viewModel.getSelectedNotes().size > 0) {
            crudViewModel.transferItemsToBin(viewModel.getSelectedNotesID())
            backToListViewState()
        }
    }

    private fun transferItemsToArchive() {
        if (viewModel.getSelectedNotes().size > 0) {
            crudViewModel.transferItemsToArchive(viewModel.getSelectedNotesID())
            backToListViewState()
        }
    }

    private fun restoreDeletedData(notes: ArrayList<NoteModel>) {
        val snackBar = Snackbar.make(
            binding.floatingActionButton, "Note deleted",
            Snackbar.LENGTH_SHORT
        )

        // snackBar.anchorView = binding.listBottomNavigationView

        snackBar.setAction("Undo") {
            printLogD(className, notes.size.toString())
            crudViewModel.insertListOfData(notes)
        }
        snackBar.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()

        noteListAdapter = null
        navBottomView = null
        menuItemColorCategory = null
        BindingAdapters.setItemOnClickListener(null)
        BindingAdapters.setSortByOnClickListener(null)
        BindingAdapters.setViewByOnClickListener(null)
    }

    private fun initNoteListAdapter() {
        noteListAdapter = NoteListAdapter(
            this@ListFragment,
            viewLifecycleOwner,
            viewModel.noteInteractionManager.selectedNotes,
            true
        )
    }

    private fun subscribeObservers() {
        viewModel.toolbarState.observe(viewLifecycleOwner) {
            when (it) {
                is MultiSelectionState -> {
                    enableMultiSelectToolbarState()
                    // disableSearchViewToolbarState()
                }
                is ListViewState -> {
                    enableListViewToolbarState()
                    disableMultiSelectToolbarState()
                }
            }
        }

        viewModel.selectedNotesInteractionState.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.appBar.title = "${it.size} selected"
            }
        }
    }

    private fun enableMultiSelectToolbarState() {
        binding.appBar.enableMultiSelection(resources)
        navBottomView?.isVisible = false
        binding.listBottomNavigationView.isVisible = true
        binding.floatingActionButton.isVisible = false
    }

    private fun enableListViewToolbarState() {
        binding.appBar.enableListViewToolbarState(resources)
        navBottomView?.isVisible = true
        binding.listBottomNavigationView.isVisible = false
        binding.floatingActionButton.isVisible = true
    }

    private fun disableMultiSelectToolbarState() {
        viewModel.clearSelectedNotes()
    }

    override fun onStart() {
        super.onStart()

        //Menus Listener
        initColorSelectedListener()
        initSortByListener()
        initViewByListener()
    }

    private fun initViewByListener() {
        BindingAdapters.setViewByOnClickListener(object : ViewByBinding {
            override fun onClick(viewBy: ViewBy) {
                viewModel.setViewByMenuState(viewBy)
                viewModel.saveViewBy(viewBy)
                bottomSheetDialog.dismiss()
            }
        })
    }

    private fun initSortByListener() {
        BindingAdapters.setSortByOnClickListener(object : SortByBinding {
            override fun onClick(sortBy: SortBy) {
                viewModel.setSortCategory(sortBy)
                viewModel.saveSortBy(sortBy)
                bottomSheetDialog.dismiss()
            }
        })
    }

    private fun initColorSelectedListener() {
        BindingAdapters.setItemOnClickListener(object : ColorCategoryBinding {
            override fun userSelectedColor(colorBinding: ColorCategory) {
                if (viewModel.toolbarStateValue == ListViewState) {
                    viewModel.setByColorCategory(colorBinding)
                    viewModel.saveDefaultColor(colorBinding)
                } else {
                    updateColorMulti(colorBinding)
                }
                bottomSheetDialog.dismiss()
            }
        })
    }

    private fun initMenuState() {
        viewModel.viewByMenuInteractionState.observe(viewLifecycleOwner) {
            when (it) {
                ViewBy.List -> {
                    //TODO
                    crudViewModel.insertListOfData(generateRecord())
                    binding.recyclerView.layoutManager = getGridLayoutManager()
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
                else -> {}
            }
            noteListAdapter?.notifyDataSetChanged()
            bottomSheetDialog.dismiss()
        }
    }

    private fun getGridLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(requireContext(), 2, OrientationHelper.VERTICAL, false)
    }

    private fun getDetailsLayoutManger(): LinearLayoutManager {
        return LinearLayoutManager(requireContext())
    }

    private fun initScrollBehaviour() {
//        ScrollAwareFABBehavior(
//            recyclerView = binding.recyclerView,
//            floatingActionButton = binding.floatingActionButton,
//            requireActivity().findViewById(R.id.bottomNavigationView)
//        ).start()
    }


    private fun initFetchList() {
        binding.recyclerView.itemAnimator = null
        binding.recyclerView.adapter = noteListAdapter


        lifecycleScope.launch {
            viewModel.combineObserver.collectLatest {
                menuItemColorCategory?.icon =
                    getImage(
                        viewModel.getColorCategoryItem(it.colorCategory).primaryColor,
                        it.colorCategory
                    )

                crudViewModel.fetchListViewRecords(it.colorCategory, it.sortBy)
                    .collectLatest { data ->
                        noteListAdapter?.submitData(data)
                    }
            }
        }

        //Scroll to top
        noteListAdapter?.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                binding.recyclerView.scrollToPosition(positionStart)
            }
        })
    }

    private fun initMenu() {
        menuItemColorCategory = binding.appBar.menu.findItem(R.id.menu_filter_by_color)

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
                R.id.menu_select_item -> {
                    viewModel.setToolbarState(MultiSelectionState)
                }
            }
            true
        }

        binding.appBar.setNavigationOnClickListener {
            viewModel.setToolbarState(ListViewState)
        }
    }

    private fun lunchViewByMenu() {
        val view = BottomDialogViewByBinding.inflate(layoutInflater)
        view.root.findViewById<ImageView>(viewModel.getViewByID(viewModel.viewByMenuInteractionState.value!!))
            .isVisible = true
        lunchBottomSheet(view.root)
    }

    private fun lunchColorSelectMenu() {
        val view = BottomDialogChangeColorBinding.inflate(layoutInflater)
        view.allNotes.isVisible = true
        view.viewText.text = "Filter"
        view.root.findViewById<ImageView>(viewModel.getColorCategoryItem(viewModel.viewByColorInteractionState.value!!).selectedItem)
            .isVisible = true
        lunchBottomSheet(view.root)
    }

    private fun lunchSortByMenu() {
        val view = BottomDialogSoryByBinding.inflate(layoutInflater)
        view.root.findViewById<ImageView>(viewModel.getSortByID(viewModel.sortByInteractionState.value!!))
            .isVisible = true
        lunchBottomSheet(view.root)
    }

    private fun lunchChoice() {
        val layout = BottomDialogAddItemBinding.inflate(layoutInflater)

        layout.txt.setOnClickListener {
            val intent = Intent(requireContext(), AddUpdateActivity::class.java).apply {
                putExtra(AddUpdateActivity.DETAIL_FRAGMENT, ViewStateModel(NewItem, null))
            }
            startActivity(intent)
            bottomSheetDialog.dismiss()
        }

        layout.checklist.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        lunchBottomSheet(layout.root)
    }

    private fun getImage(color: Int, category: ColorCategory): Drawable {
        return if (category == viewModel.getCategoryAllNotes()) {
            resources.getDrawable(R.drawable.all_category, null)
        } else {
            val drawable: Drawable = resources.getDrawable(R.drawable.circle_category, null)
            drawable.setColorFilter(
                resources.getColor(color, null),
                PorterDuff.Mode.SRC_IN
            )
            drawable
        }
    }

    override fun onItemSelected(position: Int, item: NoteModel) {
        if (isMultiSelectionModeEnabled()) {
            viewModel.addOrRemoveNoteFromSelectedList(item)
        } else {
            val intent = Intent(requireContext(), AddUpdateActivity::class.java).apply {
                putExtra(AddUpdateActivity.DETAIL_FRAGMENT, ViewStateModel(EditItem, item))
            }
            startActivity(intent)
        }
    }

    override fun isMultiSelectionModeEnabled() = viewModel.isMultiSelectionStateActive()

    override fun activateMultiSelectionMode() = viewModel.setToolbarState(MultiSelectionState)

    override fun isNoteSelected(note: NoteModel): Boolean {
        return viewModel.isNoteSelected(note)
    }

    private fun generateRecord(): ArrayList<NoteModel> {
        val new = NoteModel(
            header = UUID.randomUUID().toString(),
            body = UUID.randomUUID().toString(),
            dates = Dates(Date(), Date()),
            category = ColorCategory.OPTION_ONE,
            pinned = true
        )

        val new2 = NoteModel(
            header = UUID.randomUUID().toString(),
            body = UUID.randomUUID().toString(),
            dates = Dates(Date(), Date()),
            category = ColorCategory.OPTION_TWO,
            pinned = false
        )

        val new3 = NoteModel(
            header = UUID.randomUUID().toString(),
            body = UUID.randomUUID().toString(),
            dates = Dates(Date(), Date()),
            category = ColorCategory.OPTION_THREE,
            pinned = false
        )

        val new4 = NoteModel(
            header = UUID.randomUUID().toString(),
            body = UUID.randomUUID().toString(),
            dates = Dates(Date(), Date()),
            category = ColorCategory.OPTION_FOUR,
            pinned = false
        )

        val new5 = NoteModel(
            header = UUID.randomUUID().toString(),
            body = UUID.randomUUID().toString(),
            dates = Dates(Date(), Date()),
            category = ColorCategory.OPTION_FIVE,
            pinned = false
        )

        val new6 = NoteModel(
            header = UUID.randomUUID().toString(),
            body = UUID.randomUUID().toString(),
            dates = Dates(Date(), Date()),
            category = ColorCategory.OPTION_SIX,
            pinned = true
        )

        val new7 = NoteModel(
            header = UUID.randomUUID().toString(),
            body = UUID.randomUUID().toString(),
            dates = Dates(Date(), Date()),
            category = ColorCategory.OPTION_SEVEN,
            pinned = false
        )

        val new8 = NoteModel(
            header = UUID.randomUUID().toString(),
            body = UUID.randomUUID().toString(),
            dates = Dates(Date(), Date()),
            category = ColorCategory.OPTION_THREE,
            pinned = true
        )

        val new9 = NoteModel(
            header = UUID.randomUUID().toString(),
            body = UUID.randomUUID().toString(),
            dates = Dates(Date(), Date()),
            category = ColorCategory.OPTION_FOUR,
            pinned = false
        )

        val new10 = NoteModel(
            header = UUID.randomUUID().toString(),
            body = UUID.randomUUID().toString(),
            dates = Dates(Date(), Date()),
            category = ColorCategory.OPTION_THREE,
            pinned = false
        )

        return arrayListOf(new, new2, new3, new4, new5, new6, new7, new8, new9, new10)
    }
}