package com.chanyoung.jack.ui.component.dialog.basic

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ViewCompat.setOnApplyWindowInsetsListener(requireDialog().window?.decorView!!) { _, insets ->
                    val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom

                    val navigationBarHeight = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom

                    val topPadding = binding.root.paddingTop

                    val startPadding = binding.root.paddingStart

                    val endPadding = binding.root.paddingEnd

                    binding.root.setPadding(
                        startPadding,
                        topPadding,
                        endPadding,
                        imeHeight - navigationBarHeight
                    )
                    insets
                }
            } else {
                setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            }
        }
    }
}