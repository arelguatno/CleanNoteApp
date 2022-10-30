package com.example.noteapp.cleannoteapp.presentation.notedetail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.models.ViewStateModel
import com.example.noteapp.cleannoteapp.presentation.common.BaseActivity
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionState.DefaultState
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionState.EditState
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.ViewState
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.ViewState.*
import com.example.noteapp.cleannoteapp.util.PreferenceKeys.Companion.SETTINGS_DEFAULT_COLOR
import com.example.noteapp.cleannoteapp.util.PreferenceKeys.Companion.USER_DYNAMIC_THEME_PREFERENCE
import com.example.noteapp.cleannoteapp.util.extensions.serializable
import com.example.noteapp.cleannoteapp.util.printLogD
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddUpdateActivity : BaseActivity() {
    private val viewModel: AddUpdateViewModel by viewModels()

    companion object {
        const val DETAIL_FRAGMENT =
            "com.example.noteapp.cleannoteapp.presentation.notedetail.detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadTheme(intent.serializable(DETAIL_FRAGMENT))

        setContentView(R.layout.activity_add_update)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun loadTheme(state: ViewStateModel?) {
        when (mainViewModel.setThemeState.value) {
            EditState -> {
                setTheme(viewModel.getThemeColorForActivity(USER_DYNAMIC_THEME_PREFERENCE))
            }
            DefaultState -> {
                if (state != null) {
                    when (state.state) {
                        is EditItem -> {
                            setTheme(viewModel.getColorCategoryItem(state.noteModel?.category!!).theme)
                        }
                        is NewItem -> {
                            setTheme(viewModel.getThemeColorForActivity(SETTINGS_DEFAULT_COLOR))
                        }
                    }
                }

            }
            else -> {}
        }
    }
}