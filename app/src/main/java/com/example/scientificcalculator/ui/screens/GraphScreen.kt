package com.example.scientificcalculator.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scientificcalculator.logic.ExpressionEvaluator

@Composable
fun GraphScreen() {
    var functionInput by remember { mutableStateOf("sin(x)") }
    var scale by remember { mutableStateOf(50f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F2027))
            .padding(16.dp)
    ) {
        Text(
            text = "Function Plotter",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = functionInput,
            onValueChange = { functionInput = it },
            label = { Text("y = f(x)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color.Cyan,
                unfocusedBorderColor = Color.Gray
            )
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        scale *= zoom
                        offset += pan
                    }
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height
                val centerX = width / 2 + offset.x
                val centerY = height / 2 + offset.y

                // Draw Grid
                for (i in -20..20) {
                    val x = centerX + i * scale
                    drawLine(Color.Gray.copy(alpha = 0.2f), Offset(x, 0f), Offset(x, height))
                    val y = centerY + i * scale
                    drawLine(Color.Gray.copy(alpha = 0.2f), Offset(0f, y), Offset(width, y))
                }

                // Draw Axes
                drawLine(Color.Gray, Offset(0f, centerY), Offset(width, centerY), 2f)
                drawLine(Color.Gray, Offset(centerX, 0f), Offset(centerX, height), 2f)

                // Plot Function
                val path = Path()
                var first = true
                
                for (px in 0 until width.toInt() step 2) {
                    val x = (px - centerX) / scale
                    val y = try {
                        val expr = functionInput.replace("x", "($x)")
                        ExpressionEvaluator.evaluate(expr).toFloat()
                    } catch (e: Exception) {
                        null
                    }

                    if (y != null && !y.isNaN() && !y.isInfinite()) {
                        val py = centerY - y * scale
                        if (first) {
                            path.moveTo(px.toFloat(), py)
                            first = false
                        } else {
                            path.lineTo(px.toFloat(), py)
                        }
                    } else {
                        first = true
                    }
                }
                
                drawPath(
                    path = path,
                    color = Color.Cyan,
                    style = Stroke(width = 4f)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            "Pinch to zoom, Drag to pan",
            color = Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}
