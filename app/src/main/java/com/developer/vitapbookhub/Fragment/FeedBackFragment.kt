package com.developer.vitapbookhub.Fragment

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast

import com.developer.vitapbookhub.R

/**
 * A simple [Fragment] subclass.
 */
class FeedBackFragment : Fragment() {
    lateinit var etfeedbackName:EditText
    lateinit var etfeedback:EditText
    lateinit var btnfeedback:Button
    lateinit var ratingfeedback:RatingBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_feed_back, container, false)
        etfeedbackName=view.findViewById(R.id.etfeedbackName)
        etfeedback=view.findViewById(R.id.etfeedback)
        btnfeedback=view.findViewById(R.id.btnfeedback)
        ratingfeedback=view.findViewById(R.id.ratingfeedback)
        btnfeedback.setOnClickListener {
            Toast.makeText(activity as Context,"Rating given is ${ratingfeedback.rating} and send Feedback Via Gmail...",Toast.LENGTH_LONG).show()
            /*
            var intent=Intent(Intent.ACTION_SEND)
            intent.setType("message/html")
            intent.putExtra(Intent.EXTRA_EMAIL,"kodalihemanthchowdary4@gmail.com")
            intent.putExtra(Intent.EXTRA_SUBJECT,"FeedBack From App")
            intent.putExtra(Intent.EXTRA_TEXT,"Name:${etfeedbackName.text.toString()}\nMessage:${etfeedback.text.toString()}")*/
            var str:String="developer.vitapbookhub@gmail.com"
            var mailto= arrayOf(str)
            try {
                startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND)
                    .setType("message/html")
                    .putExtra(Intent.EXTRA_EMAIL,mailto)
                    .putExtra(Intent.EXTRA_SUBJECT,"FeedBack From App")
                    .putExtra(Intent.EXTRA_TEXT,"Name:${etfeedbackName.text.toString()}\nMessage:${etfeedback.text.toString()}\nRating:${ratingfeedback.rating}"
                    ),"Please select mail"))
            }catch (aex:ActivityNotFoundException)
            {
                Toast.makeText(activity as Context,"there are no Email Client",Toast.LENGTH_SHORT).show()
            }
        }
        return view

    }

}
