package com.example.scientificcalculator.viewmodel

import androidx.lifecycle.ViewModel
import com.example.scientificcalculator.logic.ExpressionEvaluator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalculatorViewModel : ViewModel() {
    private val _expression = MutableStateFlow("")
    val expression: StateFlow<String> = _expression.asStateFlow()

    private val _result = MutableStateFlow("")
    val result: StateFlow<String> = _result.asStateFlow()

    private val _history = MutableStateFlow<List<Calculation>>(emptyList())
    val history: StateFlow<List<Calculation>> = _history.asStateFlow()

    private val _isDegree = MutableStateFlow(value = true)
    val isDegree: StateFlow<Boolean> = _isDegree.asStateFlow()

    private val _isScientificExpanded = MutableStateFlow(value = false)
    val isScientificExpanded: StateFlow<Boolean> = _isScientificExpanded.asStateFlow()

    fun onEvent(event: CalculatorEvent) {
        when (event) {
            is CalculatorEvent.Number -> _expression.value += event.number
            is CalculatorEvent.Operator -> _expression.value += event.operator
            is CalculatorEvent.Clear -> {
                _expression.value = ""
                _result.value = ""
            }
            is CalculatorEvent.Delete -> {
                if (_expression.value.isNotEmpty()) {
                    _expression.value = _expression.value.dropLast(1)
                }
            }
            is CalculatorEvent.Calculate -> evaluateExpression()
            is CalculatorEvent.ToggleUnit -> _isDegree.value = !_isDegree.value
            is CalculatorEvent.Function -> {
                val func = when(event.function) {
                    "sin" -> if (_isDegree.value) "sind" else "sin"
                    "cos" -> if (_isDegree.value) "cosd" else "cos"
                    "tan" -> if (_isDegree.value) "tand" else "tan"
                    "asin" -> if (_isDegree.value) "asind" else "asin"
                    "acos" -> if (_isDegree.value) "acosd" else "acos"
                    "atan" -> if (_isDegree.value) "atand" else "atan"
                    else -> event.function
                }
                _expression.value += "$func("
            }
            is CalculatorEvent.ToggleScientific -> _isScientificExpanded.value = !_isScientificExpanded.value
            is CalculatorEvent.UseHistory -> {
                _expression.value = event.calculation.expression
                _result.value = event.calculation.result
            }
        }
        if ((event !is CalculatorEvent.Calculate) && (event !is CalculatorEvent.UseHistory) && _expression.value.isNotEmpty()) {
            previewResult()
        }
    }

    private fun previewResult() {
        val currentExpr = _expression.value
        if (ExpressionEvaluator.isValid(currentExpr)) {
            val res = ExpressionEvaluator.evaluate(currentExpr)
            _result.value = ExpressionEvaluator.formatResult(res)
        } else {
            _result.value = ""
        }
    }

    private fun evaluateExpression() {
        val currentExpr = _expression.value
        if (ExpressionEvaluator.isValid(currentExpr)) {
            val res = ExpressionEvaluator.evaluate(currentExpr)
            val formattedRes = ExpressionEvaluator.formatResult(res)
            val fullResult = "$currentExpr = $formattedRes"
            _history.value = listOf(Calculation(currentExpr, formattedRes)) + _history.value
            _result.value = fullResult
            _expression.value = formattedRes
        }
    }
}

data class Calculation(val expression: String, val result: String)

sealed class CalculatorEvent {
    data class Number(val number: String) : CalculatorEvent()
    data class Operator(val operator: String) : CalculatorEvent()
    data class Function(val function: String) : CalculatorEvent()
    object Clear : CalculatorEvent()
    object Delete : CalculatorEvent()
    object Calculate : CalculatorEvent()
    object ToggleUnit : CalculatorEvent()
    object ToggleScientific : CalculatorEvent()
    data class UseHistory(val calculation: Calculation) : CalculatorEvent()
}
