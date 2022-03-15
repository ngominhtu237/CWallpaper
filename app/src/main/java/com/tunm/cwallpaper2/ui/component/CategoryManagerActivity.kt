package com.tunm.cwallpaper2.ui.component

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.tunm.cwallpaper2.R
import com.tunm.cwallpaper2.data.remote.firebase.FirebaseStatus
import com.tunm.cwallpaper2.databinding.ActivityCategoryManagerBinding
import com.tunm.cwallpaper2.ui.base.BaseActivityBinding
import com.tunm.cwallpaper2.ui.component.login.AppLoginActivity
import com.tunm.cwallpaper2.ui.component.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryManagerActivity: BaseActivityBinding<ActivityCategoryManagerBinding>(
    ActivityCategoryManagerBinding::inflate
) {

    private val loginViewModel: LoginViewModel by viewModels()
    override fun observeViewModel() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnLogout.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnLogout -> {
                loginViewModel.logout()
                Toast.makeText(this, "Logout successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, AppLoginActivity::class.java))
                finish()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleLogoutResult(status: FirebaseStatus<String>) {
        when (status) {
            is FirebaseStatus.Success -> {
                startActivity(Intent(this, AppLoginActivity::class.java))
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