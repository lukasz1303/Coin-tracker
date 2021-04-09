package com.lukasz.cointracker.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lukasz.cointracker.database.CoinsDatabase
import com.lukasz.cointracker.database.DatabaseCoin
import com.lukasz.cointracker.database.asDomainModel
import com.lukasz.cointracker.domain.Coin
import com.lukasz.cointracker.network.CoinGeckoApi
import com.lukasz.cointracker.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoinsRepository(private val database: CoinsDatabase) {

    private var _order = MutableLiveData<Int>()

    fun setOrder(order: Int) {
        _order.value = order
    }

    init{
        _order.value = 1
    }

    val coins: LiveData<List<Coin>> = Transformations.switchMap(_order) {
        Transformations.map(database.coinDao.getCoins(_order.value!!)) {
            it.asDomainModel()
        }
    }

        suspend fun refreshCoins() {
            withContext(Dispatchers.IO) {
                val networkCoins = CoinGeckoApi.retrofitServiceCoinGecko.getCoins().await()
                database.coinDao.insertAll(networkCoins.asDatabaseModel())
            }
        }
}