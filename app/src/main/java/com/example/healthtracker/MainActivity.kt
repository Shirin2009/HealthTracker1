package com.example.healthtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home.*
import kotlinx.android.synthetic.main.user_registration.*


class MainActivity : AppCompatActivity() {

    lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

dbHelper= DatabaseHelper(this)

        //link the main activity to the user activity
        login_btn.setOnClickListener {
           // startActivity(Intent(this, UserModel::class.java))
       var status:String= if(login_email_txt.toString().equals("johngoodwin@gmail.com")
               && password_txt.text.toString().equals("Password")) "Login Successful" else "Login Failed"
            //pop up message when user logged in
            Toast.makeText(this,status,Toast.LENGTH_SHORT).show()
           // Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()
            //dbHelper = DatabaseHelper(this)

        }


    }

}
