package com.example.samride.viewModel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.samride.enums.UserRole
import com.example.samride.services.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
        private val _email = MutableStateFlow("")
        private val _password = MutableStateFlow("")
        private val _confirmPassword = MutableStateFlow("")
        private val _selectedRole = MutableStateFlow(UserRole.USER)

        val email: StateFlow<String> get() = _email
        val password: StateFlow<String> get() = _password
        val confirmPassword: StateFlow<String> get() = _confirmPassword
        val selectedRole: StateFlow<UserRole> get() = _selectedRole

        fun onEmailChanged(newEmail: String) {
            _email.value = newEmail
        }

        fun onPasswordChanged(newPassword: String) {
            _password.value = newPassword
        }

        fun onConfirmPasswordChanged(newConfirmPassword: String) {
            _confirmPassword.value = newConfirmPassword
        }

        fun onRoleSelected(newRole: UserRole) {
            _selectedRole.value = newRole
        }

        fun register(navController: NavController) {
            viewModelScope.launch {
                UserService().register(_email.value, _password.value, _selectedRole.value)

                if (UserService().isLogged()){
                    navController.navigate("bookSam")
                }
            }
        }

        fun isValidEmail(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

}