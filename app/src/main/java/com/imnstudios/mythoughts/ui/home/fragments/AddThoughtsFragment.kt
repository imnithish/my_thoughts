package com.imnstudios.mythoughts.ui.home.fragments

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.imnstudios.mythoughts.R
import com.imnstudios.mythoughts.data.network.firebase.FirestoreInstance
import com.imnstudios.mythoughts.ui.splashScreen.SplashScreenActivity
import com.imnstudios.mythoughts.utils.ThoughtColorPicker
import kotlinx.android.synthetic.main.fragment_add_thoughts.*
import kotlinx.android.synthetic.main.fragment_add_thoughts.view.*


class AddThoughtsFragment : Fragment(), View.OnClickListener {
    private val TAG = "Debug014589"
//    private val TAG = "AddThoughtsFragmentDebug"

    private lateinit var thoughtInput: EditText
    private lateinit var saveButtonContainer: LinearLayout
    private lateinit var thoughtsContainerCard: CardView


    //card colors
    private lateinit var colorOne: ImageButton
    private lateinit var colorTwo: ImageButton
    private lateinit var colorThree: ImageButton
    private lateinit var colorFour: ImageButton
    private lateinit var colorFive: ImageButton
    private lateinit var colorSix: ImageButton
    private lateinit var colorSeven: ImageButton
    private lateinit var colorEight: ImageButton

    private lateinit var databaseReference: DatabaseReference
    private lateinit var userUid: String

    var cardColor: String? = null
    var colorId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, " onCreate AddThoughtsFragment")

        userUid = SplashScreenActivity.auth.currentUser?.uid.toString()
        databaseReference = FirestoreInstance.getDatabase(userUid)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, " onCreateView AddThoughtsFragment")
        val v = inflater.inflate(R.layout.fragment_add_thoughts, container, false)

        thoughtInput = v.findViewById(R.id.thought)
        saveButtonContainer = v.findViewById(R.id.save_btn_container)
        thoughtsContainerCard = v.findViewById(R.id.thoughts_container_card)


        //color colors init
        colorOne = v.findViewById(R.id.one)
        colorTwo = v.findViewById(R.id.two)
        colorThree = v.findViewById(R.id.three)
        colorFour = v.findViewById(R.id.four)
        colorFive = v.findViewById(R.id.five)
        colorSix = v.findViewById(R.id.six)
        colorSeven = v.findViewById(R.id.seven)
        colorEight = v.findViewById(R.id.eight)

        colorOne.setOnClickListener(this)
        colorTwo.setOnClickListener(this)
        colorThree.setOnClickListener(this)
        colorFour.setOnClickListener(this)
        colorFive.setOnClickListener(this)
        colorSix.setOnClickListener(this)
        colorSeven.setOnClickListener(this)
        colorEight.setOnClickListener(this)

        //setting default card color
        colorId = ThoughtColorPicker.thoughtColor(colorOne.id)
        cardColor = "#" + Integer.toHexString(ContextCompat.getColor(activity!!, colorId!!))
        thoughtsContainerCard.setCardBackgroundColor(Color.parseColor(cardColor))

//        thoughtInput.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                if (thoughtInput.length() != 0) {
//                    saveButtonContainer.visibility = View.VISIBLE
//                } else {
//                    saveButtonContainer.visibility = View.INVISIBLE
//                }
//            }
//
//        })
//
//        thoughtInput.setOnEditorActionListener { _, actionId, event ->
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                saveButtonContainer.visibility = View.GONE
//            }
//            true
//        }

//        fun buttonColor(view: View) {
//            val id = view.id
//            val colorId = ThoughtColorPicker.thoughtColor(id)
//            color = "#" + Integer.toHexString(ContextCompat.getColor(activity!!, colorId))
//            thoughtsContainerCard.setBackgroundColor(Color.parseColor(color))
//        }

        return v
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.one -> {
                colorId = ThoughtColorPicker.thoughtColor(v.id)
            }
            R.id.two -> {
                colorId = ThoughtColorPicker.thoughtColor(v.id)
            }
            R.id.three -> {
                colorId = ThoughtColorPicker.thoughtColor(v.id)
            }
            R.id.four -> {
                colorId = ThoughtColorPicker.thoughtColor(v.id)
            }
            R.id.five -> {
                colorId = ThoughtColorPicker.thoughtColor(v.id)
            }
            R.id.six -> {
                colorId = ThoughtColorPicker.thoughtColor(v.id)
            }
            R.id.seven -> {
                colorId = ThoughtColorPicker.thoughtColor(v.id)
            }
            R.id.eight -> {
                colorId = ThoughtColorPicker.thoughtColor(v.id)
            }
        }
        cardColor = "#" + Integer.toHexString(ContextCompat.getColor(activity!!, colorId!!))
        thoughtsContainerCard.setCardBackgroundColor(Color.parseColor(cardColor))
    }


}