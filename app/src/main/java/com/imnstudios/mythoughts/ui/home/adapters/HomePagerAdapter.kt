package com.imnstudios.mythoughts.ui.home.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.imnstudios.mythoughts.ui.home.fragments.AddThoughtsFragment
import com.imnstudios.mythoughts.ui.home.fragments.AllThoughtsFragment
import com.imnstudios.mythoughts.ui.home.fragments.SettingsFragment

internal class HomePagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(
    fragmentManager,
    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> {
                AllThoughtsFragment()
            }
            1 -> {
                AddThoughtsFragment()
            }
            2 -> {
                SettingsFragment()
            }
            else -> AddThoughtsFragment()

        }
    }

    override fun getCount(): Int {
        return 3
    }

}