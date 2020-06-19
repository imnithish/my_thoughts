package com.imnstudios.mythoughts.ui.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imnstudios.mythoughts.R


class AddThoughtsFragment : Fragment() {

    private val log = "AddThoughtsFragmentDebug"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_thoughts, container, false)
    }

}