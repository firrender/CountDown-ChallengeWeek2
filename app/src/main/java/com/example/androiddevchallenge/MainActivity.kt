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
import android.os.Handler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.*
import com.example.androiddevchallenge.ui.vm.TimerViewModel
import kotlinx.coroutines.delay

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

    var mins = ""
    var isRunning by rememberSaveable { mutableStateOf(false) }
    if (isRunning) {
        ProgressBars(mins, isRunning = false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Who are you counting down your life for?",
                        color = white,
                        style = MaterialTheme.typography.subtitle1,
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
                        numbers(timer)
                        Text(text = " : ", style = MaterialTheme.typography.h2, color = black, modifier = Modifier.padding(top = 16.dp))
                        numbers(timer)
                        Text(text = " : ", style = MaterialTheme.typography.h2, color = black, modifier = Modifier.padding(top = 16.dp))
                        mins = numbers(timer)
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    ProgressBars(mins, isRunning)

                    Row(modifier = Modifier.fillMaxWidth()) {
                        FloatingActionButton(
                            onClick = { isRunning = false; mins = "" },
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
                            onClick = { timer.onTimerStart(mins.toLong()) },
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
private fun numbers(timer: TimerViewModel): String {
    var nums: Int
    var realNums = ""
    var offset by remember { mutableStateOf(0f) }
    //var offset by rememberSaveable { mutableStateOf(0f) }

    Box(
        Modifier
            //.size(150.dp)
            .padding(10.dp)
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollableState { delta ->
                    offset += delta
                    delta
                },
                enabled = true
            )
            .background(white),
        contentAlignment = Alignment.Center
    ) {
        nums = offset.toInt() / 10
        if (nums > 59) {
            nums = 0
            offset = 0f

        } else if (nums < 0) {
            nums = 59
            offset = 590f
        }
        realNums = if (nums >= 10) nums.toString() else "0$nums"
        Text(text = realNums, style = typography.h1, color = black, textAlign = TextAlign.Center)
    }
    return realNums
}

@Composable
private fun ProgressBars(mins: String, isRunning: Boolean) {
    var progress = if (mins.equals("")) 0 else mins.toInt()
    Box(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .height(400.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(progress = progress/59f, strokeWidth = 6.dp, modifier = Modifier.fillMaxSize(), color = yellow)
        if (progress != 0 && isRunning) {
            while (progress != 0 && isRunning) {
                //delay(1000L)
                progress--
            }
        }
        Text(text = "00:00:$progress", style = MaterialTheme.typography.h2, color = black)
    }

}

@Preview("MainActivity")
@Composable
fun LightPreview() {
    MyTheme {
        MyApp(TimerViewModel())
    }
}
