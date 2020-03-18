package com.example.healthtracker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.appointments.*
import java.text.SimpleDateFormat
import java.util.*


class AppointmentsActivity : AppCompatActivity() {

    var databaseHelper: DatabaseHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appointments)
        //calling database
        databaseHelper = DatabaseHelper(this)

        var appointments = databaseHelper!!.getAppointments(0,0)

        //book from available appointments
        val slots = mutableListOf("")
        if (appointments != null) {
            for (i in 0 until appointments.count()) {
                slots.add(appointments[i].date + " " + appointments[i].time)
            }
        }
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, slots)

        //attached arrayAdapter to spinner
         sp_option.adapter = arrayAdapter

        sp_option.onItemSelectedListener = object:
        AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                next_appoint_txt.text = slots[0]
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    next_appoint_txt.text = slots[p2]
                    databaseHelper!!.updateAppointment(DatabaseHelper.currentUserID, appointments!![p2-1].id)
                }

            }
        }

        //calender
        val dateText= findViewById<TextView>(R.id.date_txt)
        val pickdate= findViewById<View>(R.id.pickDate_btn)
        val cal= Calendar.getInstance()
        val pickTime = findViewById<Button>(R.id.pick_time_btn)
        val timeText = findViewById<TextView>(R.id.time_txt)

        //function to pick date
        pickdate.setOnClickListener {
            val date = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    cal.set(Calendar.YEAR,year)
                    cal.set(Calendar.MONTH,month)
                    cal.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                    // dateText.text=SimpleDateFormat("MM/dd/yyyy",Locale.UK).format(cal.getTime())
                }
            DatePickerDialog(this,date,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        val description = findViewById<View>(R.id.describe_multiline) as EditText

        //submit appointment
        btnSubmit.setOnClickListener{
            val date = SimpleDateFormat("MM/dd/yyyy",Locale.UK).format(cal.time)
            val time = SimpleDateFormat("HH:mm").format(cal.time)
            dateText.text = date
            timeText.text = time

            databaseHelper!!.insertAppointmentData(DatabaseHelper.currentUserID,description.text.toString().trim(),date,time,"?","?",1)
        }

        //function to pick time
        pickTime.setOnClickListener{
           // val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ _, hour: Int, minute: Int ->
                cal.set(Calendar.HOUR_OF_DAY,hour)
                cal.set(Calendar.MINUTE,minute)
               // timeText.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
        TimePickerDialog(this,timeSetListener,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()
        }
        btnSave.setOnClickListener{
            startActivity(Intent(this, NotificationActivity::class.java))
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
