package com.example.healthtracker

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var dbHelper: DatabaseHelper?=null
    var LoginButton:Button?=null
    var EditTextEmptyHolder: Boolean? = null
    var sqLiteDatabaseObj: SQLiteDatabase? = null
    var RegisterButton: Button? = null
    var Email: EditText? = null
    var Password: EditText? = null
    var EmailHolder: String? = null
    val cursor: Cursor? = null
    var PasswordHolder: String? = null
    var TempPassword = "NOT_FOUND"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      LoginButton=findViewById<View>(R.id.login_btn_main)as Button
        //RegisterButton=findViewById<View>(R.id.save_registration)as Button
        Email= findViewById<View>(R.id.login_email_txt_main)as EditText

        Password = findViewById<View>(R.id.password_txt_main) as EditText
        dbHelper = DatabaseHelper(this)
        //Adding click listener to log in button.

        //link the main activity to the user activity
        login_btn_main.setOnClickListener {
            val email= login_email_txt_main.text.toString().trim()
            val password = password_txt_main.text.toString().trim()
            // Calling EditText is empty or no method.
           // checkEditTextStatus()
            // Calling login method.
           // loginFunction()
            if(email.isEmpty()){
                login_email_txt_main.error="Email required"
                login_email_txt_main.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                password_txt_main.error="Password required"
                password_txt_main.requestFocus()
                return@setOnClickListener
            }

        }

        signup_btn_main.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

    }

   /* private fun checkEditTextStatus() {
        EmailHolder=Email!!.text.toString()
        PasswordHolder=password_txt_main!!.text.toString()

        EditTextEmptyHolder=
            if (TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
            {
                false
            }else{
                true
            }
    }

    fun loginFunction(){
        if (EditTextEmptyHolder!!){
            sqLiteDatabaseObj= dbHelper?.writableDatabase
          val cursor =sqLiteDatabaseObj!!.query(
                    DatabaseHelper.TABLE_USER,
                null,
                " " +DatabaseHelper.COLUMN_EMAIL.toString() +"=?",
                arrayOf(EmailHolder),
                null,
                null,
                null
                    )
            while(cursor.moveToNext()){
                if (cursor.isFirst()){
                    cursor.moveToFirst()
                    TempPassword=cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_PASSWORD))
                    cursor.close()

                }
            }
            CheckFinalresult()
        }else{
            Toast.makeText(this,"Please enter username or password.",Toast.LENGTH_LONG).show()
        }
    }

    private fun CheckFinalresult() {

        if (TempPassword.equals(PasswordHolder,ignoreCase = true)){
            Toast.makeText(this,"incorrect username or password",Toast.LENGTH_LONG).show()
            // Going to Dashboard activity after login success message.
            val intent = Intent(this,HomeActivity::class.java)
            intent.putExtra(UserEmail,EmailHolder)
            startActivity(intent)
        }else{
            Toast.makeText(this,"incorrect username or passwor, Please try again.",
                Toast.LENGTH_LONG).show()
        }
        TempPassword="NOT_FOUND"
    }

companion object{
    const val UserEmail=""
}*/
}
