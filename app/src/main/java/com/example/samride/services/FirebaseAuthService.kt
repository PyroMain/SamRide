package com.example.samride.services

import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseAuthService {
    companion object {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        @SuppressLint("StaticFieldLeak")
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    }
}