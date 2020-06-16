package com.imnstudios.mythoughts

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.imnstudios.mythoughts.utils.AppThemeMode
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var slideDownAnim: Animation
    lateinit var fadeInAnim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //setting up animation starts here
        slideDownAnim =
            AnimationUtils.loadAnimation(applicationContext, R.anim.slide_down_animation)
        fadeInAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in_animation)
        heading.animation = slideDownAnim
        Handler().postDelayed({
//            mode_switch.visibility = View.VISIBLE
//            mode_switch.animation = fadeInAnim
            log_in.visibility = View.VISIBLE
            log_in.animation = fadeInAnim
        }, 1000)
        //setting up animation ends here

        //setting up AppThemeMode starts here
        val appSettingPrefs: SharedPreferences = getSharedPreferences("AppThemeModePrefs", 0)
        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", false)
        if (!isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
//
//        mode_switch.isChecked = isNightModeOn
//        mode_switch.setOnCheckedChangeListener { _: CompoundButton, isNightModeOnFlag: Boolean ->
//            if (isNightModeOnFlag) {
//                val appTheme = AppThemeMode(true, applicationContext)
//                appTheme.setTheme()
//            } else {
//                val appTheme = AppThemeMode(false, applicationContext)
//                appTheme.setTheme()
//            }
//        }
        //setting up AppThemeMode ends here
    }
}