package com.developer.vitapbookhub.Activity.Books

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.*
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.ads.*
import com.developer.vitapbookhub.R
import com.internshala.bookhub.util.ConnectionManager
import com.squareup.picasso.Picasso
import hotchemi.android.rate.AppRate
import kotlinx.android.synthetic.main.activity_download.*

class DownloadActivity : AppCompatActivity() {
    var mydownloadid:Long = 0
    private lateinit var mInterstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
        MobileAds.initialize(this,getString(R.string.admob_app_id))
        val adRequest = AdRequest.Builder().build()
        /*adView.loadAd(adRequest)

        adView.visibility = View.GONE

        adView.adListener = object : AdListener(){
            override fun onAdLoaded() {
                adView.visibility = View.VISIBLE
                super.onAdLoaded()
            }
        }
        adView1.loadAd(adRequest)

        adView1.visibility = View.GONE

        adView1.adListener = object : AdListener(){
            override fun onAdLoaded() {
                adView1.visibility = View.VISIBLE
                super.onAdLoaded()
            }
        }
*/
      /*  MobileAds.initialize(this,getString(R.string.admob_app_id))

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId= getString(R.string.interstital_ad_id)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        if(mInterstitialAd.isLoaded)
            mInterstitialAd.show()*/


//        mInterstitialAd.adListener = object : AdListener(){
//
//            override fun onAdLoaded() {
//                mInterstitialAd.show()
//                super.onAdLoaded()
//            }
//
//        }
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-4f07c.appspot.com/o/download_image.png?alt=media&token=543625ad-cbc0-406f-b1f7-073e427d4b7c").into(imgdownload)
        if(ConnectionManager().checkConnectivity(this@DownloadActivity))
        {

            //rating of the app
            AppRate.with(this)
                .setInstallDays(2)
                .setLaunchTimes(3)
                .setRemindInterval(2)
                .monitor()
            AppRate.showRateDialogIfMeetsConditions(this)

            //addview1
            val details = intent.getBundleExtra("details")

            val txtBookname = details.getString("bookname")
            var url=details.getString("url")
            recyclerdownload.setOnClickListener {
                if (intent != null) {

                    /*Fetching the details from the intent*/

                    var request= DownloadManager.Request(Uri.parse(url))
                        .setTitle(txtBookname)
                        .setDescription("${txtBookname} is downloading")
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"${System.currentTimeMillis()}")
                        .setMimeType("application/pdf")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

                    var dm: DownloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                    mydownloadid=dm.enqueue(request)
                    var br=object: BroadcastReceiver(){
                        override fun onReceive(p0: Context?, p1: Intent?) {
                            var id:Long?=p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1)
                            if(id==mydownloadid)
                            {
                                Toast.makeText(applicationContext,"successfully Downloaded the file .... downloaded file in Downloads ",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
                }
            }
            btnviewdrive.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(Uri.parse(url), "application/pdf")
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                val newIntent = Intent.createChooser(intent, "Open File")
                try {
                  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(newIntent)
                } catch (e: ActivityNotFoundException) {
                    // Instruct the user to install a PDF reader here, or something
                }
            }

            /*if (intent != null) {

                *//*Fetching the details from the intent*//*
                val details = intent.getBundleExtra("details")

                val txtBookname = details.getString("bookname")
                var url=details.getString("url")
                var request= DownloadManager.Request(Uri.parse(url))
                    .setTitle(txtBookname)
                    .setDescription("${txtBookname} is downloading")
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"${System.currentTimeMillis()}")
                    .setMimeType("application/pdf")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

                var dm: DownloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                mydownloadid=dm.enqueue(request)
                var br=object: BroadcastReceiver(){
                    override fun onReceive(p0: Context?, p1: Intent?) {
                        var id:Long?=p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1)
                        if(id==mydownloadid)
                        {
                            Toast.makeText(applicationContext,"successfully Downloaded the file .... downloaded file in Downloads ",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
            }*/
        }
        else
        {
            val dialog= AlertDialog.Builder(this@DownloadActivity)
            dialog.setTitle("Error")
            dialog.setMessage("Internet is not connected to the BookHub app")
            dialog.setPositiveButton("Open Settings"){text, listener ->
                val settingsintent= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsintent)
                finish()
            }
                dialog.setNegativeButton("exit"){text, listener ->
                ActivityCompat.finishAffinity(this@DownloadActivity)
            }
            dialog.create()
            dialog.show()
        }


    }
}
