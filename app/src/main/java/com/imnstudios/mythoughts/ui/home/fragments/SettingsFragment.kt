package com.imnstudios.mythoughts.ui.home.fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.imnstudios.mythoughts.R
import com.imnstudios.mythoughts.ui.home.HomeActivity
import com.imnstudios.mythoughts.ui.login.LoginActivity


class SettingsFragment : Fragment() {

    companion object {
        lateinit var auth: FirebaseAuth
    }

    private lateinit var user: TextView
    private lateinit var logOut: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_settings, container, false)

        //init views
        user = v.findViewById(R.id.user)
        logOut = v.findViewById(R.id.log_out_btn)

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
                Intent(activity, LoginActivity::class.java).also {
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
        return v
    }

}