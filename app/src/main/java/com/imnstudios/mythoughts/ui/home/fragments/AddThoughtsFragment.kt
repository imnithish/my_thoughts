package com.imnstudios.mythoughts.ui.home.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.imnstudios.mythoughts.R


class AddThoughtsFragment : Fragment() {
    private val TAG = "Debug014589"
//    private val TAG = "AddThoughtsFragmentDebug"

    private lateinit var thoughtInput: EditText
    private lateinit var saveButtonContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, " onCreate AddThoughtsFragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, " onCreateView AddThoughtsFragment")
        val v = inflater.inflate(R.layout.fragment_add_thoughts, container, false)

        thoughtInput = v.findViewById(R.id.thought)
        saveButtonContainer = v.findViewById(R.id.save_btn_container)

        thoughtInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (thoughtInput.length() != 0){
                    saveButtonContainer.visibility = View.VISIBLE
                }else{
                    saveButtonContainer.visibility = View.INVISIBLE
                }
            }

        })

        thoughtInput.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    saveButtonContainer.visibility = View.GONE
                }
                return true
            }

        })


        return v
    }

}