package com.example.healthtracker

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity :AppCompatActivity() {

    var databaseHelper: DatabaseHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_registration)
        databaseHelper = DatabaseHelper(this)
        initTextViewLogin()
        initViews()




        //This method is used to validate input given by user
        fun validate() {

        }
    }

    //this method is used to connect XML views to its Objects
    private fun initViews() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //this method used to set Login TextView click event
    private fun initTextViewLogin() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}