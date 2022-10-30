package com.example.noteapp.cleannoteapp.util

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ScrollAwareFABBehavior(
    val recyclerView: RecyclerView,
    val floatingActionButton: FloatingActionButton,
    val bottomNavigationView: BottomNavigationView
) {

    fun start() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    if (floatingActionButton.isShown) {
                        floatingActionButton.clearAnimation();
                        floatingActionButton.hide()
                    }
                } else if (dy < 0) {
                    if (!floatingActionButton.isShown) {
                        floatingActionButton.clearAnimation();
                        floatingActionButton.show()
                    }
                }
            }
        })
    }
}