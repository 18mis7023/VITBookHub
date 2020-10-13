package com.developer.vitapbookhub.Activity.interview.Aptitude

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.*
import com.developer.vitapbookhub.Activity.interview.AptitudeActivity
import com.developer.vitapbookhub.Adapter.Interview.QuantitativeRecyclerAdapter
import com.developer.vitapbookhub.R
import com.developer.vitapbookhub.model.InterviewBook
import kotlinx.android.synthetic.main.activity_quantitative_aptitude.*
import java.util.*
import kotlin.Comparator

class QuantitativeAptitudeActivity : AppCompatActivity() {
    lateinit var recyclerC: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var ref: DatabaseReference
    lateinit var booklist:MutableList<InterviewBook>
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout
    val bookInfoList= arrayListOf<InterviewBook>(
        InterviewBook(1,"mixtures and alligations","Medium","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/quantitative%20aptitude%2FMixtures%20and%20Alligations.pdf?alt=media&token=2180c9ef-fc74-49a3-9896-1962bd8b8e2a"),
        InterviewBook(2,"number system","Medium","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/quantitative%20aptitude%2FNumber%20System.pdf?alt=media&token=e2d5eaae-85ce-4148-a99b-4d590fd4d3d9"),
        InterviewBook(3,"permutations and combinations","High","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/quantitative%20aptitude%2FPermutations%20and%20combinations.pdf?alt=media&token=15f63c58-b081-44ab-8e6b-3bd5e770011c"),
        InterviewBook(4,"probability","Medium","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/quantitative%20aptitude%2FProbability.pdf?alt=media&token=6a4bffa2-9f33-44fa-9d80-e8318e3b244a"),
        InterviewBook(5,"boats and streams","Easy","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/quantitative%20aptitude%2Fboats%20and%20streams.pdf?alt=media&token=94ed1b4c-eb4f-4e07-97cd-36b3a71e707a"),
        InterviewBook(6,"fractions","Medium","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/quantitative%20aptitude%2Ffractions.pdf?alt=media&token=46ee6188-2c32-4df8-bc10-be6293c585d8"),
        InterviewBook(7,"geometry and mensuration","Medium","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/quantitative%20aptitude%2Fgeometry%20and%20mensuration.pdf?alt=media&token=519fcb0f-b547-479b-b511-7e6d68df928c"),
        InterviewBook(8,"percentages","High","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/quantitative%20aptitude%2Fpercentages.pdf?alt=media&token=057e4733-b4da-4264-98ca-8338fe5662ea"),
        InterviewBook(9,"profit and loss","High","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/quantitative%20aptitude%2Fprogression.pdf?alt=media&token=83ad303d-fbf8-43ba-a44e-8eb2bec689b7"),
        InterviewBook(10,"progression","Medium","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/quantitative%20aptitude%2Fprogression.pdf?alt=media&token=83ad303d-fbf8-43ba-a44e-8eb2bec689b7"),
        InterviewBook(11,"ratio and proportion","High","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/quantitative%20aptitude%2Fratio%20and%20proportion.pdf?alt=media&token=4191ab46-07d9-43ff-8dcb-39f4a6d93061"),
        InterviewBook(12,"sets and union","High","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/quantitative%20aptitude%2Fsets%20and%20union.pdf?alt=media&token=3df33b9e-addc-412a-adae-12f5eee41c8a"),
        InterviewBook(13,"simple and compound interest","High","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/quantitative%20aptitude%2Fsimpl%20and%20compound%20intrest.pdf?alt=media&token=269cb8d9-6d42-4feb-966b-da87842c14d4"),
        InterviewBook(14,"simplification","Medium","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/quantitative%20aptitude%2Fsimplification.pdf?alt=media&token=420c740c-d0ef-45ef-90c2-6ff44509534d"),
        InterviewBook(15,"speed and distance","Medium","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/quantitative%20aptitude%2Fspeed%20and%20distance.pdf?alt=media&token=dc64b4ea-aee3-4af1-9939-abd3e4f65cd9"),
        InterviewBook(16,"surds and indices","Easy","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/quantitative%20aptitude%2Fsurds%20and%20indices.pdf?alt=media&token=aae564b9-4419-40a5-aa4b-909177643728"),
        InterviewBook(17,"time and work","Easy","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/quantitative%20aptitude%2Ftime%20and%20work.pdf?alt=media&token=64984453-3894-4916-b976-c5d2d3f04311")

    )

    var url:String=""
    lateinit var recycleradapter: QuantitativeRecyclerAdapter
    var order=-1
    var ratingcomparator=Comparator<InterviewBook>{book1,book2->
        book1.topic.compareTo(book2.topic,true)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quantitative_aptitude)
        SetUpToolbar()
        progressBar = findViewById(R.id.progressbar)
        progressBar.visibility = View.VISIBLE
        progressLayout = findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE
        booklist= mutableListOf()
        recyclerC=findViewById(R.id.recyclerQuantitative)
        ref= FirebaseDatabase.getInstance().getReference().child("quantitative")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                System.out.println(p0)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0!!.exists())
                {
                    booklist.clear()
                    for(b in p0.children)
                    {
                        System.out.println(b)
                        val book=b.getValue(InterviewBook::class.java)
                        booklist.add(book!!)
                        System.out.println(booklist)
                    }
                    progressLayout.visibility=View.GONE
                    layoutManager= LinearLayoutManager(applicationContext)
                    recycleradapter= QuantitativeRecyclerAdapter(applicationContext,booklist)
                    recyclerC.adapter=recycleradapter
                    recyclerC.layoutManager=layoutManager
                    recyclerC.addItemDecoration(
                        DividerItemDecoration(
                            recyclerC.context,
                            (layoutManager as LinearLayoutManager).orientation
                        )
                    )


                }
            }

        })
adview()
    }
    fun adview(){
        MobileAds.initialize(this,getString(R.string.admob_app_id))
        val adRequest = AdRequest.Builder().build()
        adViewquantitative.loadAd(adRequest)

        adViewquantitative.visibility = View.GONE
        adViewquantitative.adListener = object : AdListener(){
            override fun onAdLoaded() {
                adViewquantitative.visibility = View.VISIBLE
                super.onAdLoaded()
            }
        }
    }
    fun SetUpToolbar(){
        setSupportActionBar(quantitativetoolbar)
        supportActionBar?.title="Quantitative"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item?.itemId
        when(id)
        {
            android.R.id.home ->{
                startActivity(Intent(this, AptitudeActivity::class.java))
                finish()
            }
            R.id.action_sort_inc ->{
                if(order==1) {
                    Collections.sort(booklist, ratingcomparator)
                    order=0
                }
                else if(order==0)
                {
                    Collections.sort(booklist, ratingcomparator)
                    booklist.reverse()
                    order=1
                }
                else
                {
                    Collections.sort(booklist, ratingcomparator)
                    order=0
                }
            }
        }
        recycleradapter.notifyDataSetChanged()

        return super.onOptionsItemSelected(item)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard,menu)
        return super.onCreateOptionsMenu(menu)
    }
}
