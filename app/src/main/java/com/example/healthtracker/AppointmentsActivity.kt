package com.example.healthtracker

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.appointments.*
import java.text.SimpleDateFormat
import java.util.*

class AppointmentsActivity : AppCompatActivity() {

    var databaseHelper: DatabaseHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appointments)
        databaseHelper = DatabaseHelper(this)

        //show appointment data
        val textView: TextView = findViewById(R.id.textView2) as TextView
        val appointments:MutableList<Appointment>? = databaseHelper!!.getAppointments(DatabaseHelper.currentUserID,0)
        textView.text = appointments!![1].doctorName

        //calender
        val calendarvalue=findViewById<View>(R.id.date_txt)
        val pickdate= findViewById<View>(R.id.pickDate_btn)
        val cal= Calendar.getInstance()

        pickdate.setOnClickListener(View.OnClickListener {
            val date = object:DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    cal.set(Calendar.YEAR,year)
                    cal.set(Calendar.MONTH,month)
                    cal.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                    // calendarvalue.text=SimpleDateFormat("MM/dd/yyyy",Locale.UK).format(cal.getTime())
                }

            }
            DatePickerDialog(this,date,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        })

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
