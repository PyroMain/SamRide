package com.example.samride.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.samride.services.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _errorMessage = MutableStateFlow<String?>(null)

    val email: StateFlow<String> get() = _email
    val password: StateFlow<String> get() = _password
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun login(navController: NavController) {
        viewModelScope.launch {
            UserService().login(_email.value, _password.value)

            if (UserService().isLogged()){
                    navController.navigate("bookSam")
            }
        }
    }

    fun isLogged(): Boolean {
        return UserService().isLogged()
    }
}