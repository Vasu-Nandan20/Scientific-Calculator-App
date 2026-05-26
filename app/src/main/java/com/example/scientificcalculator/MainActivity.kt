package com.example.scientificcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scientificcalculator.ui.screens.MainScreen
import com.example.scientificcalculator.ui.theme.ScientificCalculatorTheme
import com.example.scientificcalculator.viewmodel.CalculatorViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScientificCalculatorTheme {
                val viewModel: CalculatorViewModel = viewModel()
                MainScreen(viewModel = viewModel)
            }
        }
    }
}
