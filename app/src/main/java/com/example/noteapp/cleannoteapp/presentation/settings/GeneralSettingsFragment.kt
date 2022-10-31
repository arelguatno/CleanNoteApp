package com.example.noteapp.cleannoteapp.presentation.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.databinding.FragmentSettingsBinding
import com.example.noteapp.cleannoteapp.presentation.common.BaseFragment

class GeneralSettingsFragment : BaseFragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsWidget.settings.setOnClickListener {
            it.findNavController().navigate(R.id.action_settingsFragment_to_settingsFragment2)
        }

        initListener()
    }

    private fun initListener() {
        crudViewModel.fetchBinAndArchiveCounting.observe(viewLifecycleOwner) {
            if (it.size == 1) {
                binding.settingsAppName.bin.text = it[0].reporting!!.bin
                binding.settingsAppName.archive.text = it[0].reporting!!.archive
            }
        }
    }
}