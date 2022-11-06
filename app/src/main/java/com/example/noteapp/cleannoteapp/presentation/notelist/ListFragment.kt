package com.example.noteapp.cleannoteapp.presentation.notelist

import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.*
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.databinding.*
import com.example.noteapp.cleannoteapp.models.ViewStateModel
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.models.enums.MenuActions
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
import com.example.noteapp.cleannoteapp.presentation.notelist.state.NoteListScreenState.*
import com.example.noteapp.cleannoteapp.presentation.notelist.state.NoteListToolbarState.ListViewState
import com.example.noteapp.cleannoteapp.presentation.notelist.state.NoteListToolbarState.MultiSelectionState
import com.example.noteapp.cleannoteapp.room_database.note_table.Dates
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteModel
import com.example.noteapp.cleannoteapp.util.Constants.GRID_SPAN_COUNT
import com.example.noteapp.cleannoteapp.util.PreferenceKeys.Companion.ADD_UPDATE_NODE_MODEL
import com.example.noteapp.cleannoteapp.util.PreferenceKeys.Companion.ADD_UPDATE_RESULT
import com.example.noteapp.cleannoteapp.util.extensions.enableListViewToolbarState
import com.example.noteapp.cleannoteapp.util.extensions.enableMultiSelection
import com.example.noteapp.cleannoteapp.util.extensions.serializable
import com.example.noteapp.cleannoteapp.util.printLogD
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
open class ListFragment : BaseFragment(), NoteListAdapter.Interaction {
    protected lateinit var binding: FragmentListBinding
    protected open val viewModel: ListViewModel by activityViewModels()
    private val className = this.javaClass.simpleName
    private var noteListAdapter: NoteListAdapter? = null
    private var navBottomView: BottomNavigationView? = null
    private var startForResult: ActivityResultLauncher<Intent>? = null


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
        subscribeObservers()
        initMenu()
        initFetchList()
        initOnClickListener()
        startActivityForResult()
        initOnBackPressDispatcher()

        navBottomView = requireActivity().findViewById(R.id.bottomNavigationView)
    }

    private fun initOnClickListener() {
        binding.floatingActionButton.setOnClickListener {
            lunchChoice()
        }

        binding.listBottomNavigationView.setOnItemSelectedListener {
            initColorSelectedListener()
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
            val tmpData = viewModel.getSelectedNotesID()
            restoreAction(
                tmpData,
                getString(viewModel.getToastBinMessage(tmpData)),
                MenuActions.Bin
            )
            backToListViewState()
        }
    }

    private fun transferItemsToArchive() {
        if (viewModel.getSelectedNotes().size > 0) {
            crudViewModel.transferItemsToArchive(viewModel.getSelectedNotesID())
            val tmpData = viewModel.getSelectedNotesID()
            restoreAction(
                tmpData,
                getString(viewModel.getToastArchiveMessage(tmpData)),
                MenuActions.Archive
            )
            backToListViewState()
        }
    }

    private fun restoreAction(notes: ArrayList<Int>, message: String, bin: MenuActions) {
        val snackBar = Snackbar.make(
            binding.floatingActionButton, message,
            Snackbar.LENGTH_SHORT
        )

        snackBar.anchorView = binding.floatingActionButton

        snackBar.setAction(getString(R.string.undo)) {
            when (bin) {
                MenuActions.Archive -> crudViewModel.undoTransferItemsToArchive(notes)
                MenuActions.Bin -> crudViewModel.undoTransferItemsToBin(notes)
            }
        }
        snackBar.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        noteListAdapter = null
        navBottomView = null
    }

    private fun initNoteListAdapter() {
        noteListAdapter = NoteListAdapter(
            this@ListFragment,
            viewLifecycleOwner,
            viewModel.noteInteractionManager.selectedNotes,
            viewModel.viewByMenuInteractionState
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

        viewModel.viewByColorInteractionState.observe(viewLifecycleOwner){
            menuColorBar(it)
        }
        viewModel.listScreenInterActionState.observe(viewLifecycleOwner) {
            when (it) {
                is ArchiveView -> {
                    binding.floatingActionButton.hide()
                    binding.appBar.navigationIcon = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_baseline_arrow_back_24,
                        null
                    )
                    navBottomView!!.isVisible = false
                    binding.appBar.title = "Archive"
                }
                is BinView -> {

                }
                is MainListView -> {
                    binding.floatingActionButton.show()
                    navBottomView!!.isVisible = true
                    binding.appBar.title = getString(R.string.app_name)
                }
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
                if (viewModel.isMainListViewScreenActive()) viewModel.saveViewBy(viewBy)
                bottomSheetDialog.dismiss()
            }
        })
    }

    private fun initSortByListener() {
        BindingAdapters.setSortByOnClickListener(object : SortByBinding {
            override fun onClick(sortBy: SortBy) {
                viewModel.setSortCategory(sortBy)
                if (viewModel.isMainListViewScreenActive()) viewModel.saveSortBy(sortBy)
                bottomSheetDialog.dismiss()
            }
        })
    }

    private fun initColorSelectedListener() {
        BindingAdapters.setItemOnClickListener(object : ColorCategoryBinding {
            override fun userSelectedColor(colorBinding: ColorCategory) {
                if (viewModel.toolbarStateValue == ListViewState) {
                    viewModel.setByColorCategory(colorBinding)
                    if (viewModel.isMainListViewScreenActive()) viewModel.saveDefaultColor(
                        colorBinding
                    )
                } else {
                    updateColorMulti(colorBinding)
                }
                bottomSheetDialog.dismiss()
            }
        })
    }

    private fun menuColorBar(colorBinding: ColorCategory) {
        binding.appBar.menu.findItem(R.id.menu_filter_by_color).icon =
            getImage(
                viewModel.getColorCategoryItem(colorBinding).primaryColor,
                colorBinding
            )
    }

    private fun initMenuState() {
        viewModel.viewByMenuInteractionState.observe(viewLifecycleOwner) {
            when (it) {
                ViewBy.List -> {
                    //crudViewModel.insertListOfData(generateRecord())
                    binding.recyclerView.layoutManager = getDetailsLayoutManger()
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
        return GridLayoutManager(
            requireContext(),
            GRID_SPAN_COUNT,
            OrientationHelper.VERTICAL,
            false
        )
    }

    private fun getDetailsLayoutManger(): LinearLayoutManager {
        return LinearLayoutManager(requireContext())
    }

    private fun initFetchList() {
        binding.recyclerView.itemAnimator = null
        binding.recyclerView.adapter = noteListAdapter


        lifecycleScope.launch {
            viewModel.combineObserver.collectLatest {
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
        appBarBackButton()
    }

    open fun appBarBackButton() {
        binding.appBar.setNavigationOnClickListener {
            viewModel.setToolbarState(ListViewState)
        }
    }

    open fun appBarBackButtonState() {
        viewModel.setToolbarState(ListViewState)
    }

    open fun initOnBackPressDispatcher() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isMultiSelectionModeEnabled()) {
                    appBarBackButtonState()
                } else {
                    if (viewModel.isMainListViewScreenActive()) {
                        requireActivity().finish()
                    } else {
                        view?.findNavController()?.popBackStack()
                    }

                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun lunchViewByMenu() {
        val view = BottomDialogViewByBinding.inflate(layoutInflater)
        view.root.findViewById<ImageView>(viewModel.getViewByID(viewModel.viewByMenuInteractionState.value!!))
            .isVisible = true
        lunchBottomSheet(view.root)
    }

    private fun lunchColorSelectMenu() {
        initColorSelectedListener()
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
            printLogD(className, "aa")
            resources.getDrawable(R.drawable.all_category, null)
        } else {
            printLogD(className, "bb")
            val drawable: Drawable = resources.getDrawable(R.drawable.circle_category, null)
            drawable.setColorFilter(
                resources.getColor(color, null),
                PorterDuff.Mode.SRC_IN
            )
            drawable
        }
    }

    private fun startActivityForResult() {
        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val menuAction = it.data?.serializable<MenuActions>(ADD_UPDATE_RESULT)
                    val item = it.data?.serializable<NoteModel>(ADD_UPDATE_NODE_MODEL)
                    if (menuAction != null && item != null) {
                        restoreAction(
                            arrayListOf(item.id),
                            getString(viewModel.getSingularMessage(menuAction)),
                            menuAction
                        )
                    }
                }
            }
    }

    override fun onItemSelected(position: Int, item: NoteModel) {
        if (isMultiSelectionModeEnabled()) {
            viewModel.addOrRemoveNoteFromSelectedList(item)
        } else {
            startForResult!!.launch(Intent(requireActivity(), AddUpdateActivity::class.java).apply {
                putExtra(AddUpdateActivity.DETAIL_FRAGMENT, ViewStateModel(EditItem, item))
            })
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