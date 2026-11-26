package com.sohan.childconnect.child.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun PairingEntryScreen(onPaired: () -> Unit) {
        val scope = rememberCoroutineScope()
            var code by remember { mutableStateOf("") }
                var error by remember { mutableStateOf<String?>(null) }
                    var loading by remember { mutableStateOf(false) }

                        // Child অ্যাপে ডেটা সেভ করার জন্য ইউজার লগইন থাকা লাগে, তাই আমরা Anonymous লগইন করছি
                            LaunchedEffect(Unit) {
                                        if (FirebaseAuth.getInstance().currentUser == null) {
                                                        FirebaseAuth.getInstance().signInAnonymously()
                                        }
                            }

                                Surface(color = Color(0xFF0D1120), modifier = Modifier.fillMaxSize()) {
                                            Column(
                                                            Modifier.fillMaxSize().padding(24.dp),
                                                                        horizontalAlignment = Alignment.CenterHorizontally,
                                                                                    verticalArrangement = Arrangement.Center
                                            ) {
                                                            Text("Pair with Parent", color = Color(0xFF8B5CF6), fontSize = 28.sp)
                                                                        Text("Parent অ্যাপে তৈরি করা ৬-ডিজিটের কোড দিন",
                                                                                        color = Color.White.copy(alpha = 0.6f), fontSize = 14.sp,
                                                                                                        modifier = Modifier.padding(vertical = 16.dp))

                                                                                                                    OutlinedTextField(
                                                                                                                                        value = code,
                                                                                                                                                        onValueChange = { if (it.length <= 6) code = it.filter { c -> c.isDigit() } },
                                                                                                                                                                        label = { Text("6-digit code") },
                                                                                                                                                                                        colors = OutlinedTextFieldDefaults.colors(
                                                                                                                                                                                                                focusedBorderColor = Color(0xFF8B5CF6),
                                                                                                                                                                                                                                    focusedTextColor = Color.White,
                                                                                                                                                                                                                                                        unfocusedTextColor = Color.White
                                                                                                                                                                                        ),
                                                                                                                                                                                                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                                                                                                                    )

                                                                                                                                error?.let { Text(it, color = Color(0xFFF87171), modifier = Modifier.padding(bottom = 12.dp)) }

                                                                                                                                            Button(
                                                                                                                                                                onClick = {
                                                                                                                                                                                        loading = true; error = null
                                                                                                                                                                                                            scope.launch {
                                                                                                                                                                                                                                        val ok = com.sohan.childconnect.child.data.PairingRepository.redeemCode(code)
                                                                                                                                                                                                                                                                loading = false
                                                                                                                                                                                                                                                                                        if (ok) onPaired() else error = "কোড ভুল বা সময় শেষ (15 মিনিট)"
                                                                                                                                                                                                            }
                                                                                                                                                                },
                                                                                                                                                                                enabled = !loading && code.length == 6,
                                                                                                                                                                                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6)),
                                                                                                                                                                                                                shape = RoundedCornerShape(12.dp),
                                                                                                                                                                                                                                modifier = Modifier.fillMaxWidth().height(50.dp)
                                                                                                                                            ) {
                                                                                                                                                                if (loading) CircularProgressIndicator(color = Color.White)
                                                                                                                                                                                else Text("Pair Device", fontSize = 16.sp)
                                                                                                                                            }
                                            }
                                }
}
