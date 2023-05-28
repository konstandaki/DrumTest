package com.konstandaki.drumtest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.konstandaki.drumtest.data.SportsRepository
import com.konstandaki.drumtest.model.Sport
import kotlinx.coroutines.launch

class SportsViewModel(private val sportsRepository: SportsRepository) : ViewModel() {

    private val _status = MutableLiveData<SportsApiStatus>()
    val status: LiveData<SportsApiStatus> = _status

    private val _sports = MutableLiveData<List<Sport>>()
    val sports: LiveData<List<Sport>> = _sports

    init {
        getSports()
    }

    private fun getSports() {
        viewModelScope.launch {
            _status.value = SportsApiStatus.LOADING
            try {
                _sports.value = sportsRepository.getSports()
                _status.value = SportsApiStatus.DONE
            } catch (e: Exception) {
                _status.value = SportsApiStatus.ERROR
                _sports.value = listOf()
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

enum class SportsApiStatus { LOADING, ERROR, DONE }