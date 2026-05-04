package com.example.courierapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onAddClick: () -> Unit,
    viewModel: IncomeViewModel = viewModel(
        factory = IncomeViewModelFactory(LocalContext.current.applicationContext as CourierApp)
    )
) {
    val records by viewModel.allRecords.collectAsState()
    val todayTotal by viewModel.todayTotal.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Доход курьера") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Сегодня", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "%.2f ₽".format(todayTotal),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(records, key = { it.id }) { record ->
                    RecordItem(record = record, onDelete = { viewModel.deleteRecord(record) })
                }
            }
        }
    }
}

@Composable
fun RecordItem(record: IncomeRecord, onDelete: () -> Unit) {
    val dateFormat = remember { SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()) }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(dateFormat.format(record.date), style = MaterialTheme.typography.bodySmall)
                Text("Заказы: %.2f ₽  Чаевые: %.2f ₽".format(record.ordersAmount, record.tips))
                if (record.note.isNotBlank()) Text(record.note, style = MaterialTheme.typography.bodySmall)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("%.2f ₽".format(record.total), style = MaterialTheme.typography.titleMedium)
                TextButton(onClick = onDelete) { Text("Удалить") }
            }
        }
    }
}
