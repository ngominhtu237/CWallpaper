package com.tunm.cwallpaper2.ui.component.profile

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tunm.cwallpaper2.R
import com.tunm.cwallpaper2.data.remote.firebase.FirebaseStatus
import com.tunm.cwallpaper2.databinding.FragmentProfileBinding
import com.tunm.cwallpaper2.ui.base.BaseFragmentBinding
import com.tunm.cwallpaper2.ui.component.login.LoginViewModel
import com.tunm.cwallpaper2.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProfileFragment : BaseFragmentBinding<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
), View.OnClickListener {

    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun observeViewModel() {
        observe(loginViewModel.loginResponse) {
            handleLoginResult(it)
        }
    }

    override fun initToolbar() {
    }

    @SuppressLint("SetTextI18n")
    private fun handleLoginResult(status: FirebaseStatus<String>) {
        when (status) {
            is FirebaseStatus.Success -> {
                initUI()
            }
            is FirebaseStatus.Error -> {
                binding.apply {
                    groupNonLogin.visibility = View.VISIBLE
                    groupLogin.visibility = View.GONE
                    status.msg?.let {
                        binding.tvStatusMsg.text = it
                    }
                }
            }
        }
    }

    override fun initUI() {
        if (loginViewModel.isLogin()) {
            val user = loginViewModel.getProfile()
            binding.apply {
                tvName.text = if (user?.displayName.isNullOrEmpty()) "N/A" else user?.displayName
                tvEmail.text = user?.email ?: "N/A"
                groupLogin.visibility = View.VISIBLE
                groupNonLogin.visibility = View.GONE
            }
        } else {
            binding.apply {
                tvName.text = "Guest"
                tvEmail.text = "N/A"
                groupNonLogin.visibility = View.VISIBLE
                groupLogin.visibility = View.GONE
            }
        }
    }

    override fun setupListeners() {
        binding.apply {
            btnLogin.setOnClickListener(this@ProfileFragment)
            btnLogout.setOnClickListener(this@ProfileFragment)
            btnProfileDetails.setOnClickListener(this@ProfileFragment)
            btnCategoryManager.setOnClickListener(this@ProfileFragment)
            tvName.setOnClickListener(this@ProfileFragment)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnLogin -> {
                findNavController().navigate(R.id.actionProfileFragmentToLoginFragment)
            }
            R.id.btnCategoryManager -> {
                findNavController().navigate(R.id.action_profile_dest_to_category_manage_dest)
            }
            R.id.btnLogout -> {
                loginViewModel.logout()
                initUI()
            }
            R.id.btnProfileDetails -> {
                Timber.d("Click profile details")
            }
            R.id.edtName -> {
                loginViewModel.loginResponse.value = FirebaseStatus.Error("Error")
            }
        }
    }

    override fun handleBack() {

    }
}