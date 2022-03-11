package com.tunm.cwallpaper2.ui.component.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.tunm.cwallpaper2.SPLASH_DELAY
import com.tunm.cwallpaper2.databinding.SplashLayoutBinding
import com.tunm.cwallpaper2.ui.base.BaseActivity
import com.tunm.cwallpaper2.ui.component.CategoryManagerActivity
import com.tunm.cwallpaper2.ui.component.login.AppLoginActivity
import com.tunm.cwallpaper2.ui.component.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity: BaseActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: SplashLayoutBinding

    override fun initViewBinding() {
        binding = SplashLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToMainScreen()
    }

    override fun observeViewModel() {
    }

    private fun navigateToMainScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (loginViewModel.isLogin()) {
                startActivity(Intent(this, CategoryManagerActivity::class.java))
            } else {
                startActivity(Intent(this, AppLoginActivity::class.java))
            }
            finish()
        }, SPLASH_DELAY.toLong())
    }
}