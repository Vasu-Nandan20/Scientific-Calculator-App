package com.example.scientificcalculator.logic

object ConverterLogic {
    fun convertLength(value: Double, from: String, to: String): Double {
        val inMeters = when (from) {
            "Kilometers" -> value * 1000.0
            "Meters" -> value
            "Centimeters" -> value / 100.0
            "Millimeters" -> value / 1000.0
            "Miles" -> value * 1609.34
            "Yards" -> value * 0.9144
            "Feet" -> value * 0.3048
            "Inches" -> value * 0.0254
            else -> value
        }
        return when (to) {
            "Kilometers" -> inMeters / 1000.0
            "Meters" -> inMeters
            "Centimeters" -> inMeters * 100.0
            "Millimeters" -> inMeters * 1000.0
            "Miles" -> inMeters / 1609.34
            "Yards" -> inMeters / 0.9144
            "Feet" -> inMeters / 0.3048
            "Inches" -> inMeters / 0.0254
            else -> inMeters
        }
    }

    fun convertWeight(value: Double, from: String, to: String): Double {
        val inKg = when (from) {
            "Metric Tons" -> value * 1000.0
            "Kilograms" -> value
            "Grams" -> value / 1000.0
            "Milligrams" -> value / 1000000.0
            "Pounds" -> value * 0.453592
            "Ounces" -> value * 0.0283495
            else -> value
        }
        return when (to) {
            "Metric Tons" -> inKg / 1000.0
            "Kilograms" -> inKg
            "Grams" -> inKg * 1000.0
            "Milligrams" -> inKg * 1000000.0
            "Pounds" -> inKg / 0.453592
            "Ounces" -> inKg / 0.0283495
            else -> inKg
        }
    }

    fun convertTemperature(value: Double, from: String, to: String): Double {
        val inCelsius = when (from) {
            "Celsius" -> value
            "Fahrenheit" -> (value - 32.0) * 5.0 / 9.0
            "Kelvin" -> value - 273.15
            else -> value
        }
        return when (to) {
            "Celsius" -> inCelsius
            "Fahrenheit" -> (inCelsius * 9.0 / 5.0) + 32.0
            "Kelvin" -> inCelsius + 273.15
            else -> inCelsius
        }
    }

    fun convertCurrency(value: Double, from: String, to: String): Double {
        val rates = mapOf(
            "USD" to 1.0,
            "INR" to 83.0,
            "EUR" to 0.92,
            "GBP" to 0.79,
            "JPY" to 150.0,
            "CNY" to 7.2,
            "AUD" to 1.5,
            "CAD" to 1.35
        )
        val fromRate = rates[from] ?: 1.0
        val toRate = rates[to] ?: 1.0
        return (value / fromRate) * toRate
    }

    fun convertArea(value: Double, from: String, to: String): Double {
        val inSqMeters = when (from) {
            "Square Kilometers" -> value * 1000000.0
            "Square Feet" -> value * 0.092903
            "Acres" -> value * 4046.86
            "Hectares" -> value * 10000.0
            "Square Meters" -> value
            else -> value
        }
        return when (to) {
            "Square Kilometers" -> inSqMeters / 1000000.0
            "Square Feet" -> inSqMeters / 0.092903
            "Acres" -> inSqMeters / 4046.86
            "Hectares" -> inSqMeters / 10000.0
            "Square Meters" -> inSqMeters
            else -> inSqMeters
        }
    }

    fun convertVolume(value: Double, from: String, to: String): Double {
        val inLiters = when (from) {
            "Milliliters" -> value / 1000.0
            "Cubic Meters" -> value * 1000.0
            "Gallons" -> value * 3.78541
            "Liters" -> value
            else -> value
        }
        return when (to) {
            "Milliliters" -> inLiters * 1000.0
            "Cubic Meters" -> inLiters / 1000.0
            "Gallons" -> inLiters / 3.78541
            "Liters" -> inLiters
            else -> inLiters
        }
    }

    fun convertSpeed(value: Double, from: String, to: String): Double {
        val inMs = when (from) {
            "Kilometers/Hour" -> value * 0.277778
            "Miles/Hour" -> value * 0.44704
            "Meters/Second" -> value
            else -> value
        }
        return when (to) {
            "Kilometers/Hour" -> inMs / 0.277778
            "Miles/Hour" -> inMs / 0.44704
            "Meters/Second" -> inMs
            else -> inMs
        }
    }

    fun convertPressure(value: Double, from: String, to: String): Double {
        val inPascal = when (from) {
            "Bar" -> value * 100000.0
            "Atmosphere" -> value * 101325.0
            "PSI" -> value * 6894.76
            "Pascal" -> value
            else -> value
        }
        return when (to) {
            "Bar" -> inPascal / 100000.0
            "Atmosphere" -> inPascal / 101325.0
            "PSI" -> inPascal / 6894.76
            "Pascal" -> inPascal
            else -> inPascal
        }
    }

    fun convertPower(value: Double, from: String, to: String): Double {
        val inWatts = when (from) {
            "Kilowatts" -> value * 1000.0
            "Horsepower" -> value * 745.7
            "Watts" -> value
            else -> value
        }
        return when (to) {
            "Kilowatts" -> inWatts / 1000.0
            "Horsepower" -> inWatts / 745.7
            "Watts" -> inWatts
            else -> inWatts
        }
    }

    fun convertBase(value: String, fromBase: Int, toBase: Int): String {
        return try {
            val decimal = value.toLong(fromBase)
            decimal.toString(toBase).uppercase()
        } catch (e: Exception) {
            "Error"
        }
    }
}
