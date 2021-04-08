package com.lukasz.cointracker.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coin(
    val id: String,
    val symbol: String?,
    val name: String?,
    val image: String?,
    val current_price: Double?,
    val market_cap: Double?,
    val market_cap_rank: Double?,
    val price_change_percentage_1h_in_currency: Double?,
    val price_change_percentage_24h_in_currency: Double?,
    val price_change_percentage_7d_in_currency: Double?,
    val fully_diluted_valuation: Double?,
    val high_24h: Double?,
    val low_24h: Double?,
    val circulating_supply: Double?,
    val max_supply: Double?,
    val ath: Double?,
    val ath_change_percentage: Double?,
    val ath_date: String?,
    val total_volume: Double?) :Parcelable
