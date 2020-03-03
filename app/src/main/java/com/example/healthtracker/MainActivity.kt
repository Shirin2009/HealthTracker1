package com.example.healthtracker

import android.content.Intent
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
            showLogin()
        }

        signup_txt_loin.setOnClickListener{
            showRegistration()

        }

        signup_btn_main.setOnClickListener{
            showRegistration()

        }

        save_registration.setOnClickListener{
            val email= email_registration.text.toString().trim()
            val password = password_registration.text.toString().trim()
            val name:String=full_name_registration.text.toString().trim()

            dbHelper.insertUserData(full_name_registration.text.toString(),email_registration.text.toString(),password_registration.text.toString())


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

            if(name.isEmpty()){
                password_txt_login.error="Name required"
                password_txt_login.requestFocus()
                return@setOnClickListener
            }
            showHome()

        }

        cancel_registration.setOnClickListener{
            showHome()
        }

        login_btn_login.setOnClickListener{
            val email= login_email_txt_login.text.toString().trim()
            val password = password_txt_login.text.toString().trim()

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
           if(dbHelper.userPresents(login_email_txt_login.text.toString(), password_txt_login.text.toString())) {
               startActivity(Intent(this, HomeActivity::class.java))
           }

            else
              Toast.makeText(this,"Incorrect Email or password.", Toast.LENGTH_SHORT).show()
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
