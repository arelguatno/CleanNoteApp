package com.example.noteapp.cleannoteapp.presentation.common

import android.content.Context
import android.content.SharedPreferences
import android.view.Gravity
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.databinding.AddBottomSheetDialogBinding
import com.example.noteapp.cleannoteapp.models.PreferenceKeys.Companion.THEME_FILTER_PREFERENCE
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.presentation.MyApplication
import com.example.noteapp.cleannoteapp.presentation.notedetail.AddUpdateViewModel
import com.example.noteapp.cleannoteapp.util.extensions.save
import com.example.noteapp.cleannoteapp.util.printLogD
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {
    lateinit var bottomSheetDialog: BottomSheetDialog

    @Inject
    lateinit var sharedPref: SharedPreferences

    @Inject
    lateinit var sharedPrefEditor: SharedPreferences.Editor

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bottomSheetDialog = BottomSheetDialog(requireContext())
    }

    fun saveSelectedTheme(uniqueID: Int) {
        sharedPref.save(getString(R.string.color_id), uniqueID)
    }

    fun lunchBottomSheet(view: View) {
        bottomSheetDialog.dismissWithAnimation
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }
}