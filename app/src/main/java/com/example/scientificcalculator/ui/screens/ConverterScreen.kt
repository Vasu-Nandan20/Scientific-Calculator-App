package com.example.scientificcalculator.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scientificcalculator.logic.ConverterLogic

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterScreen() {
    var selectedCategory by remember { mutableStateOf<ConverterCategory?>(null) }

    val categories = listOf(
        ConverterCategory("Length", Icons.Default.Straighten, Color(0xFF64B5F6)),
        ConverterCategory("Weight", Icons.Default.FitnessCenter, Color(0xFF81C784)),
        ConverterCategory("Temperature", Icons.Default.Thermostat, Color(0xFFFFB74D)),
        ConverterCategory("Currency", Icons.Default.AttachMoney, Color(0xFFBA68C8)),
        ConverterCategory("Area", Icons.Default.SquareFoot, Color(0xFF4DB6AC)),
        ConverterCategory("Volume", Icons.Default.Opacity, Color(0xFF4FC3F7)),
        ConverterCategory("Speed", Icons.Default.Speed, Color(0xFFFF8A65)),
        ConverterCategory("Pressure", Icons.Default.Compress, Color(0xFFAED581)),
        ConverterCategory("Power", Icons.Default.Bolt, Color(0xFFFFF176)),
        ConverterCategory("Base", Icons.Default.Numbers, Color(0xFF90A4AE))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1A1A2E), Color(0xFF16213E))
                )
            )
            .padding(16.dp)
    ) {
        Text(
            text = "Unit Converter",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        if (selectedCategory == null) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(categories) { category ->
                    CategoryCard(category) { selectedCategory = category }
                }
            }
        } else {
            CategoryDetail(selectedCategory!!) { selectedCategory = null }
        }
    }
}

@Composable
fun CategoryCard(category: ConverterCategory, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.2f)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = category.name,
                tint = category.color,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = category.name,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun CategoryDetail(category: ConverterCategory, onBack: () -> Unit) {
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    
    val units = when (category.name) {
        "Length" -> listOf("Kilometers", "Meters", "Centimeters", "Millimeters", "Miles", "Yards", "Feet", "Inches")
        "Weight" -> listOf("Metric Tons", "Kilograms", "Grams", "Milligrams", "Pounds", "Ounces")
        "Temperature" -> listOf("Celsius", "Fahrenheit", "Kelvin")
        "Currency" -> listOf("USD", "INR", "EUR", "GBP", "JPY", "CNY", "AUD", "CAD")
        "Area" -> listOf("Square Meters", "Square Kilometers", "Square Feet", "Acres", "Hectares")
        "Volume" -> listOf("Liters", "Milliliters", "Cubic Meters", "Gallons")
        "Speed" -> listOf("Meters/Second", "Kilometers/Hour", "Miles/Hour")
        "Pressure" -> listOf("Pascal", "Bar", "Atmosphere", "PSI")
        "Power" -> listOf("Watts", "Kilowatts", "Horsepower")
        "Base" -> listOf("Decimal", "Hex", "Binary", "Octal")
        else -> listOf("Unit A", "Unit B")
    }
    
    var fromUnit by remember { mutableStateOf(units[0]) }
    var toUnit by remember { mutableStateOf(units[1]) }

    LaunchedEffect(inputValue, fromUnit, toUnit) {
        if (inputValue.isEmpty()) {
            outputValue = ""
            return@LaunchedEffect
        }
        outputValue = when (category.name) {
            "Length" -> ConverterLogic.convertLength(inputValue.toDoubleOrNull() ?: 0.0, fromUnit, toUnit).toString()
            "Weight" -> ConverterLogic.convertWeight(inputValue.toDoubleOrNull() ?: 0.0, fromUnit, toUnit).toString()
            "Temperature" -> ConverterLogic.convertTemperature(inputValue.toDoubleOrNull() ?: 0.0, fromUnit, toUnit).toString()
            "Currency" -> ConverterLogic.convertCurrency(inputValue.toDoubleOrNull() ?: 0.0, fromUnit, toUnit).toString()
            "Area" -> ConverterLogic.convertArea(inputValue.toDoubleOrNull() ?: 0.0, fromUnit, toUnit).toString()
            "Volume" -> ConverterLogic.convertVolume(inputValue.toDoubleOrNull() ?: 0.0, fromUnit, toUnit).toString()
            "Speed" -> ConverterLogic.convertSpeed(inputValue.toDoubleOrNull() ?: 0.0, fromUnit, toUnit).toString()
            "Pressure" -> ConverterLogic.convertPressure(inputValue.toDoubleOrNull() ?: 0.0, fromUnit, toUnit).toString()
            "Power" -> ConverterLogic.convertPower(inputValue.toDoubleOrNull() ?: 0.0, fromUnit, toUnit).toString()
            "Base" -> {
                val fromBase = when(fromUnit) { "Decimal" -> 10; "Hex" -> 16; "Binary" -> 2; "Octal" -> 8; else -> 10 }
                val toBase = when(toUnit) { "Decimal" -> 10; "Hex" -> 16; "Binary" -> 2; "Octal" -> 8; else -> 10 }
                ConverterLogic.convertBase(inputValue, fromBase, toBase)
            }
            else -> "Feature coming soon"
        }
    }

    Column {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(category.name, color = category.color, fontWeight = FontWeight.Bold)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // From
                UnitInput(
                    label = "From",
                    value = inputValue,
                    onValueChange = { inputValue = it },
                    selectedUnit = fromUnit,
                    units = units,
                    onUnitChange = { fromUnit = it }
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // To
                UnitInput(
                    label = "To",
                    value = outputValue,
                    onValueChange = {},
                    selectedUnit = toUnit,
                    units = units,
                    onUnitChange = { toUnit = it },
                    readOnly = true
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    selectedUnit: String,
    units: List<String>,
    onUnitChange: (String) -> Unit,
    readOnly: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, color = Color.Gray, fontSize = 14.sp)
            Box {
                Text(
                    text = selectedUnit,
                    color = Color.Cyan,
                    modifier = Modifier.clickable { expanded = true }
                )
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    units.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit) },
                            onClick = {
                                onUnitChange(unit)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            readOnly = readOnly,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )
    }
}

data class ConverterCategory(val name: String, val icon: ImageVector, val color: Color)
