package com.tunm.cwallpaper2.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.IllegalArgumentException

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
abstract class BaseFragmentBinding<VB: ViewBinding> (
    private val bindingInflater: Inflate<VB>
): Fragment() {

    private var _binding: VB? = null
    val binding: VB
        get() = _binding as VB

    abstract fun observeViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater.invoke(inflater, container, false)
        if (_binding == null) throw IllegalArgumentException("Binding cannot be null")
        initUI()
        initToolbar()
        setupListeners()
        observeViewModel()
        return binding.root
    }

    abstract fun initToolbar()

    abstract fun setupListeners()

    abstract fun initUI()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}