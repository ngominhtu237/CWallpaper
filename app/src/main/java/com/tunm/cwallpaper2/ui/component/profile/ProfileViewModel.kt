package com.tunm.cwallpaper2.ui.component.profile

import com.tunm.cwallpaper2.data.repo.user.UsersRepository
import com.tunm.cwallpaper2.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : BaseViewModel() {

}