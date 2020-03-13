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
    private lateinit var dbHelper: DatabaseHelper
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

        //this function saves the new user
        save_registration.setOnClickListener{
            val email= email_registration.text.toString().trim()
            val password = password_registration.text.toString().trim()
            val name  = full_name_registration.text.toString().trim()
            val drName = dr_name_registration.text.toString()
            val age = age_registration.text.toString()

            //warn the user for the required information if they don't put any
            when {
                email.isEmpty() -> {
                    login_email_txt_login.error="Email required"
                    login_email_txt_login.requestFocus()
                    return@setOnClickListener
                }
                password.isEmpty() -> {
                    password_txt_login.error="Password required"
                    password_txt_login.requestFocus()
                    return@setOnClickListener
                }
                name.isEmpty() -> {
                    full_name_registration.error="Name required"
                    full_name_registration.requestFocus()
                    return@setOnClickListener
                }
                age.isEmpty()->{
                    age_registration.error="Age required"
                    age_registration.requestFocus()
                    return@setOnClickListener
                }
                drName.isEmpty()->{
                    dr_name_registration.error="DR name required"
                    dr_name_registration.requestFocus()
                    return@setOnClickListener
                }

                //calls a method from database to check if user already has been registered
                dbHelper.userExists(email,password) -> {
                    login_email_txt_login.error="This email is already being used"
                    login_email_txt_login.requestFocus()
                    return@setOnClickListener
                }
                //if its a new user it creates an account by inserting user's details
                else -> {
                    dbHelper.insertUserData(full_name_registration.text.toString(),
                        age_registration.text.toString(),
                        dr_name_registration.text.toString(),
                        email_registration.text.toString(),
                        password_registration.text.toString())
                }
            }
            showHome()
        }

        //when user cancels the registration it goes back to the login home page
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
           if(dbHelper.userExists(login_email_txt_login.text.toString(), password_txt_login.text.toString())) {
               dbHelper.insertAppointmentData(0,"Coronavirus", "03/18/2020", "12:00", "2a", "Harvey Specter", 0)
               dbHelper.insertAppointmentData(0,"Coronavirus2", "03/18/2020", "13:00", "3b", "Sam Winchester", 0)
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
