package com.tunm.cwallpaper2.ui.component.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.tunm.cwallpaper2.R
import com.tunm.cwallpaper2.data.remote.firebase.FirebaseStatus
import com.tunm.cwallpaper2.databinding.ActivityRegisterBinding
import com.tunm.cwallpaper2.ui.base.BaseActivity
import com.tunm.cwallpaper2.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity(), View.OnClickListener {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityRegisterBinding
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
            is FirebaseStatus.Success -> binding.resultTv.text = "Signup successfully"
            is FirebaseStatus.Error -> {
                status.msg?.let {
                    binding.resultTv.text = it
                }
                binding.resultTv.visibility = View.VISIBLE
            }
        }
    }

    override fun initViewBinding() {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}