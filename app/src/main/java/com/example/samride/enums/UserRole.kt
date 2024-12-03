package com.example.samride.enums

enum class UserRole {
    USER,
    SAM,
    ADMIN;

    override fun toString(): String {
        return when (this) {
            USER -> "USER"
            SAM -> "SAM"
            ADMIN -> "ADMIN"
        }
    }

    companion object {
        fun fromString(role: String?): UserRole {
            return when (role) {
                "USER" -> USER
                "SAM" -> SAM
                "ADMIN" -> ADMIN
                else -> throw IllegalArgumentException("Invalid role. Please provide a valid role in the following list: User, Sam, Admin")
            }
        }
    }
}