package com.example.androiddevchallenge.ui.vm

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {

    private lateinit var timer: CountDownTimer
    private val realTime = MutableLiveData("")
    val time: LiveData<String> = realTime

    fun onTimeChange(newTime: String) {
        realTime.value = newTime
    }

    fun onTimerStart(time: Long) {
        timer = object : CountDownTimer(time, 500) {
            override fun onTick(millisUntilFinished: Long) {
                var minutes = (millisUntilFinished / 1000 / 60).toString()
                var seconds = (millisUntilFinished / 1000 % 60).toString()
                if (minutes.length == 1) minutes = "0$minutes"
                if (seconds.length == 1) seconds = "0$seconds"
                onTimeChange("$minutes:$seconds")
            }

            override fun onFinish() {
                onTimeChange("")
            }
        }
        timer.start()
    }

    fun onTimerFinish() {
        timer.cancel()
        onTimeChange("")
    }
}