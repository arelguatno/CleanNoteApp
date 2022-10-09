package com.example.noteapp.cleannoteapp.presentation.common

import android.opengl.Visibility
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.noteapp.cleannoteapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

open class BaseFragment : Fragment() {

    open fun showNavigationBottom(visible: Boolean) {
        var bottomNav =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        if(visible){
            bottomNav.visibility = View.VISIBLE
        }else{
            bottomNav.visibility = View.INVISIBLE
        }
    }
}