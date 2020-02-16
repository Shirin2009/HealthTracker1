package com.example.healthtracker

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle



class LoginActivity:AppCompatActivity(), View.OnClickListener {
    private val activity = this@LoginActivity

    private lateinit var inputValidation: UserInputValidation
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // hiding the action bar
        supportActionBar!!.hide()

        // initializing the views
        initViews()

        // initializing the listeners
        initListeners()

        // initializing the objects
        initObjects()
    }


     //This method is to initialize views
    private fun initViews() {


    }


//This method is to initialize listeners
    private fun initListeners() {

    }


    // This method is to initialize objects to be used
    private fun initObjects() {

    }


    //This implemented method is to listen the click on view
    override fun onClick(v: View) {

    }


   //This method is to validate the input text fields and verify login credentials from SQLite
    private fun verifyFromSQLite() {


    }


   // This method is to empty all input edit text
    private fun emptyInputEditText() {

    }
}