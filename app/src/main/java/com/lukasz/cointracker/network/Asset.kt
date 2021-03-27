package com.lukasz.cointracker.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class Asset(
    val status: Status,
    val data: List<Data>
)

data class Status(
    val elapsed: Double,
    val timestamp: String
)

@Parcelize
data class Data(
    val id:String,
    @Json(name="slug") val coinName: String,
    val symbol: String,
    val metrics: Metrics
) : Parcelable
@Parcelize
data class Metrics(
    val market_data: MarketData,
    val marketcap: MarketCap
) :Parcelable

@Parcelize
data class MarketData(
    val price_usd: Double,
    val volume_last_24_hours: Double,
    val percent_change_usd_last_24_hours: Double?,
    val percent_change_usd_last_1_hour: Double?
):Parcelable

@Parcelize
data class MarketCap(
        val current_marketcap_usd: Double
):Parcelable
