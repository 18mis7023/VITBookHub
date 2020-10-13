package com.developer.vitapbookhub.Adapter.Interview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.developer.vitapbookhub.Activity.Books.DownloadActivity
import com.developer.vitapbookhub.R
import com.developer.vitapbookhub.model.InterviewBook

class DataRecyclerAdapter(val context: Context, val itemList:List<InterviewBook>) : RecyclerView.Adapter<DataRecyclerAdapter.DataViewHolder>() {
    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtrecyclebook: TextView = view.findViewById(R.id.txttopic)
        val txtrecycleauthor: TextView = view.findViewById(R.id.txtdiff)
        val btndownload: TextView = view.findViewById(R.id.recyclerdownload)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.recycler_interview_single_row,parent,false)

        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int)  {

        val book=itemList[position]
        holder.txtrecyclebook.text=book.topic
        holder.txtrecycleauthor.text=book.difficulty
        holder.btndownload.setOnClickListener {
            val intent = Intent(context, DownloadActivity::class.java)
            val bundle = Bundle()
            bundle.putString("data", "View")
            bundle.putString("bookname", book.topic)
            bundle.putString("author", book.difficulty)
            bundle.putString("url", book.url)
            intent.putExtra("details", bundle)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ContextCompat.startActivity(context, intent, bundle)

        }

    }
}