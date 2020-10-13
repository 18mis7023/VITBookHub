package com.developer.vitapbookhub.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.developer.vitapbookhub.R
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {
    lateinit var topanim:Animation
    lateinit var downanim:Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        topanim=AnimationUtils.loadAnimation(this,R.anim.top_anim)
        downanim=AnimationUtils.loadAnimation(this,R.anim.down_anim)
        imgwelcomeicon.setAnimation(topanim)
        txtbookname.setAnimation(downanim)
        Handler().postDelayed({
            val intent= Intent(this,
                LoginActivity::class.java)
            startActivity(intent)
        },2500)
    }
    override fun onPause() {
        super.onPause()
        finish()
    }
}
