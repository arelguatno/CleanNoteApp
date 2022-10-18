package com.example.noteapp.cleannoteapp.presentation.settings

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.databinding.LayoutChangeColorBinding
import com.example.noteapp.cleannoteapp.databinding.SettingsFeedbackWidgetBinding
import com.example.noteapp.cleannoteapp.databinding.SettingsSettingsBinding
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.presentation.common.BaseFragment
import com.example.noteapp.cleannoteapp.presentation.data_binding.BindingAdapters
import com.example.noteapp.cleannoteapp.presentation.data_binding.ColorCategoryBinding
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionState
import com.example.noteapp.cleannoteapp.presentation.notelist.ListViewModel
import com.example.noteapp.cleannoteapp.util.PreferenceKeys.Companion.SETTINGS_DEFAULT_COLOR
import com.example.noteapp.cleannoteapp.util.extensions.*
import com.example.noteapp.cleannoteapp.util.printLogD

class SettingsFragment : BaseFragment() {
    private lateinit var binding: SettingsSettingsBinding
    private val className = this.javaClass.simpleName

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingsSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenu()
        viewModel.loadDefaultColor()
        subscribeObservers()

        binding.settingsGeneral.defaultColor.setOnClickListener {
            launchDefaultColor()
        }
    }

    private fun subscribeObservers() {
        viewModel.themeSelectedInteraction.observe(viewLifecycleOwner) {
            when (it) {
                ColorCategory.OPTION_ONE -> {
                    binding.settingsGeneral.imageColor.setImageDrawable(getImage(R.color.color_one_primary))
                }
                ColorCategory.OPTION_TWO -> {
                    binding.settingsGeneral.imageColor.setImageDrawable(getImage(R.color.color_two_primary))
                }
                ColorCategory.OPTION_THREE -> {
                    binding.settingsGeneral.imageColor.setImageDrawable(getImage(R.color.color_three_primary))
                }
                ColorCategory.OPTION_FOUR -> {
                    binding.settingsGeneral.imageColor.setImageDrawable(getImage(R.color.color_four_primary))
                }
                ColorCategory.OPTION_FIVE -> {
                    binding.settingsGeneral.imageColor.setImageDrawable(getImage(R.color.color_five_primary))
                }
                ColorCategory.OPTION_SIX -> {
                    binding.settingsGeneral.imageColor.setImageDrawable(getImage(R.color.color_six_primary))
                }
                ColorCategory.OPTION_SEVEN -> {
                    binding.settingsGeneral.imageColor.setImageDrawable(getImage(R.color.color_seven_primary))
                }
                ColorCategory.OPTION_EIGHT -> {
                    binding.settingsGeneral.imageColor.setImageDrawable(getImage(R.color.color_eight_primary))
                }
                else -> {}
            }
        }
    }

    override fun onStart() {
        super.onStart()
        initColorSelectedListener()

    }

    private fun launchDefaultColor() {
        val view = LayoutChangeColorBinding.inflate(layoutInflater)
        view.allNotes.isVisible = false
        lunchBottomSheet(view.root)
    }

    private fun initColorSelectedListener() {
        BindingAdapters.setItemOnClickListener(object : ColorCategoryBinding {
            override fun userSelectedColor(colorBinding: ColorCategory) {
                viewModel.setThemeSelected(colorBinding)
                sharedPref.save(SETTINGS_DEFAULT_COLOR, colorBinding.toString())
                bottomSheetDialog.dismiss()
            }
        })
    }

    private fun initMenu() {
        binding.appBar.setNavigationOnClickListener {
            it.findNavController().popBackStack()
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
}