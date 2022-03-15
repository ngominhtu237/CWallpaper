package com.tunm.cwallpaper2.ui.component.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tunm.cwallpaper2.data.dto.auth.AuthRequest
import com.tunm.cwallpaper2.data.remote.firebase.FirebaseStatus
import com.tunm.cwallpaper2.data.repo.user.UsersRepository
import com.tunm.cwallpaper2.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : BaseViewModel() {

    private var _signupResponse = MutableLiveData<FirebaseStatus<String>>()
    val signupResponse: LiveData<FirebaseStatus<String>> get() = _signupResponse
    private var _loginResponse = MutableLiveData<FirebaseStatus<String>>()
    val loginResponse: LiveData<FirebaseStatus<String>> get() = _loginResponse

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            _signupResponse.value = usersRepository.signupUser(AuthRequest(email, password), true)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResponse.value = usersRepository.login(AuthRequest(email, password))
        }
    }

    fun isLogin() = usersRepository.isLogin()
    fun logout() {
        usersRepository.logout()
        _loginResponse.value = null
    }
    fun getProfile() = usersRepository.getCurrentUser()
}