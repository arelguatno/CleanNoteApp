package com.example.noteapp.cleannoteapp.presentation.notedetail

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
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
    private val mainViewModel: AddUpdateViewModel by activityViewModels()
    private var menuItem: MenuItem? = null

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

        initOnBackPressDispatcher()
        initToolBarBackButton()

        initMenu()
        recordData()


        binding.addTextLayout.noteBody.setOnClickListener {
            onClickNoteBody()
        }

        binding.addTextLayout.noteTitle.setOnClickListener {
            onClickNoteTitle()
        }
    }

    private fun initMenu() {
        menuItem = binding.appBar.menu.findItem(R.id.menu_color_category)
        binding.appBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_color_category -> {
                    view?.hideKeyboard()
                    launchColor()
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
                else -> {}
            }
        }
        mainViewModel.themeSelected.observe(viewLifecycleOwner) {
            setTheme(it)
        }

        mainViewModel.currentDate.observe(viewLifecycleOwner){
            binding.addTextLayout.txtDate.text = it.appMainFormatWithTime()
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
        printLogD(className, colorCategory.name)
        when (colorCategory) {
            ColorCategory.OPTION_ONE -> {
                loadTheme(1)
                binding.main.setThemeOne()
                binding.appBar.setThemeOne()
                menuItem?.icon = getImage(R.color.color_one_primary)
            }
            ColorCategory.OPTION_TWO -> {
                loadTheme(2)
                binding.main.setThemeTwo()
                binding.appBar.setThemeTwo()
                menuItem?.icon = getImage(R.color.color_two_primary)
            }
            ColorCategory.OPTION_THREE -> {
                loadTheme(3)
                binding.main.setThemeThree()
                binding.appBar.setThemeThree()
                menuItem?.icon = getImage(R.color.color_three_primary)
            }
            ColorCategory.OPTION_FOUR -> {
                loadTheme(4)
                binding.main.setThemeFour()
                binding.appBar.setThemeFour()
                menuItem?.icon = getImage(R.color.color_four_primary)
            }
            ColorCategory.OPTION_FIVE -> {
                loadTheme(5)
                binding.main.setThemeFive()
                binding.appBar.setThemeFive()
                menuItem?.icon = getImage(R.color.color_five_primary)
            }
            ColorCategory.OPTION_SIX -> {
                loadTheme(6)
                binding.main.setThemeSix()
                binding.appBar.setThemeSix()
                menuItem?.icon = getImage(R.color.color_six_primary)
            }
            ColorCategory.OPTION_SEVEN -> {
                loadTheme(7)
                binding.main.setThemeSeven()
                binding.appBar.setThemeSeven()
                menuItem?.icon = getImage(R.color.color_seven_primary)
            }
            ColorCategory.OPTION_EIGHT -> {
                loadTheme(8)
                binding.main.setThemeEight()
                binding.appBar.setThemeEight()
                menuItem?.icon = getImage(R.color.color_eight_primary)
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
        return mainViewModel.themeSelected.value!!
    }

    private fun recordData() {
        binding.addTextLayout.noteBody.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                //TODO
            } else {
                printLogD(className, "Lost Focus")
            }
        }
    }

    private fun launchColor() {
        val view = LayoutChangeColorBinding.inflate(layoutInflater)
        executeTheme(view.colorOne, ColorCategory.OPTION_ONE)
        executeTheme(view.colorTwo, ColorCategory.OPTION_TWO)
        executeTheme(view.colorThree, ColorCategory.OPTION_THREE)
        executeTheme(view.colorFour, ColorCategory.OPTION_FOUR)
        executeTheme(view.colorFive, ColorCategory.OPTION_FIVE)
        executeTheme(view.colorSix, ColorCategory.OPTION_SIX)
        executeTheme(view.colorSeven, ColorCategory.OPTION_SEVEN)
        executeTheme(view.colorEight, ColorCategory.OPTION_EIGHT)

        bottomSheetDialog.dismissWithAnimation
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.setContentView(view.root)
        bottomSheetDialog.show()
    }

    private fun executeTheme(view: LinearLayout, cat: ColorCategory) {
        view.setOnClickListener {
            mainViewModel.setThemeState(EditState)
            requireActivity().recreate()
            bottomSheetDialog.dismiss()
            mainViewModel.setThemeSelected(cat)
        }
    }
}