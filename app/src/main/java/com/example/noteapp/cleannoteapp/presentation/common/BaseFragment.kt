package com.example.noteapp.cleannoteapp.presentation.common

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {
    lateinit var bottomSheetDialog: BottomSheetDialog

    @Inject
    lateinit var sharedPref: SharedPreferences

    @Inject
    lateinit var sharedPrefEditor: SharedPreferences.Editor

    val crudViewModel: NoteViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.window?.setWindowAnimations(R.style.DialogAnimation)
    }

    fun lunchBottomSheet(view: View) {
        bottomSheetDialog.dismissWithAnimation
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }
}