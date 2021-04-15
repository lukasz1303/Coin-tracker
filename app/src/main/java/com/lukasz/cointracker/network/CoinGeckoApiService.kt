package com.lukasz.cointracker.network

import android.content.ClipData.Item
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://api.coingecko.com/api/v3/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface CoinGeckoApiService {
    @GET("coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&sparkline=false&price_change_percentage=1h%2C24h%2C7d")
    fun getCoins(@Query("page") currentPage: Int): Deferred<List<NetworkCoin>>

    @GET("coins/list")
    fun getListOfAllCoins(): Deferred<List<NetworkCoin>>

    @GET("coins/markets?vs_currency=usd&order=market_cap_desc&per_page=20&page=1&sparkline=false&price_change_percentage=1h%2C24h%2C7d")
    fun getSearchedCoinsData(@Query("ids") coinIds: String): Deferred<List<NetworkCoin>>
}

object CoinGeckoApi {
    val retrofitServiceCoinGecko: CoinGeckoApiService = retrofit.create(CoinGeckoApiService::class.java)
    }
