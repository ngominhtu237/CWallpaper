package com.tunm.cwallpaper2.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.IllegalArgumentException

abstract class BaseActivityBinding<VB: ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
): AppCompatActivity(), View.OnClickListener {

    private var _binding: VB? = null
    val binding: VB
        get() = _binding as VB

    abstract fun observeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        if (_binding == null) throw IllegalArgumentException("Binding cannot be null")
        setContentView(binding.root)
        observeViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}