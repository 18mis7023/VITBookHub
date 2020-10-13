package com.developer.vitapbookhub.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.developer.vitapbookhub.R
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        auth = FirebaseAuth.getInstance()
        btnSendMyPassword.setOnClickListener {
            auth.sendPasswordResetEmail(etEmailForgot.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(baseContext,"Password sent to your mail...Go to login page and login",Toast.LENGTH_SHORT).show()
                        startActivity(
                            Intent(this,
                                LoginActivity::class.java)
                        )
                    }
                }
        }
        txtLoginForgot.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
