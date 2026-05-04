package com.example.courierapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "income_records")
data class IncomeRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Date = Date(),
    val ordersAmount: Double = 0.0,
    val tips: Double = 0.0,
    val note: String = ""
) {
    val total: Double get() = ordersAmount + tips
}
