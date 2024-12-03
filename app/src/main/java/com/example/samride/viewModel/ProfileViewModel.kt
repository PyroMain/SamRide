package com.example.samride.viewModel

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samride.enums.UserRole
import com.example.samride.model.User
import com.example.samride.repository.UserRepository
import com.example.samride.services.FirebaseAuthService
import kotlinx.coroutines.launch

class ProfileViewModel() : ViewModel() {
    private val _userRepository = UserRepository()

    val email = mutableStateOf("")
    val firstName = mutableStateOf("")
    val lastName = mutableStateOf("")
    val age = mutableIntStateOf(0)
    val picture = mutableStateOf("")
    val drivingLicenseNumber = mutableStateOf("")
    val drivingLicense = mutableStateOf("")
    private val _role = mutableStateOf("")

    fun onEmailChange(email: String) {
        this.email.value = email
    }

    fun onFirstNameChange(firstName: String) {
        this.firstName.value = firstName
    }

    fun onLastNameChange(lastName: String) {
        this.lastName.value = lastName
    }

    fun onAgeChange(age: Int) {
        this.age.intValue = age
    }

    fun onPictureChange(picture: String) {
        this.picture.value = picture
    }

    fun onDrivingLicenseNumberChange(drivingLicenseNumber: String) {
        this.drivingLicenseNumber.value = drivingLicenseNumber
    }

    fun onDrivingLicenseChange(drivingLicense: String) {
        this.drivingLicense.value = drivingLicense
    }



    init {
        val currentFirebaseUser = FirebaseAuthService.auth.currentUser
        if (currentFirebaseUser != null) {
            viewModelScope.launch {
                val user = _userRepository.findById(currentFirebaseUser.uid)
                if (user != null) {
                    email.value = user.email.toString()
                    firstName.value = if (user.firstName != null) user.firstName.toString() else ""
                    lastName.value = if (user.lastName != null) user.lastName.toString() else ""
                    age.intValue = user.age
                    picture.value = if (user.picture != null) user.picture.toString() else ""
                    drivingLicenseNumber.value = if (user.drivingLicenseNumber != null) user.drivingLicenseNumber.toString() else ""
                    drivingLicense.value = if (user.drivingLicense != null) user.drivingLicense.toString() else ""
                    Log.d("ProfileViewModel", "Role: ${user.role.toString()}")
                    _role.value = user.role.toString()
                }
            }
        }
    }

    suspend fun update() {
        val user = User(
            id = FirebaseAuthService.auth.currentUser!!.uid,
            email = email.value,
            firstName = firstName.value,
            lastName = lastName.value,
            age = age.intValue,
            picture = picture.value,
            drivingLicenseNumber = drivingLicenseNumber.value,
            drivingLicense = drivingLicense.value,
            role = UserRole.fromString(_role.value)
        )

        _userRepository.update(user)
    }
}