package com.example.healthtracker

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
   // var EmailHolder: String? = null
    //var Email: TextView? = null
    var LogOUT: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

      //  Email = findViewById<View>(R.id.login_email_txt_main) as TextView
        LogOUT = findViewById<View>(R.id.logout_btn_home) as Button
        val intent = intent

        // Receiving User Email Send By MainActivity.
      //  EmailHolder = intent.getStringExtra(MainActivity.UserEmail)
        // Setting up received email to TextView.
      //  Email!!.text = Email!!.text.toString() + EmailHolder

        // Adding click listener to Log Out button.
        LogOUT!!.setOnClickListener {
            //Finishing current DashBoard activity on button click.
            finish()
            Toast.makeText(this, "Log Out Successful", Toast.LENGTH_LONG).show()
        }
    }
}