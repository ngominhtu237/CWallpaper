package com.tunm.cwallpaper2.ui.component.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.tunm.cwallpaper2.R
import com.tunm.cwallpaper2.databinding.ActivityAppLoginBinding
import com.tunm.cwallpaper2.ui.base.BaseActivityBinding
import com.tunm.cwallpaper2.ui.component.profile.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppLoginActivity : BaseActivityBinding<ActivityAppLoginBinding>(
    ActivityAppLoginBinding::inflate
), View.OnClickListener {

    private val loginViewModel: LoginViewModel by viewModels()
    override fun observeViewModel() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnLogin.setOnClickListener(this)
        binding.signupBtn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnLogin -> {
                startActivity(Intent(this, LoginFragment::class.java))
                finish()
            }
            R.id.signupBtn -> {
                startActivity(Intent(this, RegisterActivity::class.java))
                finish()
            }
        }
    }
}