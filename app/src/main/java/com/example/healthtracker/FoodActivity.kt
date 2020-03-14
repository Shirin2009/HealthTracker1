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
import kotlinx.android.synthetic.main.food.*
import java.text.SimpleDateFormat
import java.util.*

class FoodActivity : AppCompatActivity() {

    var formate = SimpleDateFormat(" dd MMM, YYYY", Locale.ENGLISH)
    val todayDate = Calendar.getInstance()
    val todayForm = formate.format(todayDate.time)
    val dbHelper = DatabaseHelper(this)
    val userID: Int = DatabaseHelper.currentUserID
    val types = arrayOf("Breakfast","Snack","Lunch","Dinner","Drink","Other")
    val listPie = ArrayList<PieEntry>()
    val listColors = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.food)

        mealTypePicker.minValue = 0
        mealTypePicker.maxValue = types.size -1
        mealTypePicker.displayedValues = types

        setPieChartData()

        dateBtnFood.setOnClickListener {
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR,year)
                selectedDate.set(Calendar.MONTH,month)
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                dateBtnFood.text = formate.format(selectedDate.time)
                Toast.makeText(this, "Date selected " + dayOfMonth
                        + " " + month + " " + year, LENGTH_SHORT).show()
            },
                now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }

        doneBtnFood.setOnClickListener {
            val calsBur = Integer.parseInt(calsCons.text.toString())
            val date = dateBtnFood.text.toString()
            val type = types[mealTypePicker.value]

            if(date == "Enter date"){
                Toast.makeText(this, "Please enter a date", LENGTH_SHORT).show()
            }
            else{
                dbHelper.insertFoodData(userID, calsBur, date, type)
                Toast.makeText(this,"Data added sucessfuly", LENGTH_SHORT).show()
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
            if(dbHelper.showFoodData(userID, todayForm, types[x]) >= 0){
                listPie.add(PieEntry(dbHelper.showFoodData(userID, todayForm, types[x]).toFloat(), types[x]))
            }
        }

        val pieDataSet = PieDataSet(listPie, "")
        pieDataSet.colors = listColors

        val pieData = PieData(pieDataSet)

        foodPieChart.data = pieData

        foodPieChart.holeRadius = 25F
        foodPieChart.setTransparentCircleAlpha(0)
        foodPieChart.centerText = "Meal types"
        foodPieChart.setCenterTextSize(10F)
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
