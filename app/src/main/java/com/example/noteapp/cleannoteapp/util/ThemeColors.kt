package com.example.noteapp.cleannoteapp.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.annotation.ColorInt
import com.example.noteapp.cleannoteapp.R


class ThemeColors {
    private val NAME = "ThemeColors"
    private val KEY = "color"

    @ColorInt
    var color = 0

    open fun ThemeColors(context: Context) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        val stringColor = sharedPreferences.getString(KEY, "004bff")
        color = Color.parseColor("#$stringColor")
        if (isLightActionBar()) context.setTheme(R.style.Theme_CleanNoteApp)
        context.setTheme(
            context.getResources()
                .getIdentifier("T_$stringColor", "style", context.packageName)
        )
    }

    open fun setNewThemeColor(activity: Activity, red: Int, green: Int, blue: Int) {
        var red = red
        var green = green
        var blue = blue
        val colorStep = 15
        red = Math.round((red / colorStep).toFloat()) * colorStep
        green = Math.round((green / colorStep).toFloat()) * colorStep
        blue = Math.round((blue / colorStep).toFloat()) * colorStep
        val stringColor = Integer.toHexString(Color.rgb(red, green, blue)).substring(2)
        val editor = activity.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit()
        editor.putString(KEY, stringColor)
        editor.apply()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) activity.recreate() else {
            val i = activity.packageManager.getLaunchIntentForPackage(activity.packageName)
            i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            activity.startActivity(i)
        }
    }

    private fun isLightActionBar(): Boolean { // Checking if title text color will be black
        val rgb: Int = (Color.red(color) + Color.green(color) + Color.blue(color)) / 3
        return rgb > 210
    }
}