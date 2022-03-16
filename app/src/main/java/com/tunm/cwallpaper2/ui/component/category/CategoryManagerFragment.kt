package com.tunm.cwallpaper2.ui.component.category

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tunm.cwallpaper2.R
import com.tunm.cwallpaper2.data.remote.firebase.FirebaseStatus
import com.tunm.cwallpaper2.databinding.FragmentCategoryManagerBinding
import com.tunm.cwallpaper2.ui.base.BaseFragmentBinding
import com.tunm.cwallpaper2.ui.component.login.LoginViewModel
import com.tunm.cwallpaper2.ui.component.profile.ProfileFragmentDirections
import com.tunm.cwallpaper2.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryManagerFragment : BaseFragmentBinding<FragmentCategoryManagerBinding>(
    FragmentCategoryManagerBinding::inflate
), View.OnClickListener {

    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun observeViewModel() {
        observe(loginViewModel.loginResponse) {
            handleLoginResult(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleLoginResult(status: FirebaseStatus<String>) {
        when (status) {
            is FirebaseStatus.Success -> {
            }
            is FirebaseStatus.Error -> {
                binding.apply {

                }
            }
        }
    }

    override fun initUI() {

    }

    override fun setupListeners() {

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnLogin -> {
                val action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
                findNavController().navigate(action)
            }
        }
    }

    override fun initToolbar() {

    }
}