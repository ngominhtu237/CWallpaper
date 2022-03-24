package com.tunm.cwallpaper2.ui.component.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tunm.cwallpaper2.R
import com.tunm.cwallpaper2.data.remote.firebase.FirebaseStatus
import com.tunm.cwallpaper2.databinding.FragmentRegisterBinding
import com.tunm.cwallpaper2.ui.base.BaseFragmentBinding
import com.tunm.cwallpaper2.ui.component.CategoryManagerActivity
import com.tunm.cwallpaper2.ui.component.login.LoginViewModel
import com.tunm.cwallpaper2.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragmentBinding<FragmentRegisterBinding>(
    FragmentRegisterBinding::inflate
), View.OnClickListener {

    private val loginViewModel: LoginViewModel by activityViewModels()
    override fun observeViewModel() {
        observe(loginViewModel.signupResponse) {
            handleSignupResult(it)
        }
    }

    override fun initToolbar() {
        binding.toolbar.titleTV.text = getString(R.string.sign_up)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.signupBtn -> loginViewModel.signup(
                binding.emailEt.text.toString(),
                binding.passwordEt.text.toString()
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleSignupResult(status: FirebaseStatus<String>) {
        when (status) {
            is FirebaseStatus.Success -> {
                findNavController().navigate(R.id.action_register_dest_to_profile_dest)
            }
            is FirebaseStatus.Error -> {
                status.msg?.let {
                    binding.resultTv.text = it
                }
                binding.resultTv.visibility = View.VISIBLE
            }
        }
    }

    override fun setupListeners() {
        binding.signupBtn.setOnClickListener(this)
    }

    override fun initUI() {

    }

    override fun handleBack() {

    }
}