package com.example.healthtracker

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.sleep.*
import java.text.SimpleDateFormat
import java.util.*

class SleepActivity : AppCompatActivity() {

    @SuppressLint("SimpleDateFormat", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sleep)

        val dbHelper = DatabaseHelper(this)
        val save = findViewById<View>(R.id.button) as Button
        val inputHours = findViewById<View>(R.id.editText) as EditText
       // val displaySleep =findViewById<View>(R.id.displaySleep)as EditText

        save.setOnClickListener{
            val userID: Int = DatabaseHelper.currentUserID
            val hours: String = inputHours.text.toString().trim()
            val calender: Calendar = Calendar.getInstance()
            val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
            val date: String = simpleDateFormat.format(calender.time)

            when {
                hours.isEmpty() -> {
                    inputHours.error="Field required"
                    inputHours.requestFocus()

                    return@setOnClickListener
                }
                dbHelper.hoursSleptExists(userID,date) -> {
                    inputHours.error="Hours slept for $date have already been added"
                    Toast.makeText(this, "You have slept $hours hours on $date", Toast.LENGTH_LONG).show()
                    inputHours.requestFocus()
                    return@setOnClickListener

                }
                else -> {
                    dbHelper.insertSleepData(userID,hours.toInt(),date)
                   // Toast.makeText(this, "You have slept $hours hours on $date", Toast.LENGTH_LONG).show()

                }
            }

            // Printing toast message after done inserting.
            Toast.makeText(this, "Night Saved Successfully: $hours hours on $date", Toast.LENGTH_LONG).show()

            // Empty EditText after data insert
            inputHours.text.clear()
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
