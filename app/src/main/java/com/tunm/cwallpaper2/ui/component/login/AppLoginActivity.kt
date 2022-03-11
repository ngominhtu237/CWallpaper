package com.tunm.cwallpaper2.ui.component.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.tunm.cwallpaper2.R
import com.tunm.cwallpaper2.databinding.ActivityAppLoginBinding
import com.tunm.cwallpaper2.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppLoginActivity : BaseActivity(), View.OnClickListener {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityAppLoginBinding
    override fun observeViewModel() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.loginBtn.setOnClickListener(this)
        binding.signupBtn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.loginBtn -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            R.id.signupBtn -> {
                startActivity(Intent(this, RegisterActivity::class.java))
                finish()
            }
        }
    }

    override fun initViewBinding() {
        binding = ActivityAppLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}