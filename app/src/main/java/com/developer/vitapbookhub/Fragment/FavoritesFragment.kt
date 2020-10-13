package com.developer.vitapbookhub.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView

import com.developer.vitapbookhub.Adapter.ComputerRecyclerAdapter

import com.developer.vitapbookhub.R
import com.developer.vitapbookhub.model.Book

/**
 * A simple [Fragment] subclass.
 */
class FavoritesFragment : Fragment() {
    private lateinit var recyclerRestaurant: RecyclerView
    private lateinit var allRestaurantsAdapter: ComputerRecyclerAdapter
    private var bookList = arrayListOf<Book>()
    private lateinit var rlLoading: RelativeLayout
    private lateinit var rlFav: RelativeLayout
    private lateinit var rlNoFav: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_favorites, container, false)
        /*rlFav = view.findViewById(R.id.rlFavorites)
        rlNoFav = view.findViewById(R.id.rlNoFavorites)
        rlLoading = view.findViewById(R.id.rlLoading)
        rlLoading.visibility = View.VISIBLE
        setUpRecycler(view)*/
        return view

    }
    /*private fun setUpRecycler(view: View) {
        recyclerRestaurant = view.findViewById(R.id.recyclerRestaurants)


        *//*In case of favourites, simply extract all the data from the DB and send to the adapter.
        * Here we can reuse the adapter created for the home fragment. This will save our time and optimize our app as well*//*
        val backgroundList = FavouritesAsync(activity as Context).execute().get()
        if (backgroundList.isEmpty()) {
            rlLoading.visibility = View.GONE
            rlFav.visibility = View.GONE
            rlNoFav.visibility = View.VISIBLE
        } else {
            rlFav.visibility = View.VISIBLE
            rlLoading.visibility = View.GONE
            rlNoFav.visibility = View.GONE
            for (i in backgroundList) {
                bookList.add(
                    Book(
                        i.bookName,
                        i.bookAuthor,
                        i.bookUrl,
                        i.bookimage
                    )
                )
            }

            allRestaurantsAdapter = ComputerRecyclerAdapter(activity as Context,bookList)
            val mLayoutManager = LinearLayoutManager(activity)
            recyclerRestaurant.layoutManager = mLayoutManager
            recyclerRestaurant.itemAnimator = DefaultItemAnimator()
            recyclerRestaurant.adapter = allRestaurantsAdapter
            recyclerRestaurant.setHasFixedSize(true)
        }

    }*/

    /*class FavouritesAsync(context: Context) : AsyncTask<Void, Void, List<Book>>() {

        val db = Room.databaseBuilder(context, BookDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg params: Void?): List<BookEntity> {

            return db.bookDao().getAllBooks()
        }

    }*/

}
