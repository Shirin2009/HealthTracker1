package com.example.healthtracker

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class LoginActivity:AppCompatActivity(), View.OnClickListener {
    //private val activity = this@LoginActivity
    var btnLogin: Button? = null
    var edtUsername: EditText? = null
    var edtPassword: EditText? = null
    var dbHelper: DatabaseHelper? = null
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        dbHelper= DatabaseHelper(this)
    }

    override fun onClick(v: View?) {
    }


}