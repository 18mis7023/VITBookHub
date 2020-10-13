package com.developer.vitapbookhub.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.developer.vitapbookhub.Activity.Books.DownloadActivity
import com.developer.vitapbookhub.R
import com.developer.vitapbookhub.model.Book
import com.squareup.picasso.Picasso

class EnglishRecyclerAdapter(val context: Context, val itemList:List<Book>) : RecyclerView.Adapter<EnglishRecyclerAdapter.EnglishViewHolder>() {
    class EnglishViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtrecyclebook: TextView = view.findViewById(R.id.txtrecyclerbook)
        val txtrecycleauthor: TextView = view.findViewById(R.id.txtrecyclerauthor)
        val btndownload: TextView = view.findViewById(R.id.btnDownload)
        val imgbook: ImageView = view.findViewById(R.id.imgrecyclerimage)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnglishViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.recycler_home_single_row,parent,false)

        return EnglishViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: EnglishViewHolder, position: Int)  {

        val book=itemList[position]
        holder.txtrecyclebook.text=book.bookName
        holder.txtrecycleauthor.text=book.bookAuthor
        //holder.imgbook.setImageResource(book.bookimage)
        Picasso.get().load(book.imageurl).into(holder.imgbook)
        holder.btndownload.setOnClickListener {
            val intent = Intent(context, DownloadActivity::class.java)
            val bundle = Bundle()
            bundle.putString("data", "View")
            bundle.putString("bookname", book.bookName)
            bundle.putString("author", book.bookAuthor)
            bundle.putString("url", book.bookUrl)
            intent.putExtra("details", bundle)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ContextCompat.startActivity(context, intent, bundle)

        }

    }
}