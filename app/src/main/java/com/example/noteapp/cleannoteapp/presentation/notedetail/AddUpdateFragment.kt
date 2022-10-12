package com.example.noteapp.cleannoteapp.presentation.notedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.noteapp.cleannoteapp.databinding.FragmentAddUpdateBinding
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.presentation.common.BaseFragment
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionState.*
import com.example.noteapp.cleannoteapp.room_database.note_table.Dates
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteModel
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteViewModel
import com.example.noteapp.cleannoteapp.util.extensions.disableContentInteraction
import com.example.noteapp.cleannoteapp.util.extensions.enableContentInteraction
import com.example.noteapp.cleannoteapp.util.extensions.hideKeyboard
import com.example.noteapp.cleannoteapp.util.extensions.showKeyboard
import com.example.noteapp.cleannoteapp.util.printLogD
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddUpdateFragment : BaseFragment() {
    private lateinit var binding: FragmentAddUpdateBinding
    private val crudViewModel: NoteViewModel by viewModels()
    private val mainViewModel: AddUpdateViewModel by viewModels()
    private val args: AddUpdateFragmentArgs by navArgs()
    private val className = this.javaClass.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddUpdateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDefaultState()
        subscribeObservers()
        binding.addTextLayout.noteBody.setOnClickListener {
            onClick_noteBody()
        }

        backButtonOnClick()

    }

    private fun setDefaultState() {
        if (args.note == null) {
            mainViewModel.setNoteInteractionBodyState(DefaultState)
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
            }
        }
    }

    fun onClick_noteBody() {
        if (!mainViewModel.isEditingBody()) {
            mainViewModel.setNoteInteractionBodyState(EditState)
        }
    }

    private fun backButtonOnClick() {
        binding.appBar.setNavigationOnClickListener {
            it.hideKeyboard()
            saveRecord()
            findNavController().navigateUp()
        }
    }

    private fun saveRecord() {
        //if(getNoteTitle().isNotEmpty() && getNoteBody().isNotEmpty()){
            val newData = NoteModel(
                header = getNoteTitle(),
                body = getNoteBody(),
                dates = Dates(dateCreated = Date(), dateModified = Date()),
                category = ColorCategory.OPTION_FIVE
            )
            crudViewModel.insertRecord(newData)
            printLogD(className, "onStart")
       // }
    }

    private fun getNoteTitle(): String {
        return binding.addTextLayout.noteTitle.text.toString()
    }

    private fun getNoteBody(): String {
        return binding.addTextLayout.noteBody.text.toString()
    }

}