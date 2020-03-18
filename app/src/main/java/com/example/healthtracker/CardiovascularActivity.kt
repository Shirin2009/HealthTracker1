package com.example.healthtracker

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class CardiovascularActivity : AppCompatActivity() {

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cardiovascular)

        val dbHelper = DatabaseHelper(this)
        val save = findViewById<View>(R.id.button2) as Button
        val inputHeartRate = findViewById<View>(R.id.editText2) as EditText
        val daysAverageHeartRate = findViewById<View>(R.id.textView5) as TextView
        val weeklyAverageHeartRate = findViewById<View>(R.id.textView6) as TextView
        val monthlyAverageHeartRate = findViewById<View>(R.id.textView8) as TextView
        val userID: Int = DatabaseHelper.currentUserID

        save.setOnClickListener {
            val heartRate: String = inputHeartRate.text.toString().trim()
            if (heartRate.isEmpty()) {
                inputHeartRate.error = "Field required"
                inputHeartRate.requestFocus()

                return@setOnClickListener
            } else {
                dbHelper.insertCardiovascularData(userID, heartRate.toInt())
                Toast.makeText(this, "Your heart rate is now $heartRate and has been saved", Toast.LENGTH_LONG).show()
                daysAverageHeartRate.text = dbHelper.todaysAverageHeartRate(userID).toString()
                weeklyAverageHeartRate.text = dbHelper.weeklyAverageHeartRate(userID).toString()
                monthlyAverageHeartRate.text = dbHelper.monthlyAverageHeartRate(userID).toString()
            }
        }

        daysAverageHeartRate.text = dbHelper.todaysAverageHeartRate(userID).toString()
        weeklyAverageHeartRate.text = dbHelper.weeklyAverageHeartRate(userID).toString()
        monthlyAverageHeartRate.text = dbHelper.monthlyAverageHeartRate(userID).toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.actionbar, menu)
        return true
    }

    //function for changing the application background
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.itemId

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
