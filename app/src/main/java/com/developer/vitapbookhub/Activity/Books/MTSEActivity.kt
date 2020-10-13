package com.developer.vitapbookhub.Activity.Books

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
import com.developer.vitapbookhub.Activity.HomeActivity
import com.developer.vitapbookhub.Adapter.MTSERecyclerAdapter
import com.developer.vitapbookhub.R
import com.developer.vitapbookhub.model.Book
import kotlinx.android.synthetic.main.activity_m_t_s_e.*
import java.util.*
import kotlin.Comparator

class MTSEActivity : AppCompatActivity() {
    lateinit var recyclercivil: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var ref: DatabaseReference
    lateinit var booklist:MutableList<Book>
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout


    var url:String=""
    lateinit var recycleradapter: MTSERecyclerAdapter
    var order=-1
    var ratingcomparator=Comparator<Book>{ book1, book2->
        book1.bookName.compareTo(book2.bookName,true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_m_t_s_e)
        SetUpToolbar()
        progressBar = findViewById(R.id.progressbar)
        progressBar.visibility = View.VISIBLE
        progressLayout = findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE
        booklist= mutableListOf()
        recyclercivil=findViewById(R.id.recyclerMtse)
        ref= FirebaseDatabase.getInstance().getReference().child("mtse")
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
                        val book=b.getValue(Book::class.java)
                        booklist.add(book!!)
                        System.out.println(booklist)
                    }
                    progressLayout.visibility=View.GONE
                    layoutManager= LinearLayoutManager(applicationContext)
                    recycleradapter= MTSERecyclerAdapter(applicationContext,booklist)
                    recyclercivil.adapter=recycleradapter
                    recyclercivil.layoutManager=layoutManager
                    recyclercivil.addItemDecoration(
                        DividerItemDecoration(
                            recyclercivil.context,
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
        adViewmtse.loadAd(adRequest)

        adViewmtse.visibility = View.GONE
        adViewmtse.adListener = object : AdListener(){
            override fun onAdLoaded() {
                adViewmtse.visibility = View.VISIBLE
                super.onAdLoaded()
            }
        }
    }
    fun SetUpToolbar(){
        setSupportActionBar(Mtsetoolbar)
        supportActionBar?.title="MTech Software Integrated"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item?.itemId
        when(id)
        {
            android.R.id.home ->{
                startActivity(Intent(this, HomeActivity::class.java))
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
