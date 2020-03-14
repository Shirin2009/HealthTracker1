package com.example.healthtracker

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.fitness.*
import java.text.SimpleDateFormat
import java.util.*

class FitnessActivity : AppCompatActivity() {
    var formate = SimpleDateFormat(" dd MMM, YYYY", Locale.ENGLISH)
    val todayDate = Calendar.getInstance()
    val todayForm = formate.format(todayDate.time)
    val dbHelper = DatabaseHelper(this)
    val userID: Int = DatabaseHelper.currentUserID
    val types = arrayOf("Walking","Running","Swimming","Cycling","Sports","Other")
    val listPie = ArrayList<PieEntry>()
    val listColors = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fitness)

        activityTypePicker.minValue = 0
        activityTypePicker.maxValue = types.size -1
        activityTypePicker.displayedValues = types

        setPieChartData()

        dateBtnFit.setOnClickListener {
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{_, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR,year)
                selectedDate.set(Calendar.MONTH,month)
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                dateBtnFit.text = formate.format(selectedDate.time)
                Toast.makeText(this, "Date selected " + dayOfMonth
                        + " " + month + " " + year, LENGTH_SHORT).show()
            },
                now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }

        doneBtnFit.setOnClickListener {
            val calsBur = Integer.parseInt(calsBur.text.toString())
            val date = dateBtnFit.text.toString()
            val type = types[activityTypePicker.value]


            if(date == "Enter date"){
                Toast.makeText(this, "Please enter a date", LENGTH_SHORT).show()
            }
            else{
                dbHelper.insertFitnessData(userID, calsBur, date, type)
                Toast.makeText(this,"Data added sucessfuly",LENGTH_SHORT).show()
                recreate()
            }
        }
    }

    private fun setPieChartData() {
        listColors.add(Color.BLUE)
        listColors.add(Color.RED)
        listColors.add(Color.GRAY)
        listColors.add(Color.GREEN)
        listColors.add(Color.CYAN)
        listColors.add(Color.MAGENTA)

        for (x in 0..5){
            if(dbHelper.showFitnessData(userID, todayForm, types[x]) >= 0){
                listPie.add(PieEntry(dbHelper.showFitnessData(userID, todayForm, types[x]).toFloat(), types[x]))
            }
        }

        val pieDataSet = PieDataSet(listPie, "")
        pieDataSet.colors = listColors

        val pieData = PieData(pieDataSet)

        fitPieChart.data = pieData

        fitPieChart.holeRadius = 25F
        fitPieChart.setTransparentCircleAlpha(0)
        fitPieChart.centerText = "Activity types"
        fitPieChart.setCenterTextSize(10F)
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