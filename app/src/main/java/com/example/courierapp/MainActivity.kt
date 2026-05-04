package com.example.courierapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.courierapp.ui.theme.CourierAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CourierAppTheme {
                var showAddScreen by remember { mutableStateOf(false) }

                if (showAddScreen) {
                    AddRecordScreen(onBack = { showAddScreen = false })
                } else {
                    MainScreen(onAddClick = { showAddScreen = true })
                }
            }
        }
    }
}
