/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.vm

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {

    private lateinit var timer: CountDownTimer
    private val realTime = MutableLiveData("00:00:00")
    private val isRunning = MutableLiveData(false)
    private val totalTime = MutableLiveData(1F)
    val time: LiveData<String> = realTime
    val runs: LiveData<Boolean> = isRunning
    val totals: LiveData<Float> = totalTime

    fun onChange(time: String) {
        realTime.value = time
    }

    fun onRunning(run: Boolean) {
        isRunning.value = run
    }

    fun onTotalTime(all: Float) {
        totalTime.value = all
    }

    fun onCancel() {
        timer.cancel()
        onChange("00:00:00")
        onRunning(false)
    }

    fun onStart(time: Long) {
        timer = object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                onTotalTime(if (time == 0L) 1F else millisUntilFinished.toFloat()/time.toFloat())
                var s = (millisUntilFinished / 1000 % 60).toString()
                var m = (millisUntilFinished / 1000 / 60  % 60).toString()
                var h = (millisUntilFinished / 1000 / 60 / 60).toString()
                if (s.length == 1) s = "0$s"
                if (m.length == 1) m = "0$m"
                if (h.length == 1) h = "0$h"
                onChange("$h:$m:$s")
                onRunning(true)
            }

            override fun onFinish() {
                onChange("00:00:00")
                onRunning(false)
                onTotalTime(1F)
            }
        }
        timer.start()
    }
}
