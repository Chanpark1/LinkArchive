package com.chanyoung.jack.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chanyoung.jack.application.WebUrlUtil
import com.chanyoung.jack.ui.component.clipboard.JClipboardManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ClipBoardViewModel : ViewModel() {


    private val _clipData = MutableLiveData<String>()
    val clipData: LiveData<String> get() = _clipData

    fun handlerPrimaryClip(context: Context, delayMillis: Long) {

        viewModelScope.launch {
            delay(delayMillis)

            val clipboardManager = JClipboardManager.getClipboardManager(context)
            if (clipboardManager.hasPrimaryClip()) {
                try {
                    val clip = clipboardManager.primaryClip
                    val text = clip?.getItemAt(0)?.text?.toString()
                    if (text != null && WebUrlUtil.checkUrlPrefix(text)) {
                        _clipData.value = text
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _clipData.value = null
                }

            }

        }
    }
}