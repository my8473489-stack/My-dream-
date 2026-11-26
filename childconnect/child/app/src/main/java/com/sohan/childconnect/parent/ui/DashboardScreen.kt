package com.sohan.childconnect.parent.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen() {
            var tab by remember { mutableStateOf(0) }

                Scaffold(
                                containerColor = Color(0xFF0D1120), // DarkNavy
                                        bottomBar = {
                                                            NavigationBar(containerColor = Color(0xFF1E1E2E)) { // CardDark
                                                                            NavigationBarItem(
                                                                                                    selected = tab == 0,
                                                                                                                        onClick = { tab = 0 },
                                                                                                                                            icon = { Text("H") },
                                                                                                                                                                label = { Text("Home") }
                                                                            )
                                                                                            NavigationBarItem(
                                                                                                                    selected = tab == 1,
                                                                                                                                        onClick = { tab = 1 },
                                                                                                                                                            icon = { Text("S") },
                                                                                                                                                                                label = { Text("Screen Time") }
                                                                                            )
                                                                                                            NavigationBarItem(
                                                                                                                                    selected = tab == 2,
                                                                                                                                                        onClick = { tab = 2 },
                                                                                                                                                                            icon = { Text("L") },
                                                                                                                                                                                                label = { Text("Location") }
                                                                                                            )
                                                            }
                                        }
                ) { padding ->
                        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
                                            when (tab) {
                                                                0 -> Text("Dashboard", color = Color.White)
                                                                                1 -> Text("Screen Time", color = Color.White)
                                                                                                2 -> Text("Location", color = Color.White)
                                                                                                                else -> Text("Settings", color = Color.White)
                                            }
                        }
                }
}
