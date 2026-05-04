package com.example.courierapp

import android.app.Application
import androidx.room.Room

class CourierApp : Application() {
    lateinit var database: AppDatabase
    lateinit var repository: IncomeRepository

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java, "courier_db").build()
        repository = IncomeRepository(database.incomeDao())
    }
}
