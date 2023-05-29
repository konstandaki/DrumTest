package com.konstandaki.drumtest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.konstandaki.drumtest.data.SportsRepository
import com.konstandaki.drumtest.model.Sport
import kotlinx.coroutines.launch
import java.io.IOException

class SportsViewModel(private val sportsRepository: SportsRepository) : ViewModel() {

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private val _sports = MutableLiveData<List<Sport>>()
    val sports: LiveData<List<Sport>> = _sports

    init {
        getSports()
    }

    private fun getSports() {
        viewModelScope.launch {
            try {
                _sports.value = sportsRepository.getSports()
                _eventNetworkError.value = false
            } catch (networkError: IOException) {
                _sports.value = listOf()
                _eventNetworkError.value = true
            }
        }
    }
}

class SportsViewModelFactory(private val sportsRepository: SportsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SportsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SportsViewModel(sportsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}