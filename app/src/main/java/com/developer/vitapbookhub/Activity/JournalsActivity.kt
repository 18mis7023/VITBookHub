package com.developer.vitapbookhub.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView/*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference*/
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.*
import com.developer.vitapbookhub.Adapter.JournalsRecyclerAdapter
import com.developer.vitapbookhub.R
import com.developer.vitapbookhub.model.Book
import kotlinx.android.synthetic.main.activity_journals.*
import java.util.*
import kotlin.Comparator


class JournalsActivity : AppCompatActivity() {


    lateinit var recyclersoftware: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var ref: DatabaseReference
    lateinit var booklist:MutableList<Book>
    var url:String=""
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout
    lateinit var recycleradapter: JournalsRecyclerAdapter
    var order=-1
    var ratingcomparator=Comparator<Book>{ book1, book2->
        book1.bookName.compareTo(book2.bookName,ignoreCase = true)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journals)
        SetUpToolbar()
        progressBar = findViewById(R.id.progressbar)
        progressBar.visibility = View.VISIBLE
        progressLayout = findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE
        booklist= mutableListOf()
        recyclersoftware=findViewById(R.id.recyclerJournals)

        ref= FirebaseDatabase.getInstance().getReference().child("journals")
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
                        val book=b.getValue(Book::class.java)
                        booklist.add(book!!)
                    }
                    layoutManager= LinearLayoutManager(applicationContext)
                    progressLayout.visibility=View.GONE
                    recycleradapter= JournalsRecyclerAdapter(applicationContext,booklist)
                    recyclersoftware.adapter=recycleradapter
                    recyclersoftware.layoutManager=layoutManager
                    recyclersoftware.addItemDecoration(
                        DividerItemDecoration(
                            recyclersoftware.context,
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
        adViewjournals.loadAd(adRequest)

        adViewjournals.visibility = View.GONE
        adViewjournals.adListener = object : AdListener(){
            override fun onAdLoaded() {
                adViewjournals.visibility = View.VISIBLE
                super.onAdLoaded()
            }
        }
    }
    fun SetUpToolbar(){
        setSupportActionBar(journaltoolbar)
        supportActionBar?.title="Journals"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item?.itemId
        if(id==android.R.id.home)
        {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
        when(id)
        {
            android.R.id.home ->{
                startActivity(Intent(this,HomeActivity::class.java))
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
