package com.child.childconnect.parent.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.sohan.childconnect.parent.ui.theme.DarkNavy
import com.sohan.childconnect.parent.ui.theme.PurpleAccent
import com.sohan.childconnect.parent.ui.theme.TealLive
import kotlinx.coroutines.launch

@Composable
fun LoginScreen() {
            val auth = FirebaseAuth.getInstance()
                val scope = rememberCoroutineScope()

                    var email by remember { mutableStateOf("") }
                        var password by remember { mutableStateOf("") }
                            var name by remember { mutableStateOf("") }
                                var isRegistering by remember { mutableStateOf(false) }
                                    var error by remember { mutableStateOf<String?>(null) }
                                        var loading by remember { mutableStateOf(false) }

                                            Surface(color = DarkNavy, modifier = Modifier.fillMaxSize()) {
                                                        Column(
                                                                            modifier = Modifier.fillMaxSize().padding(24.dp),
                                                                                        horizontalAlignment = Alignment.CenterHorizontally,
                                                                                                    verticalArrangement = Arrangement.Center
                                                        ) {
                                                                            Text("ChildConnect", color = PurpleAccent, fontSize = 32.sp, modifier = Modifier.padding(bottom = 8.dp))
                                                                                        Text(if (isRegistering) "Parent Registration" else "Parent Login", color = Color.White.copy(alpha = 0.7f), fontSize = 16.sp, modifier = Modifier.padding(bottom = 32.dp))

                                                                                                    if (isRegistering) {
                                                                                                                        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Your name") }, colors = textFieldColors(), modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp))
                                                                                                    }
                                                                                                                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, colors = textFieldColors(), modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp))
                                                                                                                            OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password (6+ characters)") }, visualTransformation = PasswordVisualTransformation(), colors = textFieldColors(), modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp))

                                                                                                                                        error?.let { Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(bottom = 12.dp)) }

                                                                                                                                                    Button(
                                                                                                                                                                        onClick = {
                                                                                                                                                                                                    loading = true
                                                                                                                                                                                                                        error = null
                                                                                                                                                                                                                                            scope.launch {
                                                                                                                                                                                                                                                                        if (isRegistering) {
                                                                                                                                                                                                                                                                                                            auth.createUserWithEmailAndPassword(email.trim(), password)
                                                                                                                                                                                                                                                                                                                                            .addOnSuccessListener { result ->
                                                                                                                                                                                                                                                                                                                                                                                result.user?.updateProfile(userProfileChangeRequest { displayName = name.trim() })
                                                                                                                                                                                                                                                                                                                                                                                                                    result.user?.let { u ->
                                                                                                                                                                                                                                                                                                                                                                                                                                                            FirebaseDatabase.getInstance().getReference("parents/${u.uid}").setValue(mapOf("name" to name.trim(), "email" to email.trim()))
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    loading = false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .addOnFailureListener { error = it.message; loading = false }
                                                                                                                                                                                                                                                                        } else {
                                                                                                                                                                                                                                                                                                            auth.signInWithEmailAndPassword(email.trim(), password)
                                                                                                                                                                                                                                                                                                                                            .addOnFailureListener { error = it.message; loading = false }
                                                                                                                                                                                                                                                                                                                                                                            .addOnSuccessListener { loading = false }
                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                            }
                                                                                                                                                                        },
                                                                                                                                                                                        enabled = !loading && email.isNotBlank() && password.length >= 6,
                                                                                                                                                                                                        shape = RoundedCornerShape(14.dp),
                                                                                                                                                                                                                        colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent),
                                                                                                                                                                                                                                        modifier = Modifier.fillMaxWidth().height(52.dp)
                                                                                                                                                    ) {
                                                                                                                                                                        if (loading) CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
                                                                                                                                                                                        else Text(if (isRegistering) "Create Account" else "Login", fontSize = 16.sp)
                                                                                                                                                    }

                                                                                                                                                                TextButton(onClick = { isRegistering = !isRegistering; error = null }) {
                                                                                                                                                                                        Text(if (isRegistering) "Already have an account? Login" else "New here? Create account", color = TealLive)
                                                                                                                                                                }
                                                        }
                                            }
}

@Composable
private fun textFieldColors() = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PurpleAccent, cursorColor = PurpleAccent, focusedTextColor = Color.White, unfocusedTextColor = Color.White, focusedLabelColor = PurpleAccent, unfocusedLabelColor = Color.Gray
)
