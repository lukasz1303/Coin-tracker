package com.lukasz.cointracker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.lukasz.cointracker.database.CoinsDatabase
import com.lukasz.cointracker.database.asDomainModel
import com.lukasz.cointracker.domain.Coin
import com.lukasz.cointracker.network.CoinGeckoApi
import com.lukasz.cointracker.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoinsRepository(private val database: CoinsDatabase) {

    val coins: LiveData<List<Coin>> = Transformations.map(database.coinDao.getCoins()) {
        it.asDomainModel()
    }

        suspend fun refreshCoins() {
        withContext(Dispatchers.IO) {
            val coins = CoinGeckoApi.retrofitServiceCoinGecko.getCoins().await()
            database.coinDao.insertAll(coins.asDatabaseModel())

        }
    }

}