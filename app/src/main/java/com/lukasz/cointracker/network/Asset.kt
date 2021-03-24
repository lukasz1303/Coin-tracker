package com.lukasz.cointracker.network

import com.squareup.moshi.Json

data class Asset(
    val status: Status,
    val data: List<Data>
)

data class Status(
    val elapsed: Double,
    val timestamp: String
)

data class Data(
    val id:String,
    @Json(name="slug") val coinName: String,
    val symbol: String,
    val metrics: Metrics
)
data class Metrics(
    val market_data: MarketData
)
data class MarketData(
    val price_usd: Double,
    val percent_change_usd_last_24_hours: Double
)
