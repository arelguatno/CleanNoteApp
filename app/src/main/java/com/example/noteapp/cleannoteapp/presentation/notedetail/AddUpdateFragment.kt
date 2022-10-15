package com.example.noteapp.cleannoteapp.presentation.notedetail

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.databinding.FragmentAddUpdateBinding
import com.example.noteapp.cleannoteapp.databinding.LayoutChangeColorBinding
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.presentation.common.BaseFragment
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionState.DefaultState
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionState.EditState
import com.example.noteapp.cleannoteapp.room_database.note_table.Dates
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteModel
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteViewModel
import com.example.noteapp.cleannoteapp.util.extensions.*
import com.example.noteapp.cleannoteapp.util.printLogD
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class AddUpdateFragment : BaseFragment() {
    private lateinit var binding: FragmentAddUpdateBinding
    private val crudViewModel: NoteViewModel by viewModels()
    private val mainViewModel: AddUpdateViewModel by viewModels()

    //private val args: AddUpdateFragmentArgs by navArgs()
    private val className = this.javaClass.simpleName
    private lateinit var activityMain: AddUpdateActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddUpdateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityMain =
            context as AddUpdateActivity // so we can read intent data from activity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDefaultState()
        subscribeObservers()
        setupOnBackPressDispatcher()
        toolBarBackButton()
        recordData()
        menu()

        binding.addTextLayout.noteBody.setOnClickListener {
            onClickNoteBody()
        }

        binding.addTextLayout.noteTitle.setOnClickListener {
            onClickNoteTitle()
        }
    }

    private fun menu() {
        binding.appBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_color_category -> {
                    view?.hideKeyboard()
                    launchColor(it)
                }
            }
            true
        }
    }
    private fun onClickNoteTitle() {
        if (!mainViewModel.isEditingTitle()) {
            mainViewModel.setNoteInteractionTitleState(EditState)
        }
    }

    private fun onClickNoteBody() {
        if (!mainViewModel.isEditingBody()) {
            mainViewModel.setNoteInteractionBodyState(EditState)
        }
    }

    private fun setDefaultState() {
        val newNew = activityMain.intent.serializable<NoteModel>(
            AddUpdateActivity.DETAIL_FRAGMENT
        )

        if (newNew == null) {
            mainViewModel.setNoteInteractionBodyState(EditState)
        } else {
            mainViewModel.setNoteInteractionBodyState(DefaultState)
        }
    }

    private fun subscribeObservers() {
        mainViewModel.noteBodyInteractionState.observe(viewLifecycleOwner) {
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

        mainViewModel.noteTitleInteractionState.observe(viewLifecycleOwner) {
            when (it) {

                is EditState -> {
                    binding.addTextLayout.noteTitle.enableContentInteraction()
                    binding.addTextLayout.noteTitle.showKeyboard()
                }

                is DefaultState -> {
                    binding.addTextLayout.noteTitle.disableContentInteraction()
                }
            }
        }

        mainViewModel.colorSelected.observe(viewLifecycleOwner) {
            setTheme(it)
        }
    }

    private fun setTheme(colorCategory: ColorCategory) {
        printLogD(className, colorCategory.name)
        when (colorCategory) {
            ColorCategory.OPTION_ONE -> {
                loadTheme(1)
                binding.main.setThemeOne()
                binding.appBar.setThemeOne()
            }
            ColorCategory.OPTION_TWO -> {
                loadTheme(2)
                binding.main.setThemeTwo()
                binding.appBar.setThemeTwo()
            }
            ColorCategory.OPTION_THREE -> {
                loadTheme(3)
                binding.main.setThemeThree()
                binding.appBar.setThemeThree()
            }
            ColorCategory.OPTION_FOUR -> {
                loadTheme(4)
                binding.main.setThemeFour()
                binding.appBar.setThemeFour()
            }
            ColorCategory.OPTION_FIVE -> {
                loadTheme(5)
                binding.main.setThemeFive()
                binding.appBar.setThemeFive()
            }
            ColorCategory.OPTION_SIX -> {
                loadTheme(6)
                binding.main.setThemeSix()
                binding.appBar.setThemeSix()
            }
            ColorCategory.OPTION_SEVEN -> {
                loadTheme(7)
                binding.main.setThemeSeven()
                binding.appBar.setThemeSeven()
            }
            ColorCategory.OPTION_EIGHT -> {
                loadTheme(8)
                binding.main.setThemeEight()
                binding.appBar.setThemeEight()
            }
            else -> {}
        }
    }

    fun onBackPressed() {
        view?.hideKeyboard()
        if (mainViewModel.checkEditState()) {
            saveRecord()
            mainViewModel.exitEditState()
            requireActivity().finish()
        } else {
            requireActivity().finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loadTheme(1) //TODO setBackToDefault
    }

    private fun toolBarBackButton() {
        binding.appBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupOnBackPressDispatcher() {
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

        val newData = NoteModel(
            header = getNoteTitle(),
            body = getNoteBody(),
            dates = Dates(dateCreated = Date(), dateModified = Date()),
            category = getColor()
        )
        crudViewModel.insertRecord(newData)
        printLogD(className, "Record Saved")
    }

    private fun getNoteTitle(): String {
        return binding.addTextLayout.noteTitle.text.toString()
    }

    private fun getNoteBody(): String {
        return binding.addTextLayout.noteBody.text.toString()
    }

    private fun getColor(): ColorCategory {
        return mainViewModel.colorSelected.value!!
    }

    private fun recordData() {
        binding.addTextLayout.noteBody.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {

            } else {
                printLogD(className, "Lost Focus")
            }
        }
    }

    private fun launchColor(menuItem: MenuItem) {
        val view = LayoutChangeColorBinding.inflate(layoutInflater)
        executeTheme(view.colorOne, ColorCategory.OPTION_ONE,menuItem)
        executeTheme(view.colorTwo, ColorCategory.OPTION_TWO, menuItem)
        executeTheme(view.colorThree, ColorCategory.OPTION_THREE, menuItem)
        executeTheme(view.colorFour, ColorCategory.OPTION_FOUR, menuItem)
        executeTheme(view.colorFive, ColorCategory.OPTION_FIVE, menuItem)
        executeTheme(view.colorSix, ColorCategory.OPTION_SIX, menuItem)
        executeTheme(view.colorSeven, ColorCategory.OPTION_SEVEN, menuItem)
        executeTheme(view.colorEight, ColorCategory.OPTION_EIGHT, menuItem)

        bottomSheetDialog.dismissWithAnimation
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.setContentView(view.root)
        bottomSheetDialog.show()
    }

    private fun executeTheme(view: LinearLayout, cat: ColorCategory, menuItem: MenuItem) {
        view.setOnClickListener {
            requireActivity().recreate()
            bottomSheetDialog.dismiss()
            mainViewModel.setColorCategory(cat)
          //  menuItem.icon?.setColorTint(cat)
        }
    }
}