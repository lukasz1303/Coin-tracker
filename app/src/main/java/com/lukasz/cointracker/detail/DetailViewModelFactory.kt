package com.lukasz.cointracker.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lukasz.cointracker.network.Data

class DetailViewModelFactory(
    private val data: Data,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(data, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
