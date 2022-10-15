package com.example.noteapp.cleannoteapp.presentation.common

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.fragment.app.Fragment
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.databinding.AddBottomSheetDialogBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog

open class BaseFragment : Fragment() {
    lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var sharedPref: SharedPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bottomSheetDialog = BottomSheetDialog(requireContext())
    }

    open fun showNavigationBottom(visible: Boolean) {
        var bottomNav =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        if (visible) {
            bottomNav.visibility = View.VISIBLE
        } else {
            bottomNav.visibility = View.INVISIBLE
        }
    }

    fun loadTheme(uniqueID: Int) {
        sharedPref =
            requireActivity().getSharedPreferences(
                getString(R.string.color_id),
                Context.MODE_PRIVATE
            )!!
        with(sharedPref.edit()) {
            putInt(getString(com.example.noteapp.cleannoteapp.R.string.color_id), uniqueID)
            apply()
        }
        requireActivity().recreate()
    }
}