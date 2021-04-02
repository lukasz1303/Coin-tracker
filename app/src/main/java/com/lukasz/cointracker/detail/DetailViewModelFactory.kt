package com.lukasz.cointracker.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lukasz.cointracker.network.Coin

class DetailViewModelFactory(
    private val coin: Coin,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(coin, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
