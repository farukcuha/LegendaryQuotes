package com.pandorina.legendaryquotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.pandorina.legendaryquotes.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val getBackgroundImage = repository.getCurrentBackgroundImage.asLiveData()

    fun changeBackgroundImage(imageId: Int) = viewModelScope.launch {
        repository.changeBackgroundImage(imageId)
    }
}