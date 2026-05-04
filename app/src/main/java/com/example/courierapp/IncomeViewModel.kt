package com.example.courierapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class IncomeViewModel(private val repository: IncomeRepository) : ViewModel() {
    val allRecords: StateFlow<List<IncomeRecord>> = repository.allRecords
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _todayTotal = MutableStateFlow(0.0)
    val todayTotal: StateFlow<Double> = _todayTotal

    init { refreshTodayTotal() }

    fun addRecord(ordersAmount: Double, tips: Double, note: String) {
        viewModelScope.launch {
            repository.addRecord(
                IncomeRecord(ordersAmount = ordersAmount, tips = tips, note = note)
            )
            refreshTodayTotal()
        }
    }

    fun deleteRecord(record: IncomeRecord) {
        viewModelScope.launch {
            repository.deleteRecord(record)
            refreshTodayTotal()
        }
    }

    private fun refreshTodayTotal() {
        viewModelScope.launch {
            _todayTotal.value = repository.getTodayTotal()
        }
    }
}
