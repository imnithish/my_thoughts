package com.imnstudios.mythoughts.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.firebase.auth.FirebaseAuth
import com.imnstudios.mythoughts.R
import com.imnstudios.mythoughts.ui.home.adapters.HomePagerAdapter
import com.imnstudios.mythoughts.ui.login.LoginActivity
import com.imnstudios.mythoughts.utils.toast
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var homePagerAdapter: HomePagerAdapter
    private lateinit var homePager: ViewPager

    companion object {
        lateinit var auth: FirebaseAuth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        homePager = findViewById(R.id.home_pager)

        //initialise the FirebaseAuth object
        auth = FirebaseAuth.getInstance()

//        log_out.setOnClickListener {
//            auth.signOut()
//            startActivity(Intent(this, LoginActivity::class.java))
//        }

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
            toast("0")

        }
        if (position == 1) {
            toast("1")

        }
        if (position == 2) {
            toast("2")

        }
    }
}