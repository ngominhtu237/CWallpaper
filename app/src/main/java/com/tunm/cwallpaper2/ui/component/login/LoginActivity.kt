package com.tunm.cwallpaper2.ui.component.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.tunm.cwallpaper2.R
import com.tunm.cwallpaper2.data.remote.firebase.FirebaseStatus
import com.tunm.cwallpaper2.databinding.ActivityLoginBinding
import com.tunm.cwallpaper2.ui.base.BaseActivity
import com.tunm.cwallpaper2.ui.component.CategoryManagerActivity
import com.tunm.cwallpaper2.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity(), View.OnClickListener {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    override fun observeViewModel() {
        observe(loginViewModel.loginResponse) {
            handleLoginResult(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.loginBtn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.loginBtn -> loginViewModel.login(
                binding.emailEt.text.toString(),
                binding.passwordEt.text.toString()
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleLoginResult(status: FirebaseStatus<String>) {
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

    override fun initViewBinding() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}