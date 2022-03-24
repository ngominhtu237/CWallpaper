package com.tunm.cwallpaper2.ui.component.home

import android.view.View
import androidx.fragment.app.activityViewModels
import com.tunm.cwallpaper2.R
import com.tunm.cwallpaper2.databinding.FragmentHomeBinding
import com.tunm.cwallpaper2.ui.base.BaseFragmentBinding
import com.tunm.cwallpaper2.ui.component.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragmentBinding<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
), View.OnClickListener {

    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun observeViewModel() {
    }

    override fun initUI() {

    }

    override fun setupListeners() {

    }

    override fun onClick(view: View?) {
        when (view?.id) {

        }
    }

    override fun initToolbar() {
        binding.toolbar.titleTV.text = getString(R.string.home)
    }

    override fun handleBack() {

    }
}