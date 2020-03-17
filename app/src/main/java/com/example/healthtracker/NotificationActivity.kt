package com.example.healthtracker

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.appointments.*
import kotlinx.android.synthetic.main.notification.*
import java.text.SimpleDateFormat
import java.util.*

class NotificationActivity: AppCompatActivity() {

    //for showing and hiding notifications
    lateinit var notificationManager:NotificationManager

    //for enable and disable notifications
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder :Notification.Builder
    //to create  the notification channel we need to include the package id
    private val channelID = "com.example.healthtracker"
    private val description = "Your appointment booked."


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification)

        //initialise notificationManager
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager

        btnNotify.setOnClickListener{

            //val intent = Intent(this,NotificationActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
            val contentView = RemoteViews(packageName,R.layout.notification_layout)
            contentView.setTextViewText(R.id.tv_title, "Health Tracker")
            contentView.setTextViewText(R.id.tv_content, "Your appointment has been booked.")
        //creating notification by passing the channel id and description
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = NotificationChannel(channelID,description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this, channelID)

                    .setContent(contentView)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                        //we cant directly access the mipmap ic-launcher so we use BitmapFactory
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.mipmap.ic_launcher))
                //setting the intent as pending intent
                    .setContentIntent(pendingIntent)
            }else{

                builder = Notification.Builder(this)
                    .setContent(contentView)
                    .setSmallIcon(R.drawable.logo)
                    //we cant directly access the mipmap ic-launcher so we use BitmapFactory
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.logo))
                    //setting the intent as pending intent
                    .setContentIntent(pendingIntent)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                notificationManager.notify(1234, builder.build())
            }
        }
    }
}