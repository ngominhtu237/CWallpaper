package com.tunm.cwallpaper2.ui.component.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tunm.cwallpaper2.R
import com.tunm.cwallpaper2.data.remote.firebase.FirebaseStatus
import com.tunm.cwallpaper2.databinding.FragmentLoginBinding
import com.tunm.cwallpaper2.ui.base.BaseFragmentBinding
import com.tunm.cwallpaper2.ui.component.CategoryManagerActivity
import com.tunm.cwallpaper2.ui.component.login.LoginViewModel
import com.tunm.cwallpaper2.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragmentBinding<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
), View.OnClickListener {

    private val loginViewModel: LoginViewModel by activityViewModels()
    override fun observeViewModel() {
        observe(loginViewModel.loginResponse) {
            handleLoginResult(it)
        }
    }

    override fun initToolbar() {
        binding.toolbar.titleTV.text = getString(R.string.login)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnLogin -> {
                loginViewModel.login(
                    binding.emailEt.text.toString(),
                    binding.passwordEt.text.toString()
                )
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleLoginResult(status: FirebaseStatus<String>) {
        when (status) {
            is FirebaseStatus.Success -> {
                val action = LoginFragmentDirections.actionLoginDestToProfileDest()
                findNavController().navigate(action)
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
        binding.btnLogin.setOnClickListener(this)
    }

    override fun initUI() {
        binding.resultTv.text = ""
    }
}