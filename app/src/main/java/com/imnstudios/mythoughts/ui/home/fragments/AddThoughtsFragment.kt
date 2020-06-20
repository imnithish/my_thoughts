package com.imnstudios.mythoughts.ui.home.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.imnstudios.mythoughts.R
import com.imnstudios.mythoughts.data.db.entities.Thoughts
import com.imnstudios.mythoughts.ui.home.HomeActivity
import com.imnstudios.mythoughts.ui.splashScreen.SplashScreenActivity
import com.imnstudios.mythoughts.utils.ThoughtColorPicker
import com.imnstudios.mythoughts.utils.hide


class AddThoughtsFragment : Fragment(), View.OnClickListener {
    private val TAG = "Debug014589"
//    private val TAG = "AddThoughtsFragmentDebug"

    private lateinit var thoughtInput: EditText
    private lateinit var thoughtDescription: EditText
    private lateinit var saveButton: LinearLayout
    private lateinit var saveState: TextView
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


    private lateinit var userUid: String

    private var cardColor: String? = null
    private var colorId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, " onCreate AddThoughtsFragment")

        userUid = SplashScreenActivity.auth.currentUser?.uid.toString()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, " onCreateView AddThoughtsFragment")
        val v = inflater.inflate(R.layout.fragment_add_thoughts, container, false)

        saveButton = v.findViewById(R.id.save_btn_container)
        saveState = v.findViewById(R.id.save_state)
        thoughtInput = v.findViewById(R.id.thought)
        thoughtDescription = v.findViewById(R.id.thought_description)
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

        saveButton.setOnClickListener {
            saveThought()
        }

        return v
    }

    private fun saveThought() {


        val thought = thoughtInput.text.toString()
        var thoughtDescriptionString = thoughtDescription.text.toString()
        if (thoughtDescriptionString.isBlank())
            thoughtDescriptionString = "blank"
        val color = cardColor.toString()

        if (thought.isBlank())
            return

        saveButton.isEnabled = false
        val saving: String = getString(R.string.saving)
        saveState.text = saving


        //pushing to db
        val thoughts = Thoughts(0, thought, thoughtDescriptionString, color)

        val id: Long? = HomeActivity.viewModel.insert(thoughts)

        if (id != null) {
            val thoughtsToFirebase = Thoughts(id.toInt(), thought, thoughtDescriptionString, color)

            HomeActivity.firebaseDb.collection(userUid)
                .document(id.toString())
                .set(thoughtsToFirebase)
                .addOnSuccessListener { _ ->

                    val saved: String = getString(R.string.saved)
                    saveState.text = saved

                    val imm =
                        activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view!!.windowToken, 0)

                    val handler = Handler()
                    handler.postDelayed({
                        saveButton.isEnabled = true
                        val save: String = getString(R.string.save)
                        saveState.text = save
                        thoughtInput.text.clear()
                        thoughtDescription.text.clear()

                    }, 1000)

                }.addOnFailureListener { e ->
                    val imm =
                        activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view!!.windowToken, 0)
                    val error: String = getString(R.string.error)
                    saveState.text = error

                    val handler = Handler()
                    handler.postDelayed({
                        saveButton.isEnabled = true
                        val save: String = getString(R.string.save)
                        saveState.text = save
                        thoughtInput.clearFocus()
                        thoughtDescription.clearFocus()
                    }, 1000)
                }
        }

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