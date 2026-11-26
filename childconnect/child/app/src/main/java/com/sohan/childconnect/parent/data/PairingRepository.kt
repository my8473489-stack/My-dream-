package com.sohan.childconnect.child.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

object PairingRepository {
        private val db get() = FirebaseDatabase.getInstance().reference

            suspend fun redeemCode(code: String): Boolean {
                        // Child অ্যাপে ইউজার লগইন না থাকলে (Anonymous Auth) এরর দিতে পারে, তাই চেক করা হচ্ছে
                                val childUid = FirebaseAuth.getInstance().currentUser?.uid ?: return false
                                        
                                                val snapshot = db.child("pairingCodes/$code").get().await()
                                                        val parentUid = snapshot.child("parentUid").getValue(String::class.java) ?: return false
                                                                val expiresAt = snapshot.child("expiresAt").getValue(Long::class.java) ?: return false
                                                                        
                                                                                if (System.currentTimeMillis() > expiresAt) return false

                                                                                        db.child("linkedChildren/$childUid/parentUid").setValue(parentUid).await()
                                                                                                db.child("parents/$parentUid/children/$childUid").setValue(true).await()
                                                                                                        db.child("pairingCodes/$code").removeValue().await()
                                                                                                                return true
            }
}
