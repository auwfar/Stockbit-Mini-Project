package com.auwfar.stockbitminiproject.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auwfar.stockbitminiproject.local.entities.UserEntity
import com.auwfar.stockbitminiproject.repositories.UsersRepository
import kotlinx.coroutines.launch

class UsersViewModel(private val usersRepository: UsersRepository): ViewModel() {
    val isUserFound = MutableLiveData<Boolean>()
    val registerResult = MutableLiveData<Boolean>()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = usersRepository.getUser(email, password)
            isUserFound.value = (user != null)
        }
    }

    fun getUserWithEmail(email: String) {
        viewModelScope.launch {
            val user = usersRepository.getUser(email)
            isUserFound.value = (user != null)
        }
    }

    fun registerUser(userEntity: UserEntity) {
        viewModelScope.launch {
            registerResult.value = usersRepository.addUser(userEntity)
        }
    }
}