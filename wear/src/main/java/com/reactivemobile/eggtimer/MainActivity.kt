package com.reactivemobile.eggtimer

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.wearable.activity.WearableActivity
import androidx.wear.activity.ConfirmationActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : WearableActivity() {

    lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_hard_button.setOnClickListener { startTimer(480000) }
        start_soft_button.setOnClickListener { startTimer(240000) }

        // Enables Always-on
        setAmbientEnabled()
    }

    private fun startTimer(time: Long) {
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }

        countDownTimer = object : CountDownTimer(time, 1000) {
            override fun onFinish() {
                showRemainingTime(0)
                val intent = Intent(this@MainActivity, ConfirmationActivity::class.java).apply {
                    putExtra(
                        ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                        ConfirmationActivity.SUCCESS_ANIMATION
                    )
                    putExtra(ConfirmationActivity.EXTRA_MESSAGE, getString(R.string.timer_complete))
                }
                startActivity(intent)
            }

            override fun onTick(remainingTime: Long) {
                showRemainingTime(remainingTime)
//                sun_image.
            }
        }

        countDownTimer.start()
    }

    private fun showRemainingTime(remainingTime: Long) {
        timer_view.text = formatTime(remainingTime)
    }

    private fun formatTime(millis: Long): String {
        val totalSeconds = millis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60

        return String.format("%02d:%02d", minutes, seconds)
    }
}
