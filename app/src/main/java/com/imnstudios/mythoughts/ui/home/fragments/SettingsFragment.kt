package com.imnstudios.mythoughts.ui.home.fragments

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.auth.FirebaseAuth
import com.imnstudios.mythoughts.R
import com.imnstudios.mythoughts.ui.login.LoginActivity
import com.imnstudios.mythoughts.ui.splashScreen.SplashScreenActivity
import com.imnstudios.mythoughts.utils.AppThemeMode
import com.imnstudios.mythoughts.utils.hide
import fr.castorflex.android.circularprogressbar.CircularProgressBar


class SettingsFragment : Fragment() {

    private val TAG = "Debug014589"
//    private val TAG = "SettingsFragmentDebug"

    companion object {
        lateinit var auth: FirebaseAuth
    }

    private lateinit var user: TextView
    private lateinit var logOut: Button
    private lateinit var about: Button
    private lateinit var privacyPolicy: Button
    private lateinit var modeSwitch: SwitchMaterial

    var isNightModeOn: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, " onCreate SettingsFragment")

        auth = FirebaseAuth.getInstance()

        val appSettingPrefs: SharedPreferences =
            activity!!.getSharedPreferences("AppThemeModePrefs", 0)
        isNightModeOn = appSettingPrefs.getBoolean("NightMode", true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, " onCreateView SettingsFragment")
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_settings, container, false)

        //init views
        user = v.findViewById(R.id.user)
        logOut = v.findViewById(R.id.log_out_btn)
        about = v.findViewById(R.id.about_btn)
        privacyPolicy = v.findViewById(R.id.privacy_policy_btn)
        modeSwitch = v.findViewById(R.id.mode_switch)

        user.append(" ${auth.currentUser?.displayName}")

        logOut.setOnClickListener {
            val dialog = Dialog(activity!!)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.logout_dialog)
            dialog.setCancelable(true)
            dialog.show()
            val logOut = dialog.findViewById<Button>(R.id.log_out_confirm_btn)
            val cancel = dialog.findViewById<Button>(R.id.cancel_btn)
            logOut.setOnClickListener {
                auth.signOut()
                dialog.dismiss()
                Intent(activity, SplashScreenActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                    activity!!.overridePendingTransition(
                        R.anim.activity_fade_in_animation,
                        R.anim.activity_fade_out_animation
                    )
                }
            }
            cancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        about.setOnClickListener {
            val dialog = Dialog(activity!!)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.about_dialog)
            dialog.setCancelable(true)
            dialog.show()
        }

        privacyPolicy.setOnClickListener {
            val dialog = Dialog(activity!!)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.privacy_policy_dialog)
            dialog.setCancelable(true)
            dialog.show()
            val webView = dialog.findViewById<WebView>(R.id.web_view)
            val progressBar = dialog.findViewById<CircularProgressBar>(R.id.progress_bar)
            webView.loadUrl("https://imnithish.github.io/hellorc-1/")
            val handler = Handler()
            handler.postDelayed({
                progressBar.hide()
                webView.visibility = View.VISIBLE
            }, 3000)
        }

        //setting up the mode switch
        modeSwitch.isChecked = isNightModeOn
        modeSwitch.setOnCheckedChangeListener { _: CompoundButton, isNightModeOnFlag: Boolean ->
            if (isNightModeOnFlag) {
                val appTheme = AppThemeMode(true, activity!!)
                appTheme.setTheme()
            } else {
                val appTheme = AppThemeMode(false, activity!!)
                appTheme.setTheme()
            }
        }

        return v
    }

}