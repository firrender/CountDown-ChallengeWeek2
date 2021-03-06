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
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.black
import com.example.androiddevchallenge.ui.theme.black40
import com.example.androiddevchallenge.ui.theme.typography
import com.example.androiddevchallenge.ui.theme.white
import com.example.androiddevchallenge.ui.theme.yellow
import com.example.androiddevchallenge.ui.vm.TimerViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp(TimerViewModel())
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp(timer: TimerViewModel) {

    val time by timer.time.observeAsState("00:00:00")
    val isRunning by timer.runs.observeAsState(false)
    val totalTime by timer.totals.observeAsState(1F)
    var s = 0
    var m = 0
    var h = 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "TAKE YOUR TIME",
                        color = white,
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        shape = RoundedCornerShape(12.dp).copy(
                            topStart = ZeroCornerSize,
                            topEnd = ZeroCornerSize
                        )
                    ),
                backgroundColor = yellow
            )
        },
        content = {
            Surface(color = MaterialTheme.colors.background) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.weight(1f))
                        h = numbers(isRunning, true)
                        Text(text = " : ", style = MaterialTheme.typography.h2, color = if (isRunning) black40 else black, modifier = Modifier.padding(top = 16.dp))
                        m = numbers(isRunning, false)
                        Text(text = " : ", style = MaterialTheme.typography.h2, color = if (isRunning) black40 else black, modifier = Modifier.padding(top = 16.dp))
                        s = numbers(isRunning, false)
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    Box(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth()
                            .height(400.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(progress = totalTime, strokeWidth = 6.dp, modifier = Modifier.fillMaxSize(), color = yellow)
                        Text(text = time, style = MaterialTheme.typography.h2, color = black)
                    }

                    Row(modifier = Modifier.fillMaxWidth()) {
                        FloatingActionButton(
                            onClick = { timer.onCancel() },
                            backgroundColor = yellow,
                            modifier = Modifier
                                .width(80.dp)
                                .height(80.dp),
                            content = {
                                Text(
                                    text = "CANCEL",
                                    style = MaterialTheme.typography.h6,
                                    color = white,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        FloatingActionButton(
                            onClick = { timer.onStart(s.toLong()*1000 + m.toLong()*1000*60 + h.toLong()*1000*60*60) },
                            backgroundColor = yellow,
                            modifier = Modifier
                                .width(80.dp)
                                .height(80.dp),
                            content = {
                                Text(
                                    text = "START",
                                    style = MaterialTheme.typography.h6,
                                    color = white,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun numbers(isRunning: Boolean, isHours: Boolean): Int {

    var nums = 0
    var realNums = ""
    val maxNums = if (isHours) 12 else 59
    var offset by remember { mutableStateOf(0f) }

    Box(
        Modifier
            .padding(10.dp)
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollableState { delta ->
                    offset += delta
                    delta
                },
                enabled = !isRunning
            )
            .background(white),
        contentAlignment = Alignment.Center
    ) {
        nums = offset.toInt() / 10
        if (nums > maxNums) {
            nums = 0
            offset = 0f
        } else if (nums < 0) {
            nums = maxNums
            offset = if (isHours) 120f else 590f
        }
        realNums = if (nums >= 10) nums.toString() else "0$nums"
        Text(text = realNums, style = typography.h1, color = if (isRunning) black40 else black, textAlign = TextAlign.Center)
    }
    return nums
}

@Preview("MainActivity")
@Composable
fun LightPreview() {
    MyTheme {
        MyApp(TimerViewModel())
    }
}
