package com.developer.vitapbookhub.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.developer.vitapbookhub.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
        btnRegister.setOnClickListener {
            regiterUser()
        }
        txtLogin.setOnClickListener {
            startActivity(Intent(this,
                LoginActivity::class.java))
            finish()
        }

    }
    private fun regiterUser()
    {
        if(etEmailRegister.text.toString().isEmpty())
        {
            etEmailRegister.error="Please enter email"
            etEmailRegister.requestFocus()
            return

        }
        if(!Patterns.EMAIL_ADDRESS.matcher(etEmailRegister.text.toString()).matches())
        {
            etEmailRegister.error="Please enter email"
            etEmailRegister.requestFocus()
            return
        }
        if(etPasswordRegister.text.toString().isEmpty())
        {
            etPasswordRegister.error="Please enter password"
            etPasswordRegister.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(etEmailRegister.text.toString(), etPasswordRegister.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this,
                        LoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(baseContext, "Sign Up failed. Try again after some time.",
                        Toast.LENGTH_SHORT).show()
                }
            }


    }
}
