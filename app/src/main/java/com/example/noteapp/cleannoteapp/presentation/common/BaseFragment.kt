package com.example.noteapp.cleannoteapp.presentation.common

import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.noteapp.cleannoteapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

open class BaseFragment : Fragment() {

    open fun showNavigationBottom(visible: Boolean) {
        var bottomNav =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.isVisible = visible
    }
}