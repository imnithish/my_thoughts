package com.imnstudios.mythoughts.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.imnstudios.mythoughts.R
import com.imnstudios.mythoughts.ui.home.HomeActivity
import com.imnstudios.mythoughts.utils.hide
import com.imnstudios.mythoughts.utils.show
import com.imnstudios.mythoughts.utils.snackbar
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 1
    private val tag = "LoginActivityDebug"

    companion object {
        lateinit var auth: FirebaseAuth
        lateinit var mGoogleSignInClient: GoogleSignInClient
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initialise the FirebaseAuth object
        auth = FirebaseAuth.getInstance()

        setContentView(R.layout.activity_login)

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
                    Intent(this, HomeActivity::class.java).also {
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


}