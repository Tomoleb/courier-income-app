package com.example.courierapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomeDao {
    @Query("SELECT * FROM income_records ORDER BY date DESC")
    fun getAllRecords(): Flow<List<IncomeRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: IncomeRecord)

    @Delete
    suspend fun delete(record: IncomeRecord)

    @Query("SELECT SUM(ordersAmount + tips) FROM income_records WHERE date >= :startOfDay AND date < :endOfDay")
    suspend fun getTodayTotal(startOfDay: Long, endOfDay: Long): Double?
}
