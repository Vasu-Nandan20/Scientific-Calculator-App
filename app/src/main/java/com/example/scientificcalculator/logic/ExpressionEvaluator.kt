package com.example.scientificcalculator.logic

import org.mariuszgromada.math.mxparser.Expression

object ExpressionEvaluator {
    fun evaluate(expression: String): Double {
        val e = Expression(expression)
        return e.calculate()
    }

    fun isValid(expression: String): Boolean {
        if (expression.isEmpty()) return false
        val e = Expression(expression)
        return e.checkSyntax()
    }
    
    fun formatResult(result: Double): String {
        if (result.isNaN()) return "Error"
        if (result.isInfinite()) return "Infinity"
        val s = result.toString()
        return if (s.endsWith(".0")) s.substring(0, s.length - 2) else s
    }
}
