package com.example.healthtracker

import android.provider.BaseColumns

///user class
class UserModel(val id: Int = -1,
                val username: String,
                val password: String,
                val fullName: String,
                val email:String)
{
    class DBContract:BaseColumns{
        companion object{
            val TABLE_NAME = "users"
            val COLUMN_ID = "user id"
            val COLUMN_NAME = "name"

        }
    }

}