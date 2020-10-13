package com.developer.vitapbookhub.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.developer.vitapbookhub.Activity.Books.*
import com.developer.vitapbookhub.R
import kotlinx.android.synthetic.main.activity_library_books.*

class LibraryBooksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library_books)
        /*btnMTSE.setOnClickListener{
            startActivity(Intent(this,MTSEActivity::class.java))
            finish()
        }*/
        btnMaths.setOnClickListener{
            startActivity(Intent(this,MathsActivity::class.java))
            finish()
        }
        btncomputer.setOnClickListener{
            startActivity(Intent(this,ComputerActivity::class.java))
            finish()
        }
        btnSoftware.setOnClickListener{
            startActivity(Intent(this,SoftwareActivity::class.java))
            finish()
        }
        btnEnglish.setOnClickListener{
            startActivity(Intent(this,EnglishActivity::class.java))
            finish()
        }
        btnelectronics.setOnClickListener{
            startActivity(Intent(this,ElectronicsActivity::class.java))
            finish()
        }
        btnCivil.setOnClickListener{
            startActivity(Intent(this,CivilActivity::class.java))
            finish()
        }
        btnMechanical.setOnClickListener{
            startActivity(Intent(this,MechanicalActivity::class.java))
            finish()
        }
        btnphysics.setOnClickListener{
            startActivity(Intent(this,PhysicsActivity::class.java))
            finish()
        }
    adview()
    }
    fun adview(){
        MobileAds.initialize(this,getString(R.string.admob_app_id))
        val adRequest = AdRequest.Builder().build()
        adViewlibrarybooks.loadAd(adRequest)

        adViewlibrarybooks.visibility = View.GONE
        adViewlibrarybooks.adListener = object : AdListener(){
            override fun onAdLoaded() {
                adViewlibrarybooks.visibility = View.VISIBLE
                super.onAdLoaded()
            }
        }
    }
}
