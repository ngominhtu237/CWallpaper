package com.tunm.cwallpaper2.ui.component.category

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tunm.cwallpaper2.R
import com.tunm.cwallpaper2.data.dto.category.Category
import com.tunm.cwallpaper2.data.remote.firebase.FirebaseStatus
import com.tunm.cwallpaper2.databinding.FragmentAddCategoryBinding
import com.tunm.cwallpaper2.ui.base.BaseFragmentBinding
import com.tunm.cwallpaper2.ui.component.login.LoginViewModel
import com.tunm.cwallpaper2.ui.component.profile.RegisterFragmentDirections
import com.tunm.cwallpaper2.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCategoryFragment : BaseFragmentBinding<FragmentAddCategoryBinding>(
    FragmentAddCategoryBinding::inflate
), View.OnClickListener {

    private val loginViewModel: LoginViewModel by activityViewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()
    private var bgImageUri: Uri? = null

    override fun observeViewModel() {
        observe(categoryViewModel.addCategoryResponse) {
            handleAddCategory(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleAddCategory(status: FirebaseStatus<String>) {
        when (status) {
            is FirebaseStatus.Success -> {
                handleBack()
            }
            is FirebaseStatus.Error -> {
                status.msg?.let {
                    binding.tvStatusMsg.text = it
                }
                binding.tvStatusMsg.visibility = View.VISIBLE
            }
        }
    }

    override fun initUI() {

    }

    override fun setupListeners() {
        binding.toolbar.backBtn.setOnClickListener {
            handleBack()
        }
        binding.apply {
            btnAddBg.setOnClickListener(this@AddCategoryFragment)
            btnAddCategory.setOnClickListener(this@AddCategoryFragment)
        }
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {  result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.apply {
                    binding.ivBackground.setImageURI(data)
                    bgImageUri = data
                }
            }
        }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnAddBg -> {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                resultLauncher.launch(intent)
            }
            R.id.btnAddCategory -> {
                categoryViewModel.addCategoryByAdmin(
                    Category(
                        categoryName = binding.categoryName.getCategoryName(),
                        bgImage = if (bgImageUri == null) null else bgImageUri.toString()
                    )
                )
            }
        }
    }

    override fun initToolbar() {
        binding.toolbar.apply {
            titleTV.text = getString(R.string.add_category)
            backBtn.visibility = View.VISIBLE
        }

    }

    override fun handleBack() {
        val action = AddCategoryFragmentDirections.actionAddCategoryFragmentToCategoryManageDest()
        findNavController().navigate(action)
    }
}