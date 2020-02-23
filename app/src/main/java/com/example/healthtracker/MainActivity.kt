package com.example.healthtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.user_registration.*

class MainActivity : AppCompatActivity() {
    lateinit var dbHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper= DatabaseHelper(this)
        showHome()
        login_btn_main.setOnClickListener {
            Toast.makeText(this,"hi there!", Toast.LENGTH_SHORT).show()
            showLogin()

        }

        signup_txt_loin.setOnClickListener{
            Toast.makeText(this,"hi there!", Toast.LENGTH_SHORT).show()
            showRegistration()

        }
        signup_btn_main.setOnClickListener{
            Toast.makeText(this,"hi there!", Toast.LENGTH_SHORT).show()
            showRegistration()

        }
        save_registration.setOnClickListener{
            dbHelper.insertUserData(full_name_registration.text.toString(),email_registration.text.toString(),password_registration.text.toString())
            showHome()
        }

        login_btn_login.setOnClickListener{
            val email= login_email_txt_login.text.toString().trim()
            val password = password_txt_login.text.toString().trim()

            if( dbHelper.userPresents(login_email_txt_login.text.toString(),password_txt_login.text.toString()))
                Toast.makeText(this,"You are logged in.", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this,"Incorrect Email or password.", Toast.LENGTH_SHORT).show()

            if(email.isEmpty()){
                login_email_txt_login.error="Email required"
                login_email_txt_login.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                password_txt_login.error="Password required"
                password_txt_login.requestFocus()
                return@setOnClickListener
            }

        }
    }

    private fun showRegistration(){
        registration_layout.visibility = View.VISIBLE
        login_layout.visibility = View.GONE
        home_11.visibility = View.GONE
    }

    private fun showLogin(){
        registration_layout.visibility = View.GONE
        login_layout.visibility = View.VISIBLE
        home_11.visibility = View.GONE
    }

    private fun showHome(){
        registration_layout.visibility = View.GONE
        login_layout.visibility = View.GONE
        home_11.visibility = View.VISIBLE
    }
}
