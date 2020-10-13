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
import com.developer.vitapbookhub.Adapter.Interview.LogicalRecyclerAdapter
import com.developer.vitapbookhub.R
import com.developer.vitapbookhub.model.InterviewBook
import kotlinx.android.synthetic.main.activity_logical_aptitude.*
import java.util.*
import kotlin.Comparator

class LogicalAptitudeActivity : AppCompatActivity() {
    lateinit var recyclerC: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var ref: DatabaseReference
    lateinit var booklist:MutableList<InterviewBook>
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout
    val bookInfoList= arrayListOf<InterviewBook>(

        InterviewBook(1,"analogies","Easy","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/logical%20reasoning%2Fanalogies.pdf?alt=media&token=9ae62d31-c632-4be4-af92-c1b7f258ad78"),
        InterviewBook(2,"blood relations","High","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/logical%20reasoning%2Fblood%20relations.pdf?alt=media&token=fa26bc34-b20f-47c5-a199-f24911c34dfb"),
        InterviewBook(3,"cause and effect","Easy","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/logical%20reasoning%2Fcause%20and%20effect.pdf?alt=media&token=fd56341c-94db-4169-8852-fbab5d789de7"),
        InterviewBook(4,"clocks calenders and age","Medium","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/logical%20reasoning%2Fclocks%2Ccalender%20and%20age.pdf?alt=media&token=754bedd7-a09a-4ffa-9034-13e6bb9dd4c1"),
        InterviewBook(5,"coding and decoding","Medium","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/logical%20reasoning%2Fcoding%20and%20decoding.pdf?alt=media&token=1ca37fe4-0dd5-4594-82c6-334aab41a94a"),
        InterviewBook(6,"cubes and dices","Easy","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/logical%20reasoning%2Fcubes%20and%20dices.pdf?alt=media&token=a8ad3f2a-7197-426a-a39d-2878eb852724"),
        InterviewBook(7,"data sufficiency","Easy","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/logical%20reasoning%2Fdata%20sufficiency.pdf?alt=media&token=88b4a805-2428-4ed9-a392-73985cf43207"),
        InterviewBook(8,"day sequence","Easy","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/logical%20reasoning%2Fday%20sequence.pdf?alt=media&token=4aa4d48c-706e-4dff-bc96-6c80b1159866"),
        InterviewBook(9,"decision making","Medium","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/logical%20reasoning%2Fdecision%20making.pdf?alt=media&token=23509ced-113a-4c79-af17-1f5800ba5c09"),
        InterviewBook(10,"deductions","Easy","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/logical%20reasoning%2Fdeductions.pdf?alt=media&token=1469d969-32f3-4b3b-a37e-6e1380a330a3"),
        InterviewBook(11,"directions and senses","Medium","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/logical%20reasoning%2Fdirections%20and%20senses.pdf?alt=media&token=f8d465b1-5ee9-4465-9c2c-c96b6498c389"),
        InterviewBook(12,"inferences","Easy","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/logical%20reasoning%2Finferences.pdf?alt=media&token=97d69e88-a2fc-42a5-8bb3-0dd2674b1d6e"),
        InterviewBook(13,"input and output","Easy","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/logical%20reasoning%2Finput%20and%20output.pdf?alt=media&token=d31da5d3-74bf-49e5-91ee-bbed7eccbaa4"),
        InterviewBook(14,"logical problems","Medium","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/logical%20reasoning%2Flogical%20problems.pdf?alt=media&token=e60047a9-2c5f-4477-b78d-138588605672"),
        InterviewBook(15,"puzzeles","Easy","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/logical%20reasoning%2Fpuzzeles.pdf?alt=media&token=a9bdcd15-444b-465d-8ed8-e74f36789853"),
        InterviewBook(16,"statements and assumptions","Medium","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/logical%20reasoning%2Fstatements%20and%20assumptions.pdf?alt=media&token=0ade240c-9291-46c3-abd6-a37d02dc8d67"),
        InterviewBook(17,"statements and conclusions","Easy","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/logical%20reasoning%2Fstatements%20and%20conclusions.pdf?alt=media&token=061a8830-ba60-4c8b-ab2f-eeb1ae79ce32"),
        InterviewBook(18,"symbols and notations","Easy","https://firebasestorage.googleapis.com/v0/b/vitap-bookhub-9c2cc.appspot.com/o/logical%20reasoning%2Fsymbols%20and%20notations.pdf?alt=media&token=e49937ea-7e28-4836-89c5-401d5364f11a")    )

    var url:String=""
    lateinit var recycleradapter: LogicalRecyclerAdapter
    var order=-1
    var ratingcomparator=Comparator<InterviewBook>{book1,book2->
        book1.topic.compareTo(book2.topic,true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logical_aptitude)
        SetUpToolbar()
        progressBar = findViewById(R.id.progressbar)
        progressBar.visibility = View.VISIBLE
        progressLayout = findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE
        booklist= mutableListOf()
        recyclerC=findViewById(R.id.recyclerLogical)
        ref= FirebaseDatabase.getInstance().getReference().child("logical")
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
                    recycleradapter= LogicalRecyclerAdapter(applicationContext,booklist)
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
        adViewlogical.loadAd(adRequest)

        adViewlogical.visibility = View.GONE
        adViewlogical.adListener = object : AdListener(){
            override fun onAdLoaded() {
                adViewlogical.visibility = View.VISIBLE
                super.onAdLoaded()
            }
        }
    }
    fun SetUpToolbar(){
        setSupportActionBar(logicaltoolbar)
        supportActionBar?.title="Logical"
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
