package com.lukasz.cointracker.overview

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lukasz.cointracker.database.getDatabase
import com.lukasz.cointracker.domain.Coin
import com.lukasz.cointracker.repository.CoinsRepository
import kotlinx.coroutines.*

class OverviewViewModel (application: Application) : AndroidViewModel(application){

    private val coinsRepository = CoinsRepository(getDatabase(application))

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun setOrder(order: Int) {
        coinsRepository.setOrder(order)
    }

    fun setTop(top: Int) {
        coinsRepository.setTop(top-100)
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

    init{
        refreshDataFromRepository()
    }

    val coins = coinsRepository.coins

    private fun refreshDataFromRepository() {
        coroutineScope.launch {
            while (true){
                try {
                    coinsRepository.refreshCoins()
                    Log.i("OverviewViewModel", "Success: assets retrieved\n\n\n")

                } catch (e: Exception){
                    Log.i("OverviewViewModel", "Failure: ${e.message}\n\n\n")
                }
                delay(15000)
            }
        }
    }
    suspend fun singleRefreshDataFromRepository(): Int {
        var res = 0
        coroutineScope.launch {
            res = try {
                coinsRepository.refreshCoins()
                Log.i("OverviewViewModel", "Success: assets retrieved\n\n\n")
                1

            } catch (e: Exception) {
                Log.i("OverviewViewModel", "Failure: ${e.message}\n\n\n")
                2
            }
        }.join()

        return res
    }

}