package com.example.noteapp.cleannoteapp.util

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.MaterialFadeThrough

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

//                    floatingActionButton.clearAnimation();
//                    floatingActionButton.animate().translationY(floatingActionButton.height.toFloat()).duration = 150

                    if (bottomNavigationView.isShown) {
//                        bottomNavigationView.clearAnimation();
//                        bottomNavigationView.animate().translationY(bottomNavigationView.height.toFloat()).duration = 150
                      //  bottomNavigationView.isVisible = false
                    }
                } else if (dy < 0) {
                    if (!floatingActionButton.isShown) {
                        floatingActionButton.clearAnimation();
                        floatingActionButton.show()
                    }
//                    floatingActionButton.clearAnimation();
//                    floatingActionButton.animate().translationY(0f).duration = 150
//
//                    bottomNavigationView.clearAnimation();
//                    bottomNavigationView.animate().translationY(0f).duration = 150
                   // bottomNavigationView.isVisible = true

                    if (!bottomNavigationView.isShown) {
                       // bottomNavigationView.isVisible = true
                    }
                }
            }
        })
    }
}