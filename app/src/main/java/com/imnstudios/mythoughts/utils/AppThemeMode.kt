package com.imnstudios.mythoughts.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class AppThemeMode(private val isNightModeOn: Boolean, context: Context) {

    private val appSettingPrefs: SharedPreferences =
        context.getSharedPreferences("AppThemeModePrefs", 0)
    private val sharedPrefsEdit: SharedPreferences.Editor = appSettingPrefs.edit()

    fun setTheme() {
        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            sharedPrefsEdit.putBoolean("NightMode", true)
            sharedPrefsEdit.apply()
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            sharedPrefsEdit.putBoolean("NightMode", false)
            sharedPrefsEdit.apply()
        }

    }
}