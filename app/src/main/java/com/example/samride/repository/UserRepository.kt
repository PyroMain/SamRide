package com.example.samride.repository

import com.example.samride.enums.UserRole
import com.example.samride.model.User
import com.example.samride.services.FirebaseAuthService
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await

class UserRepository {
    suspend fun findById(id: String): User? {
        val user = User(id = id)
        val documentTask: Task<DocumentSnapshot> = FirebaseAuthService.db.collection("users")
            .document(id)
            .get()

        return try {
            val document = documentTask.await()
            val data = document.data
            if (data != null) {
                user.firstName = data["firstName"] as String?
                user.lastName = data["lastName"] as String?
                user.email = data["email"] as String?
                user.role = UserRole.fromString(data["role"] as String?)
                user.age = (data["age"] as Long).toInt()
                user.picture = data["picture"] as String?
                user.drivingLicenseNumber = data["drivingLicenseNumber"] as String?
                user.drivingLicense = data["drivingLicense"] as String?
            }
            user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun update(user: User) {
        FirebaseAuthService.db.collection("users")
            .document(user.id)
            .set(user)
            .await()
    }
}