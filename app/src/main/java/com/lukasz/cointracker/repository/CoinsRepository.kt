package com.lukasz.cointracker.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.room.util.StringUtil
import com.lukasz.cointracker.database.CoinsDatabase
import com.lukasz.cointracker.database.DatabaseCoin
import com.lukasz.cointracker.database.asDomainModel
import com.lukasz.cointracker.domain.Coin
import com.lukasz.cointracker.network.CoinGeckoApi
import com.lukasz.cointracker.network.NetworkCoin
import com.lukasz.cointracker.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoinsRepository(private val database: CoinsDatabase) {

    private var _order = MutableLiveData<Int>()

    fun setOrder(order: Int) {
        _order.value = order
    }

    init {
        _order.value = 1
    }

    private var _top = MutableLiveData<Int>()

    fun setTop(top: Int) {
        _top.value = top
    }

    private var _searchedName = MutableLiveData<String>()

    fun setSearchedName(name: String) {
        _searchedName.value = name
    }

    init {
        _order.value = 1
        _top.value = 0
        _searchedName.value = ""
    }

    private val combinedValues = MediatorLiveData<Pair<Int?, Int?>>().apply {
        addSource(_order) {
            value = Pair(it, _top.value)
        }
        addSource(_top) {
            value = Pair(_order.value, it)
        }
    }

    val coins: LiveData<List<Coin>> = Transformations.switchMap(combinedValues) { pair ->
        Transformations.map(database.coinDao.getCoins(pair.first!!, pair.second!!)) {
            it.asDomainModel()
        }
    }

    val searchedCoins: LiveData<List<Coin>> = Transformations.switchMap(_searchedName) {
        Transformations.map(database.coinDao.getSelectedCoins(_searchedName.value!!)) {
            it.asDomainModel()
        }
    }

    suspend fun refreshCoins() {
        withContext(Dispatchers.IO) {
            val networkCoins =
                CoinGeckoApi.retrofitServiceCoinGecko.getCoins(_top.value!! / 100 + 1).await()
            database.coinDao.insertAll(networkCoins.asDatabaseModel())
        }
    }

    suspend fun getListOfCoins() {
        withContext(Dispatchers.IO) {
            val networkCoins =
                CoinGeckoApi.retrofitServiceCoinGecko.getListOfAllCoins().await()
            database.coinDao.insertAllCoinsIds(networkCoins.asDatabaseModel())
        }
    }

    suspend fun getSearchedCoinsData() {
        withContext(Dispatchers.IO) {
            val ids = database.coinDao.getSelectedIds(_searchedName.value!!)
            val queryIds = ids.joinToString(separator = ",")
            val networkCoins =
                CoinGeckoApi.retrofitServiceCoinGecko.getSearchedCoinsData(queryIds).await()
            database.coinDao.insertAll(networkCoins.asDatabaseModel())
        }
    }
}