package com.tunm.cwallpaper2

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tunm.cwallpaper2.databinding.ActivityMainBinding
import com.tunm.cwallpaper2.ui.base.BaseActivityBinding
import com.tunm.cwallpaper2.ui.component.profile.categorymanager.CategoryViewModel
import com.tunm.cwallpaper2.ui.component.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivityBinding<ActivityMainBinding>(
    ActivityMainBinding::inflate
), View.OnClickListener {

    private val loginViewModel: LoginViewModel by viewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()
    override fun observeViewModel() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.bottomNavigationView.apply {
            setupWithNavController(getNavController())
        }

        getNavHostFragment().childFragmentManager.addOnBackStackChangedListener {
            if (getNavHostFragment().childFragmentManager.backStackEntryCount == 0) {
                Timber.d("stack count: empty")
            } else {
                Timber.d("stack count: ${getNavHostFragment().childFragmentManager.backStackEntryCount}")
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return getNavController().navigateUp() || super.onSupportNavigateUp()
    }

    private fun getNavController(): NavController {
        return getNavHostFragment().navController
    }

    private fun getNavHostFragment(): NavHostFragment {
        return supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
    }

    override fun onClick(view: View?) {
        when (view?.id) {

        }
    }
}