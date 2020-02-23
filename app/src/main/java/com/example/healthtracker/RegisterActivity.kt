package com.example.healthtracker

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class RegisterActivity :AppCompatActivity() {

    var dbHelper: DatabaseHelper?=null
    var NameHolder: String? = null
    var Email: EditText? = null
    var Register: Button? = null
    var Password: EditText? = null
    var Name: EditText? = null
    var PasswordHolder: String? = null
    var EmailHolder: String? = null
    var EditTextEmptyHolder: Boolean? = null
    var sqLiteDatabaseObj: SQLiteDatabase? = null
    var SQLiteDataBaseQueryHolder: String? = null
    var F_Result = "Not_Found"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fitness)

        Register = findViewById<View>(R.id.save_registration) as Button
        Email = findViewById<View>(R.id.email_registration) as EditText
        Password = findViewById<View>(R.id.password_registration) as EditText
        Name = findViewById<View>(R.id.full_name_registration) as EditText
        dbHelper = DatabaseHelper(this)

        Register!!.setOnClickListener{
            SQLiteDataBaseBuild()
            // Creating SQLite table if dose n't exists.
            SQLiteTableBuild()
            // Checking EditText is empty or Not.
           // CheckEditTextStatus()
            // Method to check Email is already exists or not.
            CheckingEmailAlreadyExistsOrNot()
            // Empty EditText After done inserting process.
            EmptyEditTextAfterDataInsert()
        }

    }
    // SQLite database build method
   fun SQLiteDataBaseBuild() {
        sqLiteDatabaseObj = SQLiteDatabase.openOrCreateDatabase(
            DatabaseHelper.DATABASE_NAME,
           // Context.MODE_PRIVATE,
            null
        )
    }

    // SQLite table build method.
   fun SQLiteTableBuild() {
        sqLiteDatabaseObj!!.execSQL("CREATE TABLE IF NOT EXISTS "
                + DatabaseHelper.TABLE_USER.toString()
                + "(" + DatabaseHelper.COLUMN_ID.toString() +
                " PRIMARY KEY AUTOINCREMENT NOT NULL, " + DatabaseHelper.COLUMN_NAME.toString()
                + " VARCHAR, " + DatabaseHelper.COLUMN_EMAIL.toString()
                + " VARCHAR, " + DatabaseHelper.COLUMN_USER_PASSWORD.toString() + " VARCHAR);")
    }

    // Insert data into SQLite database method.
    fun InsertDataIntoSQLiteDatabase() {
        if (EditTextEmptyHolder == true) { // SQLite query to insert data into table.
            SQLiteDataBaseQueryHolder =
                "INSERT INTO " + DatabaseHelper.TABLE_USER.toString() +
                        " (name,email,password) VALUES('" + NameHolder.toString() + "', '" + EmailHolder.toString() + "', '" + PasswordHolder.toString() + "');"
            // Executing query.
            sqLiteDatabaseObj!!.execSQL(SQLiteDataBaseQueryHolder)
            // Closing SQLite database object.
            sqLiteDatabaseObj!!.close()

            // Printing toast message after done inserting.
            Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_LONG)
                .show()
        } else { // Printing toast message if any of EditText is empty.
            Toast.makeText(
                this,
                "Please Fill All The Required Fields.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // Empty edittext after done inserting process method
    fun EmptyEditTextAfterDataInsert() {
        Name!!.text.clear()
        Email!!.text.clear()
        Password!!.text.clear()
    }


    /** Method to check EditText is empty or Not
    private fun CheckEditTextStatus() {
        NameHolder = Name!!.text.toString()
        EmailHolder = Email!!.text.toString()
        PasswordHolder = Password!!.text.toString()
        EditTextEmptyHolder =if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
        {
            false
        } else
        { true
        }
    }*/


// Checking Email is already exists or not.
  fun CheckingEmailAlreadyExistsOrNot() {
    sqLiteDatabaseObj= dbHelper?.writableDatabase
        // Adding search email query to cursor.
       val cursor = sqLiteDatabaseObj!!.query(
            DatabaseHelper.TABLE_USER,
            null,
            " " + DatabaseHelper.COLUMN_EMAIL.toString() + "=?",
            arrayOf(EmailHolder),
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst()
                // If Email is already exists then Result variable value set as Email Found.
                F_Result = "Email Found"
                // Closing cursor.
                cursor.close()
            }
        }
        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult()
    }

     fun CheckFinalResult() {
        if (F_Result.equals(
                "Email Found",
                ignoreCase = true
            )
        ) { // If email is exists then toast msg will display.
            Toast.makeText(this, "Email Already Exists", Toast.LENGTH_LONG).show()
        } else { // If email already dose n't exists then user registration details will entered to SQLite database.
            InsertDataIntoSQLiteDatabase()
        }
        F_Result = "Not_Found"
    }


}



