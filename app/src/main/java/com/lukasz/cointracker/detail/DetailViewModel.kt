package com.lukasz.cointracker.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lukasz.cointracker.network.Coin

class DetailViewModel(coin: Coin, app: Application) : AndroidViewModel(app) {
    private val _selectedCoin = MutableLiveData<Coin>()
    val selectedCoin: LiveData<Coin>
        get() = _selectedCoin

    init {
        _selectedCoin.value = coin
    }
}