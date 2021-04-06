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
    val price_change_percentage_1h_in_currency: Double?,
    val price_change_percentage_24h_in_currency: Double?,
    val price_change_percentage_7d_in_currency: Double?,
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
            price_change_percentage_1h_in_currency = it.price_change_percentage_1h_in_currency,
            price_change_percentage_24h_in_currency = it.price_change_percentage_24h_in_currency,
            price_change_percentage_7d_in_currency = it.price_change_percentage_7d_in_currency,
            total_volume = it.total_volume)
    }
}
