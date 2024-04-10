package com.chanyoung.jack.ui.viewmodel.networking

import androidx.lifecycle.*
import com.chanyoung.jack.data.repository.networking.WebScraperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebScraperViewModel @Inject constructor(
    private val scrapRepo: WebScraperRepository
) : ViewModel() {

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> get() = _imageUrl

    fun scrapUrl(url: String) {
        viewModelScope.launch {
            val (title, imageUrl) = scrapRepo.setScrap(url)
            _title.value = title ?: ""
            _imageUrl.value = imageUrl ?: ""
        }
    }

    fun resetLiveData() {
        _imageUrl.value = ""
        _title.value = ""
    }
}