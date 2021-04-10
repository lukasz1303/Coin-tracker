package com.lukasz.cointracker.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lukasz.cointracker.database.CoinsDatabase.Companion.MIGRATION_1_2


@Dao
interface CoinDao {
    @Query("select * from (select * from databasecoin order by market_cap desc LIMIT 100 OFFSET :top ) order by CASE WHEN :order = 1 THEN market_cap END DESC, CASE WHEN :order = 0 THEN price_change_percentage_24h_in_currency END DESC")
    fun getCoins(order: Int, top: Int): LiveData<List<DatabaseCoin>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(videos: List<DatabaseCoin>)

}

@Database(entities = [DatabaseCoin::class], version = 2)
abstract class CoinsDatabase : RoomDatabase() {
    abstract val coinDao: CoinDao

    companion object {

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE databasecoin "
                            + " ADD COLUMN market_cap_rank REAL"
                )
                database.execSQL(
                    "ALTER TABLE databasecoin "
                            + " ADD COLUMN fully_diluted_valuation REAL"
                )
                database.execSQL(
                    "ALTER TABLE databasecoin "
                            + " ADD COLUMN high_24h REAL"
                )
                database.execSQL(
                    "ALTER TABLE databasecoin "
                            + " ADD COLUMN low_24h REAL"
                )
                database.execSQL(
                    "ALTER TABLE databasecoin "
                            + " ADD COLUMN circulating_supply REAL"
                )
                database.execSQL(
                    "ALTER TABLE databasecoin "
                            + " ADD COLUMN max_supply REAL"
                )
                database.execSQL(
                    "ALTER TABLE databasecoin "
                            + " ADD COLUMN ath REAL"
                )
                database.execSQL(
                    "ALTER TABLE databasecoin "
                            + " ADD COLUMN ath_change_percentage REAL"
                )
                database.execSQL(
                    "ALTER TABLE databasecoin "
                            + " ADD COLUMN ath_date TEXT"
                )
            }
        }
    }
}

private lateinit var INSTANCE: CoinsDatabase

fun getDatabase(context: Context): CoinsDatabase {
    synchronized(CoinsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                CoinsDatabase::class.java,
                "coins"
            ).addMigrations(MIGRATION_1_2)
                .build()
        }
    }
    return INSTANCE
}