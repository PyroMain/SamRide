package com.example.samride.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samride.model.User
import com.example.samride.repository.UserRepository
import com.example.samride.services.FirebaseAuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MenuViewModel() : ViewModel() {
    private val _user = MutableStateFlow(User(""))
    private val _userRepository = UserRepository()

    val user: MutableStateFlow<User> get() = _user

    private fun setUser(user: User) {
        _user.value = user
    }

    init {
        val currentFirebaseUser = FirebaseAuthService.auth.currentUser
        if (currentFirebaseUser != null) {
            viewModelScope.launch {
                val user = _userRepository.findById(currentFirebaseUser.uid)
                if (user != null) {
                    setUser(user)
                }
            }
        }
    }
}