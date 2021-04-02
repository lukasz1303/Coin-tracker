package com.lukasz.cointracker.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coin (
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val current_price: Double,
    val market_cap: Double,
    val price_change_percentage_1h_in_currency: Double,
    val price_change_percentage_24h_in_currency: Double,
    val price_change_percentage_7d_in_currency: Double,
    val total_volume: Double
) :Parcelable