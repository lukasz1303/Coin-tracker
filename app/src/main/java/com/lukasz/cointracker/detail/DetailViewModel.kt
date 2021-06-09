package com.lukasz.cointracker.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lukasz.cointracker.domain.Coin
import com.lukasz.cointracker.network.NetworkCoin
import java.util.*

class DetailViewModel(coin: Coin, app: Application) : AndroidViewModel(app) {
    private val _selectedCoin = MutableLiveData<Coin>()
    val selectedCoin: LiveData<Coin>
        get() = _selectedCoin

    private val _navigateToCoinGeckoUrl = MutableLiveData<String>()
    val navigateToCoinGeckoUrl: LiveData<String>
        get() = _navigateToCoinGeckoUrl

    fun displayInWebBrowser(name: String) {
        val re = Regex("[^A-Za-z0-9]")
        var validName = re.replace(name, "-")
        validName = validName.toLowerCase(Locale.getDefault())
        _navigateToCoinGeckoUrl.value = "https://www.coingecko.com/pl/waluty/$validName"
    }

    fun displayInWebBrowserComplete() {
        _navigateToCoinGeckoUrl.value = null
    }

    init {
        _selectedCoin.value = coin
    }
}