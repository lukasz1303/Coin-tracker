package com.lukasz.cointracker.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lukasz.cointracker.network.Data

class DetailViewModel(data: Data, app: Application) : AndroidViewModel(app) {
    private val _selectedCoin = MutableLiveData<Data>()
    val selectedCoin: LiveData<Data>
        get() = _selectedCoin

    init {
        _selectedCoin.value = data
    }
}