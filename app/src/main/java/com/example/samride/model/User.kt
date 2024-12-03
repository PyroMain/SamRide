package com.example.samride.model

import com.example.samride.enums.UserRole

data class User(
    var id: String,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var role: UserRole? = null,
    var age: Int = 0,
    var picture: String? = null,
    var drivingLicenseNumber: String? = null,
    var drivingLicense: String? = null
)
