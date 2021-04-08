package com.lukasz.cointracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lukasz.cointracker.domain.Coin

@Entity
data class DatabaseCoin constructor(
    @PrimaryKey
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
    val total_volume: Double?)

fun List<DatabaseCoin>.asDomainModel(): List<Coin> {
    return map {
        Coin(
            id = it.id,
            symbol = it.symbol,
            name = it.name,
            image = it.image,
            current_price = it.current_price,
            market_cap = it.market_cap,
            market_cap_rank = it.market_cap_rank,
            price_change_percentage_1h_in_currency = it.price_change_percentage_1h_in_currency,
            price_change_percentage_24h_in_currency = it.price_change_percentage_24h_in_currency,
            price_change_percentage_7d_in_currency = it.price_change_percentage_7d_in_currency,
            fully_diluted_valuation = it.fully_diluted_valuation,
            high_24h = it.high_24h,
            low_24h = it.low_24h,
            circulating_supply = it.circulating_supply,
            max_supply = it.max_supply,
            ath = it.ath,
            ath_change_percentage = it.ath_change_percentage,
            ath_date = it.ath_date,
            total_volume = it.total_volume)
    }
}
