package com.tunm.cwallpaper2.ui.component.profile

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
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

    @SuppressLint("SetTextI18n")
    private fun handleLoginResult(status: FirebaseStatus<String>) {
        when (status) {
            is FirebaseStatus.Success -> {
                updateUI()
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

    override fun updateUI() {
        if (loginViewModel.isLogin()) {
            val user = loginViewModel.getProfile()
            binding.apply {
                tvName.text = user?.displayName ?: "N/A"
                tvEmail.text = user?.email ?: "N/A"
                tvId.text = user?.uid ?: "N/A"
                groupLogin.visibility = View.VISIBLE
                groupNonLogin.visibility = View.GONE
            }
        } else {
            binding.apply {
                groupNonLogin.visibility = View.VISIBLE
                groupLogin.visibility = View.GONE
            }
        }
    }

    override fun setupListeners() {
        binding.apply {
            btnLogin.setOnClickListener(this@ProfileFragment)
            btnLogout.setOnClickListener(this@ProfileFragment)
            btnUpdate.setOnClickListener(this@ProfileFragment)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnLogin -> {
                Timber.d("Click update button")
                val action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
                findNavController().navigate(action)
            }
            R.id.btnLogout -> {
                loginViewModel.logout()
                updateUI()
            }
            R.id.btnUpdate -> {
                Timber.d("Click update button")
            }
        }
    }
}