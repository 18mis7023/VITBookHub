package com.developer.vitapbookhub.Activity

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
import com.developer.vitapbookhub.Adapter.HomeRecyclerAdapter
import com.developer.vitapbookhub.R
import com.developer.vitapbookhub.model.Book
import kotlinx.android.synthetic.main.activity_library_joournals.*
import java.util.*
import kotlin.Comparator

class LibraryJoournalsActivity : AppCompatActivity() {
    lateinit var recyclerhome: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
   // lateinit var database:DatabaseReference
   /* val bookList= arrayListOf(
        "1","2","3","4","5","6","7","8","9","10","11","12"
    )*/
   lateinit var ref:DatabaseReference
    lateinit var booklist:MutableList<Book>
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout
    var url:String=""
    lateinit var recycleradapter: HomeRecyclerAdapter
    var order=-1
    var ratingcomparator=Comparator<Book>{book1,book2->
        book1.bookName.compareTo(book2.bookName,true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library_joournals)
        SetUpToolbar()
        progressBar = findViewById(R.id.progressbar)
        progressBar.visibility = View.VISIBLE
        progressLayout = findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE
        booklist= mutableListOf()




        recyclerhome=findViewById(R.id.recyclerHome)

        ref=FirebaseDatabase.getInstance().getReference().child("libraryjournals")
        ref.addValueEventListener(object : ValueEventListener{
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
                    progressLayout.visibility=View.GONE
                    layoutManager=LinearLayoutManager(applicationContext)
                    recycleradapter= HomeRecyclerAdapter(applicationContext,booklist)
                    recyclerhome.adapter=recycleradapter
                    recyclerhome.layoutManager=layoutManager
                    recyclerhome.addItemDecoration(
                        DividerItemDecoration(
                            recyclerhome.context,
                            (layoutManager as LinearLayoutManager).orientation
                        ))

                }
            }

        })




    adview()
    }
    fun adview(){
        MobileAds.initialize(this,getString(R.string.admob_app_id))
        val adRequest = AdRequest.Builder().build()
        adViewlibraryjournals.loadAd(adRequest)

        adViewlibraryjournals.visibility = View.GONE
        adViewlibraryjournals.adListener = object : AdListener(){
            override fun onAdLoaded() {
                adViewlibraryjournals.visibility = View.VISIBLE
                super.onAdLoaded()
            }
        }
    }

    fun SetUpToolbar(){
        setSupportActionBar(libraryjournaltoolbar)
        supportActionBar?.title="Journals"
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
