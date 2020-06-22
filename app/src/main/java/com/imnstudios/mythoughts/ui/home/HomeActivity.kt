package com.imnstudios.mythoughts.ui.home

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.imnstudios.mythoughts.R
import com.imnstudios.mythoughts.ui.home.adapters.HomePagerAdapter
import com.imnstudios.mythoughts.ui.home.viewModel.ThoughtsViewModel
import com.imnstudios.mythoughts.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    //    private val TAG = "HomeActivityActivityDebug"
    private val TAG = "Debug014589"
    private lateinit var homePagerAdapter: HomePagerAdapter
    private lateinit var homePager: ViewPager

    private var doubleBackToExitPressedOnce = false

    companion object {
        lateinit var viewModel: ThoughtsViewModel
        lateinit var firestoreDb: FirebaseFirestore

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, " onCreate HomeActivity")

        setContentView(R.layout.activity_home)

        homePager = findViewById(R.id.home_pager)

        //viewModel
        viewModel = ViewModelProvider(this).get(ThoughtsViewModel::class.java)

        //firebase firestore
        firestoreDb = FirebaseFirestore.getInstance()
        val firebaseSettings: FirebaseFirestoreSettings =
            FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()
        firestoreDb.firestoreSettings = firebaseSettings

        //setting up HomePager
        homePagerAdapter = HomePagerAdapter(supportFragmentManager)
        homePager.adapter = homePagerAdapter
        homePager.offscreenPageLimit = 3

        homePager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                changingTabs(position)
            }

        })

        homePager.currentItem = 1
        add_thoughts.setImageResource(R.drawable.add_colored)



        all_thoughts.setOnClickListener {
            homePager.currentItem = 0
            all_thoughts.setImageResource(R.drawable.thoughts_colored)
            add_thoughts.setImageResource(R.drawable.add_black)
            settings.setImageResource(R.drawable.settings_black)
        }

        add_thoughts.setOnClickListener {
            homePager.currentItem = 1
            all_thoughts.setImageResource(R.drawable.thoughts_black)
            add_thoughts.setImageResource(R.drawable.add_colored)
            settings.setImageResource(R.drawable.settings_black)
        }

        settings.setOnClickListener {
            homePager.currentItem = 2
            all_thoughts.setImageResource(R.drawable.thoughts_black)
            add_thoughts.setImageResource(R.drawable.add_black)
            settings.setImageResource(R.drawable.settings_colored)
        }
    }

    private fun changingTabs(position: Int) {
        if (position == 0) {
            homePager.currentItem = 0
            all_thoughts.setImageResource(R.drawable.thoughts_colored)
            add_thoughts.setImageResource(R.drawable.add_black)
            settings.setImageResource(R.drawable.settings_black)
            hideKeyboard()
        }
        if (position == 1) {
            homePager.currentItem = 1
            all_thoughts.setImageResource(R.drawable.thoughts_black)
            add_thoughts.setImageResource(R.drawable.add_colored)
            settings.setImageResource(R.drawable.settings_black)
            hideKeyboard()
        }
        if (position == 2) {
            homePager.currentItem = 2
            all_thoughts.setImageResource(R.drawable.thoughts_black)
            add_thoughts.setImageResource(R.drawable.add_black)
            settings.setImageResource(R.drawable.settings_colored)
            hideKeyboard()
        }
    }

    override fun onBackPressed() {
        val dialog = Dialog(this)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.logout_dialog)
        dialog.setCancelable(true)
        dialog.show()
        val prompt = dialog.findViewById<TextView>(R.id.log_out_prompt)
        val exit = dialog.findViewById<Button>(R.id.log_out_confirm_btn)
        val cancel = dialog.findViewById<Button>(R.id.cancel_btn)
        prompt.text = getString(R.string.exit_prompt)
        exit.text = getString(R.string.exit)
        exit.setOnClickListener {
            dialog.dismiss()
            super.onBackPressed()
        }
        cancel.setOnClickListener {
            dialog.dismiss()
        }

    }

}