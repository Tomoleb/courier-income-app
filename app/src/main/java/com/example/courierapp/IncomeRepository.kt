package com.example.courierapp

import kotlinx.coroutines.flow.Flow

class IncomeRepository(private val dao: IncomeDao) {
    val allRecords: Flow<List<IncomeRecord>> = dao.getAllRecords()

    suspend fun addRecord(record: IncomeRecord) = dao.insert(record)
    suspend fun deleteRecord(record: IncomeRecord) = dao.delete(record)

    suspend fun getTodayTotal(): Double {
        val cal = java.util.Calendar.getInstance().apply {
            set(java.util.Calendar.HOUR_OF_DAY, 0)
            set(java.util.Calendar.MINUTE, 0)
            set(java.util.Calendar.SECOND, 0)
            set(java.util.Calendar.MILLISECOND, 0)
        }
        val start = cal.timeInMillis
        cal.add(java.util.Calendar.DAY_OF_MONTH, 1)
        val end = cal.timeInMillis
        return dao.getTodayTotal(start, end) ?: 0.0
    }
}
