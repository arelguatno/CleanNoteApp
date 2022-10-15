package com.example.noteapp.cleannoteapp.presentation.common

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.databinding.AddBottomSheetDialogBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog

open class BaseFragment : Fragment() {
    lateinit var bottomSheetDialog: BottomSheetDialog

    companion object {
        val className: String = this.javaClass.simpleName
    }

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
}