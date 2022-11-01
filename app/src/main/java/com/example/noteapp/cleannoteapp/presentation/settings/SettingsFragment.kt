package com.example.noteapp.cleannoteapp.presentation.settings

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.noteapp.cleannoteapp.BuildConfig
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.databinding.BottomDialogChangeColorBinding
import com.example.noteapp.cleannoteapp.databinding.SettingsSettingsBinding
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.presentation.common.BaseFragment
import com.example.noteapp.cleannoteapp.presentation.data_binding.BindingAdapters
import com.example.noteapp.cleannoteapp.presentation.data_binding.ColorCategoryBinding

class SettingsFragment : BaseFragment(), ColorCategoryBinding {
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
            launchDefaultColorBottomSheet()
        }

        binding.versionText.text = "version ${BuildConfig.VERSION_NAME}-${BuildConfig.BUILD_TYPE}"
    }

    private fun subscribeObservers() {
        viewModel.themeSelectedInteraction.observe(viewLifecycleOwner) {
            binding.settingsGeneral.imageColor.setImageDrawable(
                getImage(viewModel.getColorCategoryItem(it).primaryColor)
            )
        }
    }

    override fun onStart() {
        super.onStart()
        initColorSelectedListener()
    }

    private fun launchDefaultColorBottomSheet() {
        val view = BottomDialogChangeColorBinding.inflate(layoutInflater)
        view.allNotes.isVisible = false
        view.root.findViewById<ImageView>(viewModel.getColorCategoryItem(viewModel.themeSelectedInteraction.value!!).selectedItem)
            .isVisible = true
        lunchBottomSheet(view.root)
    }

    private fun initColorSelectedListener() {
        BindingAdapters.setItemOnClickListener(object : ColorCategoryBinding {
            override fun userSelectedColor(colorBinding: ColorCategory) {
                viewModel.setThemeSelected(colorBinding)
                viewModel.saveDefaultColor(colorBinding)
                bottomSheetDialog.dismiss()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        BindingAdapters.setItemOnClickListener(null)
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

    override fun userSelectedColor(colorBinding: ColorCategory) {
        TODO("Not yet implemented")
    }
}