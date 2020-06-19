package com.imnstudios.mythoughts.ui.splashScreen

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.imnstudios.mythoughts.R
import com.imnstudios.mythoughts.ui.home.HomeActivity
import com.imnstudios.mythoughts.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {
    private val log = "SplashScreenActivityDebug"
    private lateinit var slideDownAnim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        setupAppTheme()

        //setting up anim
        slideDownAnim =
            AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.slide_down_animation
            )
        app_name_full.animation = slideDownAnim

        Handler().postDelayed({
            if (FirebaseAuth.getInstance().currentUser != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                    overridePendingTransition(
                        R.anim.activity_fade_in_animation,
                        R.anim.activity_fade_out_animation
                    )
                }

            } else {
                Intent(this, LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                    overridePendingTransition(
                        R.anim.activity_fade_in_animation,
                        R.anim.activity_fade_out_animation
                    )
                }

            }

        }, 1000)

    }

    private fun setupAppTheme() {
        val appSettingPrefs: SharedPreferences = getSharedPreferences("AppThemeModePrefs", 0)
        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", true)
        if (!isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}