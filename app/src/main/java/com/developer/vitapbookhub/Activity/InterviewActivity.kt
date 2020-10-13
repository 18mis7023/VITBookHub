package com.developer.vitapbookhub.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.developer.vitapbookhub.Activity.interview.*
import com.developer.vitapbookhub.R
import kotlinx.android.synthetic.main.activity_interview.*

class InterviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interview)
        relativelayoutLanguages.setOnClickListener {
            startActivity(Intent(this,InterviewQuestionsActivity::class.java))
        }
        relativelayoutCIQ.setOnClickListener {
            startActivity(Intent(this, CIQActivity::class.java))
            finish()
        }
        relativelayoutCompanies.setOnClickListener {
            startActivity(Intent(this, CompaniesActivity::class.java))
            finish()
        }
        relativelayoutAlgorithims.setOnClickListener {
            startActivity(Intent(this, AlgorithimsActivity::class.java))
            finish()
        }
        relativelayoutSubjects.setOnClickListener {
            startActivity(Intent(this, SubjectsActivity::class.java))
            finish()
        }
        relativelayoutWeb.setOnClickListener {
            startActivity(Intent(this,WebDevelopmentActivity::class.java))
            finish()
        }
        relativelayoutAptitude.setOnClickListener {
            startActivity(Intent(this,AptitudeActivity::class.java))
        }




    }
}
