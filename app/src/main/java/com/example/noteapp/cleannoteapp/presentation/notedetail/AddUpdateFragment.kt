package com.example.noteapp.cleannoteapp.presentation.notedetail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.databinding.FragmentAddUpdateBinding
import com.example.noteapp.cleannoteapp.databinding.LayoutChangeColorBinding
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.presentation.common.BaseFragment
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionState.*
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
    private val args: AddUpdateFragmentArgs by navArgs()
    private val className = this.javaClass.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddUpdateBinding.inflate(layoutInflater)
        return binding.root
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
                    launchSortBy()
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
        if (args.note == null) {
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
        when (colorCategory) {
            ColorCategory.OPTION_ONE -> {
                binding.main.setThemeOne()
                binding.appBar.setThemeOne()
            }
            ColorCategory.OPTION_TWO -> {
                binding.main.setThemeTwo()
                binding.appBar.setThemeTwo()
            }
            ColorCategory.OPTION_THREE -> {
                binding.main.setThemeThree()
                binding.appBar.setThemeThree()
            }
            ColorCategory.OPTION_FOUR -> {
                binding.main.setThemeFour()
                binding.appBar.setThemeFour()
            }
            ColorCategory.OPTION_FIVE -> {
                binding.main.setThemeFive()
                binding.appBar.setThemeFive()
            }
            ColorCategory.OPTION_SIX -> {
                binding.main.setThemeSix()
                binding.appBar.setThemeSix()
            }
            ColorCategory.OPTION_SEVEN -> {
                binding.main.setThemeSeven()
                binding.appBar.setThemeSeven()
            }
            ColorCategory.OPTION_EIGHT -> {
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
            findNavController().popBackStack()
        } else {
            findNavController().popBackStack()
        }
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
        if (getNoteTitle().isNullOrEmpty() && getNoteBody().isNullOrEmpty()) {
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

    private fun launchSortBy() {
        val view = LayoutChangeColorBinding.inflate(layoutInflater)

        view.colorOne.setOnClickListener {
            mainViewModel.setColorCategory(ColorCategory.OPTION_ONE)
            bottomSheetDialog.dismiss()
        }

        view.colorTwo.setOnClickListener {
            mainViewModel.setColorCategory(ColorCategory.OPTION_TWO)
            bottomSheetDialog.dismiss()
        }

        view.colorThree.setOnClickListener {
            mainViewModel.setColorCategory(ColorCategory.OPTION_THREE)
            bottomSheetDialog.dismiss()
        }

        view.colorFour.setOnClickListener {
            mainViewModel.setColorCategory(ColorCategory.OPTION_FOUR)
            bottomSheetDialog.dismiss()
        }

        view.colorFive.setOnClickListener {
            mainViewModel.setColorCategory(ColorCategory.OPTION_FIVE)
            bottomSheetDialog.dismiss()
        }

        view.colorSix.setOnClickListener {
            mainViewModel.setColorCategory(ColorCategory.OPTION_SIX)
            bottomSheetDialog.dismiss()
        }

        view.colorSeven.setOnClickListener {
            mainViewModel.setColorCategory(ColorCategory.OPTION_SEVEN)
            bottomSheetDialog.dismiss()
        }

        view.colorEight.setOnClickListener {
            mainViewModel.setColorCategory(ColorCategory.OPTION_EIGHT)
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.dismissWithAnimation
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.setContentView(view.root)
        bottomSheetDialog.show()
    }
}