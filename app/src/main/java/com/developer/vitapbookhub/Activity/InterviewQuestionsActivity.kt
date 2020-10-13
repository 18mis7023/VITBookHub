package com.developer.vitapbookhub.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.developer.vitapbookhub.Activity.interview.*
import com.developer.vitapbookhub.R
import kotlinx.android.synthetic.main.activity_interview_questions.*

class InterviewQuestionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interview_questions)
        relativelayoutJava.setOnClickListener{
            startActivity(Intent(this,JavaActivity::class.java))
        }
        relativelayoutC.setOnClickListener {
            startActivity(Intent(this,CActivity::class.java))
            finish()
        }
        relativelayoutDatabase.setOnClickListener {
            startActivity(Intent(this, DbmsActivity::class.java))
            finish()
        }
        relativelayoutOS.setOnClickListener {
            startActivity(Intent(this, OsActivity::class.java))
            finish()
        }
        relativelayoutWebDevelopment.setOnClickListener {
            startActivity(Intent(this, HtmlActivity::class.java))
            finish()
        }
        relativelayoutpython.setOnClickListener {
            startActivity(Intent(this,PhythonActivity::class.java))
            finish()
        }
        relativelayoutPHPDevelopment.setOnClickListener {
            startActivity(Intent(this,PhpActivity::class.java))
            finish()
        }
        relativelayoutnetworking.setOnClickListener {
            startActivity(Intent(this,NetworkingActivity::class.java))
            finish()
        }
    }
}
