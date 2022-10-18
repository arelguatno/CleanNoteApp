package com.example.noteapp.cleannoteapp.presentation

import android.app.Application
import android.view.Gravity
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import es.dmoral.toasty.Toasty

@HiltAndroidApp
class MyApplication: Application() {

//    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        //Always white theme
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setupToast()
//        initAppComponent()
    }

    private fun setupToast() {
        val toasty = Toasty.Config.getInstance()
        toasty.setGravity(Gravity.TOP, 0, 100)
        toasty.allowQueue(true)
        toasty.apply()
    }

//    open fun initAppComponent(){
//        appComponent = DaggerAppComponent
//            .factory()
//            .create(this)
//    }
}