package com.imnstudios.mythoughts

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.imnstudios.mythoughts.utils.hide
import com.imnstudios.mythoughts.utils.show
import com.imnstudios.mythoughts.utils.snackbar
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var slideDownAnim: Animation
    private lateinit var fadeInAnim: Animation
    private val RC_SIGN_IN = 1
    private val tag = "LoginActivity"

    companion object {
        lateinit var auth: FirebaseAuth
        lateinit var mGoogleSignInClient: GoogleSignInClient
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initialise the FirebaseAuth object
        auth = FirebaseAuth.getInstance()

        setupAppTheme()

        if (auth.currentUser != null) {
            Intent(this, MainActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
                overridePendingTransition(
                    R.anim.activity_fade_in_animation,
                    R.anim.activity_fade_out_animation
                )
            }
        } else {
            setContentView(R.layout.activity_login)

            //setting up animation starts here
            slideDownAnim =
                AnimationUtils.loadAnimation(applicationContext, R.anim.slide_down_animation)
            fadeInAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in_animation)
            heading.animation = slideDownAnim
            Handler().postDelayed({
                log_in.visibility = View.VISIBLE
                log_in.animation = fadeInAnim
            }, 500)
            //setting up animation ends here


            //GoogleSignInOptions object
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build()

            //get the GoogleSignInClient object from GoogleSignIn class
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

            log_in.setOnClickListener {
                signIn()
            }
        }

    }


    private fun signIn() {
        progress_bar.show()
        log_in.hide()
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //result returned from launching the Intent from mGoogleSignInClient.signInIntent
        if (requestCode == RC_SIGN_IN) {

            //GoogleSignIn Task
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                //Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                //authenticating with firebase
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                base_layout.snackbar("Something's wrong $e")
                progress_bar.hide()
                log_in.show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(tag, " firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)

        //sign in using Firebase
        auth.signInWithCredential(credential)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    Log.d(tag, " signInWithCredential:success")
                    Toast.makeText(
                        this,
                        "Welcome " + auth.currentUser?.displayName,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    Intent(this, MainActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                        overridePendingTransition(
                            R.anim.activity_fade_in_animation,
                            R.anim.activity_fade_out_animation
                        )
                    }
                } else {
                    Log.w(tag, " signInWithCredential:failure", task.exception)
                    base_layout.snackbar("Authentication failed ${task.exception.toString()}")
                    progress_bar.hide()
                    log_in.show()
                }
            }
    }


    private fun setupAppTheme() {
        val appSettingPrefs: SharedPreferences = getSharedPreferences("AppThemeModePrefs", 0)
        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", true)
        if (!isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }


}