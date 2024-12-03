package com.example.samride.services

import android.util.Log
import com.example.samride.model.User
import com.example.samride.enums.UserRole
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import java.util.Objects

class UserService {
    fun register(email: String?, password: String?, role: UserRole?) {
        FirebaseAuthService.auth.createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    Log.d("UserService", "User registered successfully")
                    val userId =
                        Objects.requireNonNull(FirebaseAuthService.auth.currentUser)
                            ?.uid
                    if (userId != null) {
                        FirebaseAuthService.db.collection("users")
                            .document(userId)
                            .set(
                                User(id = userId, email = email, role = role)
                            )
                    }
                }
            }
    }

    fun login(email: String?, password: String?) {
        FirebaseAuthService.auth.signInWithEmailAndPassword(email!!, password!!)
    }

    fun logout() {
        FirebaseAuthService.auth.signOut()
    }

    fun isLogged(): Boolean {
        return FirebaseAuthService.auth.currentUser != null
    }
}