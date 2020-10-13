package com.developer.vitapbookhub.Activity.interview

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
import com.developer.vitapbookhub.Activity.InterviewQuestionsActivity
import com.developer.vitapbookhub.Adapter.Interview.PhpRecyclerAdapter
import com.developer.vitapbookhub.R
import com.developer.vitapbookhub.model.InterviewBook
import kotlinx.android.synthetic.main.activity_php.*
import java.util.*
import kotlin.Comparator

class PhpActivity : AppCompatActivity() {
    lateinit var recyclerDbms: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var ref: DatabaseReference
    lateinit var booklist:MutableList<InterviewBook>
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout

    var url:String=""
    lateinit var recycleradapter: PhpRecyclerAdapter
    var order=-1
    var ratingcomparator=Comparator<InterviewBook>{book1,book2->
        book1.topic.compareTo(book2.topic,true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_php)
        SetUpToolbar()
        progressBar = findViewById(R.id.progressbar)
        progressBar.visibility = View.VISIBLE
        progressLayout = findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE
        booklist= mutableListOf()
        recyclerDbms=findViewById(R.id.recyclerPhp)
        ref= FirebaseDatabase.getInstance().getReference().child("php")
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
                        val book=b.getValue(InterviewBook::class.java)
                        booklist.add(book!!)
                    }
                    progressLayout.visibility=View.GONE
                    layoutManager= LinearLayoutManager(applicationContext)
                    recycleradapter= PhpRecyclerAdapter(applicationContext,booklist)
                    recyclerDbms.adapter=recycleradapter
                    recyclerDbms.layoutManager=layoutManager
                    recyclerDbms.addItemDecoration(
                        DividerItemDecoration(
                            recyclerDbms.context,
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
        adViewphp.loadAd(adRequest)

        adViewphp.visibility = View.GONE
        adViewphp.adListener = object : AdListener(){
            override fun onAdLoaded() {
                adViewphp.visibility = View.VISIBLE
                super.onAdLoaded()
            }
        }
    }
    fun SetUpToolbar(){
        setSupportActionBar(phptoolbar)
        supportActionBar?.title="PHP"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item?.itemId
        when(id)
        {
            android.R.id.home ->{
                startActivity(Intent(this, InterviewQuestionsActivity::class.java))
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
