package com.developer.vitapbookhub.Activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.developer.vitapbookhub.R
import com.internshala.bookhub.util.ConnectionManager
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private var TAG:String = "MyActivity"
    //lateinit var auth: FirebaseAuth
    private lateinit var googleauth: FirebaseAuth
   // lateinit var sharedPreferences: SharedPreferences
    //var logged=true
    var id:String?="1"
    // Configure Google Sign In
    private lateinit var googleSignInClient:GoogleSignInClient
    var RC_SIGN_IN:Int = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //    sharedPreferences = getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
    //    val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        setContentView(R.layout.activity_login)
        if(ConnectionManager().checkConnectivity(this@LoginActivity))
        {
            createRequest()
            /*id=intent.getStringExtra("idLogged")
            if (isLoggedIn && id!="1") {
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }*/
            /*auth = FirebaseAuth.getInstance()*/
            googleauth = FirebaseAuth.getInstance()
            /*txtRegister.setOnClickListener{
                startActivity(Intent(this,
                    RegisterActivity::class.java))
                finish()
            }*/
            /*btnlogin.setOnClickListener {
                dologin()
            }

            txtForgotPassword.setOnClickListener {
                startActivity(Intent(this,
                    ForgotPasswordActivity::class.java))
                finish()
            }*/
            btngoolgesignin.setOnClickListener {
                signIn()
            }
        }
        else
        {
            val dialog= AlertDialog.Builder(this@LoginActivity)
            dialog.setTitle("Error")
            dialog.setMessage("Internet is not connected to the BookHub app")
            dialog.setPositiveButton("Open Settings"){text, listener ->
                val settingsintent= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsintent)
                finish()
            }
            dialog.setNegativeButton("Exit"){text, listener ->
                ActivityCompat.finishAffinity(this@LoginActivity)
            }
            dialog.create()
            dialog.show()
        }

    }

    private fun createRequest() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso)

    }
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
               // Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
               // Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        googleauth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                  //  val user = googleauth.currentUser
                    //Log.d(TAG, "signInWithCredential:success")
                    val intent=Intent(this,HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    // ...
                    //Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(baseContext, "Login failed.",
                        Toast.LENGTH_SHORT).show()
                 //   Snackbar.make(view, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                    //updateUI(null)
                }

                // ...
            }
    }

    /*private fun dologin() {
        if(etemail.text.toString().isEmpty())
        {
            etemail.error="Please enter email"
            etEmailRegister.requestFocus()
            return

        }
        if(!Patterns.EMAIL_ADDRESS.matcher(etemail.text.toString()).matches())
        {
            etemail.error="Please enter email"
            etemail.requestFocus()
            return
        }
        if(etpassword.text.toString().isEmpty())
        {
            etpassword.error="Please enter password"
            etpassword.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(etemail.text.toString(), etpassword.text.toString())
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    //sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Login failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)

                }

            }

    }*/
    public override fun onStart() {
        super.onStart()
        if(ConnectionManager().checkConnectivity(this))
        {
            var user=googleauth.currentUser
            if(user!=null)
            {
                val intent=Intent(this,HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        else
        {
            val dialog= AlertDialog.Builder(this)
            dialog.setTitle("Error")
            dialog.setMessage("Internet is not connected to the BookHub app")
            dialog.setPositiveButton("Open Settings"){text, listener ->
                val settingsintent= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsintent)
                finish()
            }
            dialog.setNegativeButton("Exit"){text, listener ->
                ActivityCompat.finishAffinity(this)
            }
            dialog.create()
            dialog.show()
        }


       /* val currentUser = auth.currentUser
        updateUI(currentUser)*/

    }
    private fun updateUI(currentUser : FirebaseUser?) {
        if(currentUser != null)
        {
            val intent=Intent(this,HomeActivity::class.java)
            //id="1"
            //intent.putExtra("email",etemail.text.toString())
            startActivity(intent)
            finish()
        }
        else
        {
            Toast.makeText(baseContext,"Login Failed.",Toast.LENGTH_SHORT).show()
        }
    }
    override fun onBackPressed() {
        ActivityCompat.finishAffinity(this@LoginActivity)
        }
    /*private fun revokeAccess() {
        googleSignInClient.revokeAccess().addOnCompleteListener{
            @Override
            fun onComplete(@NonNull task:Task<Void>) {
                // ...
            }

        }
    }*/

}

