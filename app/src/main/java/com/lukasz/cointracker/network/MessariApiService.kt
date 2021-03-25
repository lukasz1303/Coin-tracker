package com.lukasz.cointracker.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.Retrofit
import retrofit2.http.GET
import kotlin.coroutines.CoroutineContext

private const val BASE_URL = "https://data.messari.io/api/v1/"
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

interface MessariApiService {
        @GET("assets?limit=50&fields=id,slug,symbol,metrics/market_data/price_usd,metrics/market_data/percent_change_usd_last_24_hours")
        fun getAssets(): Deferred<Asset>

}

object MessariApi {
        val retrofitService : MessariApiService by lazy {
                retrofit.create(MessariApiService::class.java)
        }
}
