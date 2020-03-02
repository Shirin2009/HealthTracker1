package com.example.healthtracker

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.appointments.*
import java.text.SimpleDateFormat
import java.util.*

class Appointments : AppCompatActivity() {

    var databaseHelper: DatabaseHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appointments)
        databaseHelper = DatabaseHelper(this)


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
}
