package com.developer.vitapbookhub.Activity.interview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.developer.vitapbookhub.Activity.interview.Aptitude.DataInterpretationActivity
import com.developer.vitapbookhub.Activity.interview.Aptitude.LogicalAptitudeActivity
import com.developer.vitapbookhub.Activity.interview.Aptitude.QuantitativeAptitudeActivity
import com.developer.vitapbookhub.Activity.interview.Aptitude.VerbalActivity
import com.developer.vitapbookhub.R
import kotlinx.android.synthetic.main.activity_aptitude.*

class AptitudeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aptitude)
        relativelayoutquantitative.setOnClickListener {
            startActivity(Intent(this,QuantitativeAptitudeActivity::class.java))
            finish()
        }
        relativelayoutLogical.setOnClickListener {
            startActivity(Intent(this,LogicalAptitudeActivity::class.java))
            finish()
        }
        relativelayoutDatainterpretation.setOnClickListener {
            startActivity(Intent(this,DataInterpretationActivity::class.java))
            finish()
        }
        relativelayoutVerbal.setOnClickListener {
            startActivity(Intent(this,VerbalActivity::class.java))
            finish()
        }
        adview()
    }
    fun adview(){
        MobileAds.initialize(this,getString(R.string.admob_app_id))
        val adRequest = AdRequest.Builder().build()
        adViewaptitude.loadAd(adRequest)

        adViewaptitude.visibility = View.GONE
        adViewaptitude.adListener = object : AdListener(){
            override fun onAdLoaded() {
                adViewaptitude.visibility = View.VISIBLE
                super.onAdLoaded()
            }
        }
    }
}
