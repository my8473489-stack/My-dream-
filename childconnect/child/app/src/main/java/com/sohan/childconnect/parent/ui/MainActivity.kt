package com.sohan.childconnect.parent // আপনার প্যাকেজ নাম যা আছে তাই রাখুন

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class MainActivity : ComponentActivity() {
            override fun onCreate(savedInstanceState: Bundle?) {
                        super.onCreate(savedInstanceState)
                                setContent {
                                                    MaterialTheme {
                                                                        Surface {
                                                                                                    // এখানে আপাতত টেক্সট দেখাচ্ছে, পরের ধাপে এখানে পেয়ারিং স্ক্রিন বসবে
                                                                                                                        Text("Child App Ready")
                                                                        }
                                                    }
                                }
            }
}
