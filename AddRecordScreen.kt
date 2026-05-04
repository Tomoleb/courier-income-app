package com.example.courierapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecordScreen(
    onBack: () -> Unit,
    viewModel: IncomeViewModel = viewModel(
        factory = IncomeViewModelFactory(LocalContext.current.applicationContext as CourierApp)
    )
) {
    var ordersAmount by remember { mutableStateOf("") }
    var tips by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Новая запись") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Text("←") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = ordersAmount,
                onValueChange = { ordersAmount = it },
                label = { Text("Доход от заказов, ₽") },
                singleLine = true
            )
            OutlinedTextField(
                value = tips,
                onValueChange = { tips = it },
                label = { Text("Чаевые, ₽") },
                singleLine = true
            )
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Комментарий (необязательно)") }
            )
            Button(
                onClick = {
                    val orders = ordersAmount.toDoubleOrNull() ?: 0.0
                    val tip = tips.toDoubleOrNull() ?: 0.0
                    viewModel.addRecord(orders, tip, note)
                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Сохранить")
            }
        }
    }
}
