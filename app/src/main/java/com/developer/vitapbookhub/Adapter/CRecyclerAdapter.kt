package com.developer.vitapbookhub.Adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.developer.vitapbookhub.Activity.Books.DownloadActivity
import com.developer.vitapbookhub.R
import com.developer.vitapbookhub.model.InterviewBook

class CRecyclerAdapter (val context: Context, val itemList:List<InterviewBook>) : RecyclerView.Adapter<CRecyclerAdapter.CViewHolder>() {
    class CViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtrecyclebook: TextView = view.findViewById(R.id.txttopic)
        val txtrecycleauthor: TextView = view.findViewById(R.id.txtdiff)
        val btndownload: Button = view.findViewById(R.id.recyclerdownload)
        val btnviewdrive:Button=view.findViewById(R.id.btnviewdrive)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.recycler_interview_single_row,parent,false)

        return CViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: CViewHolder, position: Int)  {

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
//        holder.btnviewdrive.setOnClickListener {
//            val intent = Intent(Intent.ACTION_VIEW)
//        intent.setDataAndType(Uri.parse(book.url), "application/pdf")
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//        val newIntent = Intent.createChooser(intent, "Open File")
//        try {
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            ContextCompat.startActivity(context,newIntent, bundleOf())
//        } catch (e: ActivityNotFoundException) {
//            // Instruct the user to install a PDF reader here, or something
//        }
//        }

    }
}