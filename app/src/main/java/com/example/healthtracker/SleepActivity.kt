package com.example.healthtracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SleepActivity : AppCompatActivity() {

    var databaseHelper: DatabaseHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sleep)
        databaseHelper = DatabaseHelper(this)


    }
}
