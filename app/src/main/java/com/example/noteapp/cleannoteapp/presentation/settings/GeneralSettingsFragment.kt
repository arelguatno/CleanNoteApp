package com.example.noteapp.cleannoteapp.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.databinding.FragmentSettingsBinding
import com.example.noteapp.cleannoteapp.presentation.common.BaseFragment
import com.example.noteapp.cleannoteapp.presentation.notelist.ListViewModel
import com.example.noteapp.cleannoteapp.presentation.notelist.state.NoteListScreenState
import com.example.noteapp.cleannoteapp.presentation.notelist.state.NoteListScreenState.*
import com.example.noteapp.cleannoteapp.util.PreferenceKeys.Companion.BIN_ARCHIVE_VIEW
import com.example.noteapp.cleannoteapp.util.extensions.serializable
import com.google.android.material.bottomnavigation.BottomNavigationView

class GeneralSettingsFragment : BaseFragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!


    private val viewModel: ListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsWidget.settings.setOnClickListener {
            it.findNavController().navigate(R.id.action_settingsFragment_to_settingsFragment2)
        }

        binding.settingsAppName.recycleArchive.setOnClickListener {
            it.findNavController().navigate(R.id.action_settingsFragment_to_archiveFragment)
        }

        binding.settingsAppName.recycleBin.setOnClickListener {
            it.findNavController().navigate(R.id.action_settingsFragment_to_binFragment)
        }

        initListener()
        setUpFragmentNavResultListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpFragmentNavResultListener() {
        setFragmentResultListener(BIN_ARCHIVE_VIEW) { _, bundle ->
            val result = bundle.serializable<Boolean>(BIN_ARCHIVE_VIEW)
            if (result != null && result) {
                val navBottomView =
                    requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
                navBottomView.isVisible = true
                viewModel.setListScreenState(MainListView)
                viewModel.clearSelectedNotes()
            }
        }
    }

    private fun initListener() {
        crudViewModel.fetchBinAndArchiveCounting().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.settingsAppName.bin.text = it[0].reporting_bin ?: "0"
                binding.settingsAppName.archive.text = it[0].reporting_archive ?: "0"
            }
        }
    }
}