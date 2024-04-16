package com.chanyoung.jack.ui.component.dialog.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class JBasicBottomSheetDialog<T : ViewBinding> : BottomSheetDialogFragment() {
    private var _binding: T? = null
    protected val binding : T get() = _binding!!

    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) : T

    override fun onCreateView(
        inflater: LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?)
    : View? {
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}