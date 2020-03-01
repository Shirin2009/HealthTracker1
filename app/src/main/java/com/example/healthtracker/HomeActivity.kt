package com.example.healthtracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.home.*

class HomeActivity : AppCompatActivity() {
   // var EmailHolder: String? = null
    //var Email: TextView? = null
    var logout: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        img_sleep.setOnClickListener {
            startActivity(Intent(this, SleepActivity::class.java))
        }
        img_appointment.setOnClickListener {
            startActivity(Intent(this, Appointments::class.java))
        }
        img_fitness.setOnClickListener {
            startActivity(Intent(this, FitnessActivity::class.java))
        }
        img_food.setOnClickListener {
            startActivity(Intent(this, FoodActivity::class.java))
        }
        logout = findViewById<View>(R.id.logout_btn_home) as Button
        val intent = intent

        // Adding click listener to Log Out button.
        logout!!.setOnClickListener {
            //Finishing current activity on button click.
            finish()
            Toast.makeText(this, "Log Out Successful", Toast.LENGTH_LONG).show()
        }
    }
}