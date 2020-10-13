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
import com.developer.vitapbookhub.Adapter.Interview.DataRecyclerAdapter
import com.developer.vitapbookhub.R
import com.developer.vitapbookhub.model.InterviewBook
import kotlinx.android.synthetic.main.activity_data_interpretation.*
import java.util.*
import kotlin.Comparator

class DataInterpretationActivity : AppCompatActivity() {
    lateinit var recyclerC: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var ref: DatabaseReference
    lateinit var booklist:MutableList<InterviewBook>
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout
    val bookInfoList= arrayListOf<InterviewBook>(

        InterviewBook(1,"bar graph","Easy","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/data%20interpretation%2Fbar%20graph.pdf?alt=media&token=86deb4fc-96f7-4fd3-a1ca-58b363f9e007"),
        InterviewBook(2,"net diagrams","Medium","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/data%20interpretation%2Fnet%20diagrams.pdf?alt=media&token=88eb6a02-43dc-43de-b9ae-9c214ae9735f"),
        InterviewBook(3,"pie charts","Medium","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/data%20interpretation%2Fpie%20charts.pdf?alt=media&token=95899451-1304-4496-b0fd-36676aab955b"),
        InterviewBook(4,"tabular data","High","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/data%20interpretation%2Ftabular%20data.pdf?alt=media&token=83ddc6b1-68de-4ab2-9fe5-24319a564a94"),
        InterviewBook(5,"venn diagrams","High","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/data%20interpretation%2Fvenn%20diagrams.pdf?alt=media&token=c80e8f0c-39c9-450f-983d-e067330f2331")
    )

    var url:String=""
    lateinit var recycleradapter: DataRecyclerAdapter
    var order=-1
    var ratingcomparator=Comparator<InterviewBook>{book1,book2->
        book1.topic.compareTo(book2.topic,true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_interpretation)
        SetUpToolbar()
        progressBar = findViewById(R.id.progressbar)
        progressBar.visibility = View.VISIBLE
        progressLayout = findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE
        booklist= mutableListOf()
        recyclerC=findViewById(R.id.recyclerDataInterpretation)
        ref= FirebaseDatabase.getInstance().getReference().child("datainterpretation")
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
                    recycleradapter= DataRecyclerAdapter(applicationContext,booklist)
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
        adViewdatainter.loadAd(adRequest)

        adViewdatainter.visibility = View.GONE
        adViewdatainter.adListener = object : AdListener(){
            override fun onAdLoaded() {
                adViewdatainter.visibility = View.VISIBLE
                super.onAdLoaded()
            }
        }
    }
    fun SetUpToolbar(){
        setSupportActionBar(datatoolbar)
        supportActionBar?.title="Data Interpretation"
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
