package com.example.healthtracker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewParent
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.appointments.*
import java.text.SimpleDateFormat
import java.util.*

class Appointments : AppCompatActivity() {

    var databaseHelper: DatabaseHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appointments)
        //calling database
        databaseHelper = DatabaseHelper(this)

        //book from available appointments
        val slots = arrayOf("", "Mon 9:30","Mon 9:45","Mon 10:30","Mon 11:45",
            "Tue 13:00", "Tue 13:20","Tue 15:00",
            "Tue 16:10", "Wed 12:00","Thu 9:00",
            "Thu 12:00","Thu 13:00", "Fri 10:15")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, slots)

        //attached arrayAdapter to spinner
         sp_option.adapter = arrayAdapter

        sp_option.onItemSelectedListener = object:
        AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                p2: Int,
                p3: Long
            ) {
                next_appoint_txt.text = slots[p2]
            }

        }

        //calender
        val dateText= findViewById<TextView>(R.id.date_txt)
        val pickdate= findViewById<View>(R.id.pickDate_btn)
        val cal= Calendar.getInstance()
        val pickTime = findViewById<Button>(R.id.pick_time_btn)
        val timeText = findViewById<TextView>(R.id.time_txt)

        //function to pick date
        pickdate.setOnClickListener(View.OnClickListener {
            val date = object:DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    cal.set(Calendar.YEAR,year)
                    cal.set(Calendar.MONTH,month)
                    cal.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                  // dateText.text=SimpleDateFormat("MM/dd/yyyy",Locale.UK).format(cal.getTime())
                }

            }
            DatePickerDialog(this,date,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        })

        //submit appointment
        btnSubmit.setOnClickListener{
            dateText.text=SimpleDateFormat("MM/dd/yyyy",Locale.UK).format(cal.getTime())
            timeText.text = SimpleDateFormat("HH:mm").format(cal.time)
        }

        //function to pick time
        pickTime.setOnClickListener{
           // val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker: TimePicker?, hour: Int, minute: Int ->
                cal.set(Calendar.HOUR_OF_DAY,hour)
                cal.set(Calendar.MINUTE,minute)
               // timeText.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
        TimePickerDialog(this,timeSetListener,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()
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
        if (id == R.id.red) {
            //red
            window.decorView.setBackgroundColor(Color.parseColor("#C48282"))
            return true
        }
        if (id == R.id.limeGreen) {
            //lime green
            window.decorView.setBackgroundColor(Color.parseColor("#32CD32"))
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
