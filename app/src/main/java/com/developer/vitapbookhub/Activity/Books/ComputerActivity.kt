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
import com.developer.vitapbookhub.Activity.LibraryBooksActivity
import com.developer.vitapbookhub.Adapter.ComputerRecyclerAdapter
import com.developer.vitapbookhub.R
import com.developer.vitapbookhub.model.Book
import kotlinx.android.synthetic.main.activity_computer.*
import java.util.*
import kotlin.Comparator

class ComputerActivity : AppCompatActivity() {

    lateinit var recyclersoftware: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var ref: DatabaseReference
    lateinit var booklist:MutableList<Book>
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout
    var url:String=""
    lateinit var recycleradapter: ComputerRecyclerAdapter
    var order=-1
    var ratingcomparator=Comparator<Book>{book1,book2->
        book1.bookName.compareTo(book2.bookName,true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_computer)
        SetUpToolbar()
        progressBar = findViewById(R.id.progressbar)
        progressBar.visibility = View.VISIBLE
        progressLayout = findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE
        booklist= mutableListOf()

        recyclersoftware=findViewById(R.id.recyclerComputer)
        ref= FirebaseDatabase.getInstance().getReference().child("computer")
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
                    recycleradapter= ComputerRecyclerAdapter(applicationContext,booklist)
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
        adViewcomputer.loadAd(adRequest)

        adViewcomputer.visibility = View.GONE
        adViewcomputer.adListener = object : AdListener(){
            override fun onAdLoaded() {
                adViewcomputer.visibility = View.VISIBLE
                super.onAdLoaded()
            }
        }
    }
    fun SetUpToolbar(){
        setSupportActionBar(computertoolbar)
        supportActionBar?.title="Computer"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item?.itemId
        when(id)
        {
            android.R.id.home ->{
                startActivity(Intent(this, LibraryBooksActivity::class.java))
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
