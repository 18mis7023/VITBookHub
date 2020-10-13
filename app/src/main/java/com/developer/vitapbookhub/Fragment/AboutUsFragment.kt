package com.developer.vitapbookhub.Fragment

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import com.developer.vitapbookhub.R

/**
 * A simple [Fragment] subclass.
 */
class AboutUsFragment : Fragment() {
lateinit var txtaboutemail:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_about_us, container, false)
        txtaboutemail=view.findViewById(R.id.txtaboutemail)

        txtaboutemail.setOnClickListener {
            var str:String="developer.vitapbookhub@gmail.com"
            var mailto= arrayOf(str)
            try {
                startActivity(
                    Intent.createChooser(Intent(Intent.ACTION_SEND)
                    .setType("message/html")
                    .putExtra(Intent.EXTRA_EMAIL,mailto)
                    .putExtra(Intent.EXTRA_SUBJECT,"Queries From App")
                    ,"Please select mail"))
            }catch (aex: ActivityNotFoundException)
            {
                Toast.makeText(activity as Context,"there are no Email Client",Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

}
