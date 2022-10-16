package com.example.noteapp.cleannoteapp.presentation.common

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.databinding.AddBottomSheetDialogBinding
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.presentation.notedetail.AddUpdateViewModel
import com.example.noteapp.cleannoteapp.util.extensions.save
import com.example.noteapp.cleannoteapp.util.printLogD
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment() {
    lateinit var bottomSheetDialog: BottomSheetDialog
    lateinit var sharedPref: SharedPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bottomSheetDialog = BottomSheetDialog(requireContext())
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)!!
    }

    fun saveSelectedTheme(uniqueID: Int) {
        sharedPref.save(getString(R.string.color_id), uniqueID)
    }
}