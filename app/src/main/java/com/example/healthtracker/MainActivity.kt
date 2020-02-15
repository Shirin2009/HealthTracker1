package com.example.healthtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.user_registration.*
import userPackage.User


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //link the main activity to the user activity
        login_btn.setOnClickListener {

            startActivity(Intent(this, User::class.java))
         //pop up message when user logged in
        Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()
    }
        //get reference t all views
signup_btn.setOnClickListener{
    val intent = Intent(this, SignUpActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(intent)
    finish()

      }
    }

}
