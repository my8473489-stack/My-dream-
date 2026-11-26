package com.sohan.childconnect.parent

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun FirebaseAuth.authStateFlow(): Flow<com.google.firebase.auth.FirebaseUser?> =
    callbackFlow {
                val listener = FirebaseAuth.AuthStateListener { auth -> trySend(auth.currentUser) }
                        addAuthStateListener(listener)
                                awaitClose { removeAuthStateListener(listener) }
    }