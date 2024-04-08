package com.chanyoung.jack.ui.viewmodel.basic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

abstract class JBaseViewModelImpl :
    JBaseViewModel, ViewModel()

abstract class JBaseAndroidViewModelImpl(application: Application) :
    JBaseViewModel, AndroidViewModel(application)