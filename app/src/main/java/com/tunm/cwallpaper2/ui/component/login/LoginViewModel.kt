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

    private var signupResponsePrivate = MutableLiveData<FirebaseStatus<String>>()
    val signupResponse: LiveData<FirebaseStatus<String>> get() = signupResponsePrivate

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            signupResponsePrivate.value = usersRepository.signupUser(AuthRequest(email, password), true)
        }
    }
}