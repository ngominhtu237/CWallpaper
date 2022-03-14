package com.tunm.cwallpaper2.ui.component.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.tunm.cwallpaper2.R
import com.tunm.cwallpaper2.data.remote.firebase.FirebaseStatus
import com.tunm.cwallpaper2.databinding.ActivityRegisterBinding
import com.tunm.cwallpaper2.ui.base.BaseActivityBinding
import com.tunm.cwallpaper2.ui.component.CategoryManagerActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivityBinding<ActivityRegisterBinding>(
    ActivityRegisterBinding::inflate
), View.OnClickListener {

    private val loginViewModel: LoginViewModel by viewModels()
    override fun observeViewModel() {
//        observe(loginViewModel.signupResponse) {
//            handleSignupResult(it)
//        }
        loginViewModel.signupResponse.observe(this) {
            handleSignupResult(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.signupBtn.setOnClickListener(this)
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
                startActivity(Intent(this, CategoryManagerActivity::class.java))
                finish()
            }
            is FirebaseStatus.Error -> {
                status.msg?.let {
                    binding.resultTv.text = it
                }
                binding.resultTv.visibility = View.VISIBLE
            }
        }
    }
}