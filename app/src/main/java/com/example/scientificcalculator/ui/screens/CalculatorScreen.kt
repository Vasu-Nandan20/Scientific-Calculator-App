package com.example.scientificcalculator.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scientificcalculator.viewmodel.CalculatorEvent
import com.example.scientificcalculator.viewmodel.CalculatorViewModel

@Composable
fun MainScreen(viewModel: CalculatorViewModel) {
    val navController = rememberNavController()
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Calc", "Graph", "Convert")
    val icons = listOf(Icons.Default.Calculate, Icons.AutoMirrored.Filled.ShowChart, Icons.Default.SwapHoriz)

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF1A1A2E),
                contentColor = Color.White,
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(item)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Cyan,
                            unselectedIconColor = Color.Gray,
                            selectedTextColor = Color.Cyan,
                            unselectedTextColor = Color.Gray,
                            indicatorColor = Color.White.copy(alpha = 0.1f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Calc",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("Calc") { CalculatorScreen(viewModel) }
            composable("Graph") { GraphScreen() }
            composable("Convert") { ConverterScreen() }
        }
    }
}

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel) {
    val expression by viewModel.expression.collectAsState()
    val result by viewModel.result.collectAsState()
    val isExpanded by viewModel.isScientificExpanded.collectAsState()
    val history by viewModel.history.collectAsState()
    val haptic = LocalHapticFeedback.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // History Header
        if (history.isNotEmpty()) {
            Text(
                text = "History",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Box(modifier = Modifier.height(100.dp)) {
                Column(modifier = Modifier.fillMaxSize()) {
                    history.take(3).forEach { calc ->
                        Text(
                            text = "${calc.expression} = ${calc.result}",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.onEvent(CalculatorEvent.UseHistory(calc)) }
                                .padding(vertical = 2.dp),
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }

        // Display Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = expression,
                fontSize = 48.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.End,
                lineHeight = 56.sp,
                modifier = Modifier.fillMaxWidth()
            )
            AnimatedVisibility(
                visible = result.isNotEmpty(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Text(
                    text = if (result.isNotEmpty()) "= $result" else "",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
            }
        }

        // Toggle Button & Units
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { 
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.onEvent(CalculatorEvent.ToggleScientific) 
                }
            ) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = "Toggle Scientific",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = { viewModel.onEvent(CalculatorEvent.ToggleUnit) }) {
                val isDegree by viewModel.isDegree.collectAsState()
                Text(
                    text = if (isDegree) "DEG" else "RAD",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Scientific Panel
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            ScientificPanel(viewModel)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Basic Keypad
        BasicPanel(viewModel)
    }
}

@Composable
fun ScientificPanel(viewModel: CalculatorViewModel) {
    val haptic = LocalHapticFeedback.current
    var isInverse by remember { mutableStateOf(false) }
    val buttons = if (!isInverse) {
        listOf(
            "sin", "cos", "tan", "log",
            "ln", "sqrt", "^", "!",
            "π", "e", "%", "inv"
        )
    } else {
        listOf(
            "asin", "acos", "atan", "10^x",
            "e^x", "x^2", "x^y", "nCr",
            "π", "e", "%", "inv"
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(buttons) { btn ->
            CalculatorButton(
                text = btn,
                onClick = { 
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    when(btn) {
                        "π" -> viewModel.onEvent(CalculatorEvent.Number("pi"))
                        "e" -> viewModel.onEvent(CalculatorEvent.Number("e"))
                        "%" -> viewModel.onEvent(CalculatorEvent.Operator("%"))
                        "inv" -> { isInverse = !isInverse }
                        "x^2" -> viewModel.onEvent(CalculatorEvent.Operator("^2"))
                        "x^y" -> viewModel.onEvent(CalculatorEvent.Operator("^"))
                        "10^x" -> viewModel.onEvent(CalculatorEvent.Function("10^"))
                        "e^x" -> viewModel.onEvent(CalculatorEvent.Function("exp"))
                        else -> viewModel.onEvent(CalculatorEvent.Function(btn))
                    }
                },
                containerColor = if (btn == "inv" && isInverse) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
                contentColor = if (btn == "inv" && isInverse) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun BasicPanel(viewModel: CalculatorViewModel) {
    val haptic = LocalHapticFeedback.current
    val buttons = listOf(
        listOf("AC", "DEL", "(", ")"),
        listOf("7", "8", "9", "/"),
        listOf("4", "5", "6", "*"),
        listOf("1", "2", "3", "-"),
        listOf("0", ".", "+", "=")
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { btn ->
                    CalculatorButton(
                        text = btn,
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            when (btn) {
                                "AC" -> viewModel.onEvent(CalculatorEvent.Clear)
                                "DEL" -> viewModel.onEvent(CalculatorEvent.Delete)
                                "=" -> viewModel.onEvent(CalculatorEvent.Calculate)
                                "(", ")" -> viewModel.onEvent(CalculatorEvent.Number(btn))
                                "/", "*", "-", "+" -> viewModel.onEvent(CalculatorEvent.Operator(btn))
                                else -> viewModel.onEvent(CalculatorEvent.Number(btn))
                            }
                        },
                        modifier = Modifier.weight(1f),
                        containerColor = when (btn) {
                            "AC", "DEL" -> MaterialTheme.colorScheme.errorContainer
                            "=" -> MaterialTheme.colorScheme.primary
                            "/", "*", "-", "+" -> MaterialTheme.colorScheme.primaryContainer
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        },
                        contentColor = when (btn) {
                            "AC", "DEL" -> MaterialTheme.colorScheme.onErrorContainer
                            "=" -> MaterialTheme.colorScheme.onPrimary
                            "/", "*", "-", "+" -> MaterialTheme.colorScheme.onPrimaryContainer
                            else -> MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Box(
        modifier = modifier
            .aspectRatio(if (text == "=") 1f else { if (text == "0") 1f else 1.2f })
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))
            .background(containerColor)
            .border(
                width = 0.5.dp,
                color = Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(24.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = contentColor
        )
    }
}
