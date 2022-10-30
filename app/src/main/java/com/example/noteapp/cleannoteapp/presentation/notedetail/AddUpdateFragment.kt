package com.example.noteapp.cleannoteapp.presentation.notedetail

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.databinding.BottomDialogChangeColorBinding
import com.example.noteapp.cleannoteapp.databinding.FragmentAddUpdateBinding
import com.example.noteapp.cleannoteapp.models.ViewStateModel
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.presentation.common.BaseFragment
import com.example.noteapp.cleannoteapp.presentation.data_binding.BindingAdapters
import com.example.noteapp.cleannoteapp.presentation.data_binding.ColorCategoryBinding
import com.example.noteapp.cleannoteapp.presentation.notedetail.AddUpdateActivity.Companion.DETAIL_FRAGMENT
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionState.DefaultState
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionState.EditState
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.ViewState.EditItem
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.ViewState.NewItem
import com.example.noteapp.cleannoteapp.room_database.note_table.Dates
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteModel
import com.example.noteapp.cleannoteapp.util.extensions.*
import com.example.noteapp.cleannoteapp.util.printLogD
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import java.util.*
import kotlin.reflect.typeOf

@AndroidEntryPoint
class AddUpdateFragment : BaseFragment() {
    private lateinit var binding: FragmentAddUpdateBinding
    private val className = this.javaClass.simpleName
    private val viewModel: AddUpdateViewModel by activityViewModels()

    private var menuItemColorCategory: MenuItem? = null
    private var menuItemPinned: MenuItem? = null
    private var activityMain: AddUpdateActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddUpdateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        menuItemColorCategory = null
        menuItemPinned = null
        activityMain = null
    }


    override fun onStart() {
        super.onStart()
        initColorSelectedListener() //registerColor change click listener
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityMain =
            context as AddUpdateActivity // so we can read intent data from activity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitialState()   // Set either NewItem or EditItem
        subscribeObservers()

        initOnBackPressDispatcher() // Phone Back Button
        initToolBarBackButton()  // Toolbar Back button

        initMenu() // Menu

        binding.addTextLayout.noteBody.setOnClickListener {
            onClickNoteBody()
        }

        binding.addTextLayout.noteTitle.setOnClickListener {
            onClickNoteTitle()
        }
    }

    private fun initColorSelectedListener() {
        BindingAdapters.setItemOnClickListener(object : ColorCategoryBinding {
            override fun userSelectedColor(colorBinding: ColorCategory) {
                onClickColorCategory()
                viewModel.setThemeState(EditState) // open main activity for theme change
                requireActivity().recreate()  // restart activity life cycle to set a new theme
                bottomSheetDialog.dismiss()
                viewModel.setThemeSelected(colorBinding)
            }
        })
    }

    private fun initMenu() {
        menuItemColorCategory = binding.appBar.menu.findItem(R.id.menu_color_category)
        menuItemPinned = binding.appBar.menu.findItem(R.id.menu_pinned)

        binding.appBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_color_category -> {
                    view?.hideKeyboard()
                    launchColorChange()
                }
                R.id.menu_pinned -> {
                    onClickPin()
                    setPinUI()
                }
            }
            true
        }
    }

    private fun setPinUI() {
        if (viewModel.pinnedInteractionState == true) {
            viewModel.setPinnedIsClicked(false)
        } else {
            viewModel.setPinnedIsClicked(true)
            showCustomToast("Pinned from the top")
        }
    }

    private fun showCustomToast(message: String) {
        context?.let {
            Toasty.custom(
                it.applicationContext,
                message,
                R.drawable.app_icon_two,
                android.R.color.background_dark,
                Toasty.LENGTH_SHORT,
                false,
                true
            ).show()
        }
    }

    private fun setInitialState() {
        val state = activityMain?.intent?.serializable<ViewStateModel>(DETAIL_FRAGMENT)

        when (state?.state) {
            is NewItem -> {
                if (!viewModel.isEditingBody() && !viewModel.isEditingTitle()) {
                    viewModel.loadDefaultColor()
                    viewModel.setViewState(ViewStateModel(NewItem, state.noteModel))
                    viewModel.setNoteInteractionBodyState(EditState)
                }
            }
            is EditItem -> {
                if (!viewModel.isEditingBody() && !viewModel.isEditingTitle() && !viewModel.isEditingColor() && !viewModel.isEditingPin()) {
                    setNoteBody(state.noteModel?.body.toString())
                    setNoteTitle(state.noteModel?.header.toString())
                    viewModel.setThemeSelected(state.noteModel?.category!!)
                    viewModel.setCurrentDate(state.noteModel.dates?.dateModified!!)
                    viewModel.setPinnedIsClicked(state.noteModel.pinned)
                    viewModel.setViewState(state)
                }
            }
            else -> {}
        }
    }


    private fun subscribeObservers() {
        viewModel.noteBodyInteractionState.observe(viewLifecycleOwner) {
            when (it) {
                is EditState -> {
                    binding.addTextLayout.noteBody.showKeyboard()
                    binding.addTextLayout.noteBody.enableContentInteraction()
                }
                is DefaultState -> {
                    binding.addTextLayout.noteBody.disableContentInteraction()
                }
                else -> {}
            }
        }

        viewModel.noteTitleInteractionState.observe(viewLifecycleOwner) {
            when (it) {
                is EditState -> {
                    binding.addTextLayout.noteTitle.enableContentInteraction()
                    binding.addTextLayout.noteTitle.showKeyboard()
                }

                is DefaultState -> {
                    binding.addTextLayout.noteTitle.disableContentInteraction()
                }
                else -> {}
            }
        }

        viewModel.themeSelectedInteraction.observe(viewLifecycleOwner) {
            setTheme(it)
        }

        viewModel.interactionDate.observe(viewLifecycleOwner) {
            binding.addTextLayout.txtDate.text = "Edited: ${it.appMainFormatWithTime()}"
        }

        viewModel.pinnedIsClicked.observe(viewLifecycleOwner) {
            if (it) {
                menuItemPinned?.pinOnClick(resources)
            } else {
                menuItemPinned?.pinUnClick(resources)
            }
        }
    }

    private fun onClickPin() {
        if (!viewModel.isEditingPin()) {
            viewModel.setPinnedState(EditState)
        }
    }

    private fun onClickNoteTitle() {
        if (!viewModel.isEditingTitle()) {
            viewModel.setNoteInteractionTitleState(EditState)
        }
    }

    private fun onClickColorCategory() {
        if (!viewModel.isEditingColor()) {
            viewModel.setColorInterActionState(EditState)
        }
    }

    private fun onClickNoteBody() {
        if (!viewModel.isEditingBody()) {
            viewModel.setNoteInteractionBodyState(EditState)
        }
    }

    private fun getImage(color: Int): Drawable {
        val drawable: Drawable = resources.getDrawable(R.drawable.circle_category, null)
        drawable.setColorFilter(
            resources.getColor(color, null),
            PorterDuff.Mode.SRC_IN
        )
        return drawable
    }

    private fun setTheme(colorCategory: ColorCategory) {
        viewModel.storeDynamicThemeSelected(colorCategory)
        binding.main.setBackgroundResource(viewModel.getColorCategoryItem(colorCategory).secondaryColor)
        binding.appBar.setBackgroundResource(viewModel.getColorCategoryItem(colorCategory).secondaryColor)
        menuItemColorCategory?.icon =
            getImage(viewModel.getColorCategoryItem(colorCategory).primaryColor)
    }

    fun onBackPressed() {
        view?.hideKeyboard()
        if (viewModel.checkEditState()) {
            saveRecord()
            viewModel.exitEditState()
            requireActivity().finish()
        } else {
            requireActivity().finish()
        }
    }

    private fun initToolBarBackButton() {
        binding.appBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initOnBackPressDispatcher() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun saveRecord() {
        if (getNoteTitle().isEmpty() && getNoteBody().isEmpty()) {
            printLogD(className, "Both records are empty")
            return
        }
        when (viewModel.viewState?.state) {
            is EditItem -> {
                val existingItem = viewModel.viewState!!.noteModel!!
                existingItem.header = getNoteTitle()
                existingItem.body = getNoteBody()
                existingItem.dates!!.dateModified = Date()
                existingItem.category = getColor()
                existingItem.pinned = getPinState()
                crudViewModel.updateRecord(existingItem)
            }
            is NewItem -> {
                val newData = NoteModel(
                    header = getNoteTitle(),
                    body = getNoteBody(),
                    dates = Dates(
                        dateCreated = getCurrentDate(),
                        dateModified = getViewModelDate()
                    ),
                    category = getColor(),
                    pinned = getPinState()
                )
                crudViewModel.insertRecord(newData)
            }
            else -> {}
        }
    }

    private fun getNoteTitle(): String {
        return binding.addTextLayout.noteTitle.text.toString()
    }

    private fun getNoteBody(): String {
        return binding.addTextLayout.noteBody.text.toString()
    }

    private fun setNoteTitle(title: String) {
        binding.addTextLayout.noteTitle.setText(title)
    }

    private fun setNoteBody(body: String) {
        binding.addTextLayout.noteBody.setText(body)
    }

    private fun getViewModelDate(): Date {
        return viewModel.currentDbDate!!
    }

    private fun getPinState(): Boolean {
        return viewModel.pinnedInteractionState!!
    }

    private fun getCurrentDate(): Date {
        return Date()
    }

    private fun getColor(): ColorCategory {
        return viewModel.colorDbCategory!!
    }

    private fun launchColorChange() {
        val view = BottomDialogChangeColorBinding.inflate(layoutInflater)
        view.allNotes.isVisible = false
        view.root.findViewById<ImageView>(viewModel.getColorCategoryItem(viewModel.themeSelectedInteraction.value!!).selectedItem)
            .isVisible = true
        lunchBottomSheet(view.root)
    }
}