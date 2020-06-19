package com.imnstudios.mythoughts.ui.home.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imnstudios.mythoughts.R


class AddThoughtsFragment : Fragment() {

    private val TAG = "Debug014589"
//    private val TAG = "AddThoughtsFragmentDebug"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, " onCreate AddThoughtsFragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, " onCreateView AddThoughtsFragment")
        return inflater.inflate(R.layout.fragment_add_thoughts, container, false)
    }

}