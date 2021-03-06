package com.example.healthtracker

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
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
            startActivity(Intent(this, AppointmentsActivity::class.java))
        }
        img_fitness.setOnClickListener {
            startActivity(Intent(this, FitnessActivity::class.java))
        }
        img_food.setOnClickListener {
            startActivity(Intent(this, FoodActivity::class.java))
        }
        img_cardiovascular.setOnClickListener {
            startActivity(Intent(this, CardiovascularActivity::class.java))
        }

        //button to link the home page to the stepCounter activity
        step_counter_btn.setOnClickListener{
            startActivity(Intent(this, TimerActivity::class.java))
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.actionbar, menu)
        return true
    }

    //function for changing the application background
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.yellow) {
            //yellow
            window.decorView.setBackgroundColor(Color.parseColor("#FFC20A"))
            return true
        }
        if (id == R.id.blue) {
            //blue
            window.decorView.setBackgroundColor(Color.parseColor("#1A85FF"))
            return true
        }
        if (id == R.id.lightGreen) {
            //red
            window.decorView.setBackgroundColor(Color.parseColor("#e6ffe6"))
            return true
        }
        if (id == R.id.pink) {
            //lime green
            window.decorView.setBackgroundColor(Color.parseColor("#ff99ff"))
            return true
        }
        if (id == R.id.lightBlue) {
            //light blue
            window.decorView.setBackgroundColor(Color.parseColor("#ADD8E6"))
            return true
        }

        return super.onOptionsItemSelected(item)

    }

}