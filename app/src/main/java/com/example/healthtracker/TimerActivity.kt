package com.example.healthtracker

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.timer.*

class TimerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.timer)

        val timer = MyCounter(10000, 1000)
        start.setOnClickListener { timer.start() }
        stop.setOnClickListener { timer.cancel() }

        //cancels the count down and returns to the home page
        cancel_button.setOnClickListener{
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
    //class to generate the counter
    inner class MyCounter(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onFinish() {
            println("You reached your daily goal!")
            tv.text = "You reached your daily goal!"
        }

        override fun onTick(millisUntilFinished: Long) {
            tv.textSize = 30f

            tv.text = (millisUntilFinished / 1000).toString() + ""
            println("Timer: " + millisUntilFinished / 1000)
        }
    }
}