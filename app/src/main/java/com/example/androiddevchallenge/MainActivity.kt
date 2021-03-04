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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
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
                        Text(text = "00", style = MaterialTheme.typography.h2, color = black, textAlign = TextAlign.Center)
                        Text(text = " : ", style = MaterialTheme.typography.h3, color = black, textAlign = TextAlign.Center)
                        Text(text = "00", style = MaterialTheme.typography.h2, color = black, textAlign = TextAlign.Center)
                        Text(text = " : ", style = MaterialTheme.typography.h3, color = black, textAlign = TextAlign.Center)
                        Text(text = "00", style = MaterialTheme.typography.h2, color = black, textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(modifier = Modifier.fillMaxWidth()) {

                        FloatingActionButton(
                            onClick = { /*TODO*/ },
                            backgroundColor = yellow,
                            modifier = Modifier.width(80.dp).height(80.dp),
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
                            onClick = { /*TODO*/ },
                            backgroundColor = yellow,
                            modifier = Modifier.width(80.dp).height(80.dp),
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

@Preview("MainActivity")
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}
