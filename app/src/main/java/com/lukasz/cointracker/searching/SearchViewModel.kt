package com.lukasz.cointracker.searching

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lukasz.cointracker.database.getDatabase
import com.lukasz.cointracker.domain.Coin
import com.lukasz.cointracker.repository.CoinsRepository
import kotlinx.coroutines.*

class SearchViewModel (application: Application) : AndroidViewModel(application) {

    private val coinsRepository = CoinsRepository(getDatabase(application))

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun setSearchedName(name: String) {
        coinsRepository.setSearchedName(name)
    }

    private val _navigateToSelectedCoin = MutableLiveData<Coin>()
    val navigateToSelectedCoin: LiveData<Coin>
        get() = _navigateToSelectedCoin

    fun displayCoinDetails(coin: Coin) {
        _navigateToSelectedCoin.value = coin
    }

    fun displayCoinDetailsComplete() {
        _navigateToSelectedCoin.value = null
    }

    val searchedCoin = coinsRepository.searchedCoins


}