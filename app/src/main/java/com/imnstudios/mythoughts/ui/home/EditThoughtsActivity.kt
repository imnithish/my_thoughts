package com.imnstudios.mythoughts.ui.home

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.imnstudios.mythoughts.R
import com.imnstudios.mythoughts.data.db.entities.Thoughts
import com.imnstudios.mythoughts.ui.splashScreen.SplashScreenActivity
import com.imnstudios.mythoughts.utils.ThoughtColorPicker
import kotlinx.android.synthetic.main.activity_edit_thoughts.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class EditThoughtsActivity : AppCompatActivity() {


    private val TAG = "Debug014589"
//    private val TAG = "AllThoughtsFragmentDebug"

    private var idValue: Int? = null
    private var thoughtValue: String? = null
    private var thoughtDescriptionValue: String? = null
    private var colorValue: String? = null
    private var colorValuePublic: String? = null

    private lateinit var userUid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, " onCreate EditThoughtsActivity")
        setContentView(R.layout.activity_edit_thoughts)

        userUid = SplashScreenActivity.auth.currentUser?.uid.toString()

        idValue = intent.getIntExtra("id", 0)
        thoughtValue = intent.getStringExtra("thought")
        thoughtDescriptionValue = intent.getStringExtra("thoughtDescription")
        colorValue = intent.getStringExtra("color")
        colorValuePublic = colorValue

        thought.setText(thoughtValue)
        if (!thoughtDescriptionValue.equals("blank"))
            thought_description.setText(thoughtDescriptionValue)
        thoughts_container_card.setCardBackgroundColor(Color.parseColor(colorValue))

        delete_thought.setOnClickListener {
            deleteThought()
        }

    }

    private fun deleteThought() {

        val thoughts =
            Thoughts(
                idValue as Int,
                thoughtValue as String,
                thoughtDescriptionValue as String,
                colorValuePublic as String
            )

        val dialog = Dialog(this)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.logout_dialog)
        dialog.setCancelable(true)
        dialog.show()
        val deleteConfirm = dialog.findViewById<Button>(R.id.log_out_confirm_btn)
        val cancel = dialog.findViewById<Button>(R.id.cancel_btn)
        val message = dialog.findViewById<TextView>(R.id.log_out_prompt)
        message.text = getString(R.string.delete_message)
        deleteConfirm.text = getString(R.string.delete)
        deleteConfirm.setOnClickListener {

            HomeActivity.viewModel.delete(thoughts)
            HomeActivity.firestoreDb.collection(userUid).document("$idValue").delete()
                .addOnSuccessListener {
                    Log.d(TAG, " delete success")
                }.addOnFailureListener {
                    Log.d(TAG, " delete failure")
                }
            dialog.dismiss()
            finish()
            overridePendingTransition(
                R.anim.activity_fade_in_animation,
                R.anim.activity_fade_out_animation
            )
        }
        cancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun cardColor(view: View) {
        val id = view.id
        val colorId = ThoughtColorPicker.thoughtColor(id)
        colorValue = "#" + Integer.toHexString(ContextCompat.getColor(this, colorId))
        thoughts_container_card.setCardBackgroundColor(Color.parseColor(colorValue))
    }

    override fun onBackPressed() {
        saveThought()
    }

    private fun saveThought() {

        val thoughtValue = thought.text.toString().trim()
        val thoughtDescriptionValue = thought_description.text.toString().trim()
        val color = colorValue.toString()

        if (thoughtValue.isBlank() && thoughtDescriptionValue.isBlank()) {
            finish()
            overridePendingTransition(
                R.anim.activity_fade_in_animation,
                R.anim.activity_fade_out_animation
            )
            return
        }

        //pushing to db
        val thoughts =
            Thoughts(idValue as Int, thoughtValue, thoughtDescriptionValue, color)

        HomeActivity.viewModel.update(thoughts)
        HomeActivity.firestoreDb.collection(userUid).document(idValue.toString())
            .set(thoughts)

        finish()
        overridePendingTransition(
            R.anim.activity_fade_in_animation,
            R.anim.activity_fade_out_animation
        )
    }


}