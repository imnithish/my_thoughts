package com.imnstudios.mythoughts

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.imnstudios.mythoughts.utils.AppThemeMode
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //setting up AppThemeMode starts here
        val appSettingPrefs: SharedPreferences = getSharedPreferences("AppThemeModePrefs", 0)
        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", false)
        if (!isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        mode_switch.isChecked = isNightModeOn
        mode_switch.setOnCheckedChangeListener { _: CompoundButton, isNightModeOnFlag: Boolean ->
            if (isNightModeOnFlag) {
                val appTheme = AppThemeMode(true, applicationContext)
                appTheme.setTheme()
            } else {
                val appTheme = AppThemeMode(false, applicationContext)
                appTheme.setTheme()
            }
        }
        //setting up AppThemeMode ends here
    }
}