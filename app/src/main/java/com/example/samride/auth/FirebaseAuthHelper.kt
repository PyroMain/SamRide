package com.example.samride.auth

import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirebaseAuthHelper {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    @SuppressLint("StaticFieldLeak")
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun registerUser(email: String, password: String, role: String): FirebaseUser? {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user?.let { user ->
                saveUserRole(user.uid, email, role)
            }
            result.user
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun saveUserRole(uid: String, email: String, role: String) {
        val userMap = hashMapOf(
            "email" to email,
            "role" to role
        )
        firestore.collection("users").document(uid).set(userMap).await()
    }

    suspend fun loginUser(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            null
        }
    }

    fun logoutUser() {
        auth.signOut()
    }

    fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
    }

    fun getCurrentUser(): FirebaseUser? = auth.currentUser
}