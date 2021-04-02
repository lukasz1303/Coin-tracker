package com.lukasz.cointracker.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lukasz.cointracker.network.*
import com.lukasz.cointracker.network.CoinGeckoApi.retrofitServiceCoinGecko
import kotlinx.coroutines.*

class OverviewViewModel :ViewModel(){

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
    get() = _response

    private val _coins = MutableLiveData<List<Coin>>()
    val coins: LiveData<List<Coin>>
        get() = _coins

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _navigateToSelectedCoin = MutableLiveData<Coin>()
    val navigateToSelectedCoin: LiveData<Coin>
        get() = _navigateToSelectedCoin

    fun displayCoinDetails(coin: Coin) {
        _navigateToSelectedCoin.value = coin
    }

    fun displayCoinDetailsComplete() {
        _navigateToSelectedCoin.value = null
    }

    init{
        getCoins()
    }

    private fun getCoins(){
        coroutineScope.launch {
            while (true){
                val getCoinsDeferred = retrofitServiceCoinGecko.getCoins()
                try {
                    val result =  getCoinsDeferred.await()
                    _response.value = "Success: ${result.size} assets retrieved"
                    _coins.value = result
                    Log.i("OverviewViewModel", "Success: ${result.size} assets retrieved\n\n\n")
                } catch (e: Exception){
                    _response.value = "Failure: ${e.message}"
                    _coins.value = ArrayList()
                    Log.i("OverviewViewModel", "Failure: ${e.message}\n\n\n")
                }
                delay(15000)
            }
        }
    }

}