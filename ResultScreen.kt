package com.example.calculatorbmi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun ResultScreen(
    weightKg: Float,
    heightCm: Float,
    onBack: () -> Unit,
    onHistory: () -> Unit
) {
    val h = (heightCm / 100f).coerceIn(0.5f, 2.5f)
    val bmi = weightKg / (h * h)
    val bmiRounded = ((bmi * 100).roundToInt() / 100f)

    val (label, color) = when {
        bmi < 18.5f -> "Niedowaga" to Color(0xFF42A5F5)
        bmi < 25f   -> "Normalna"  to Color(0xFF66BB6A)
        bmi < 30f   -> "Nadwaga"   to Color(0xFFFFB300)
        else        -> "Otyłość"   to Color(0xFFE53935)
    }

    val minBmi = 10f
    val maxBmi = 40f
    val clamped = bmi.coerceIn(minBmi, maxBmi)

    val wTxt = String.format("%.1f", weightKg)
    val hTxt = String.format("%.0f", heightCm)
    val bmiTxt = String.format("%.2f", bmiRounded)

    Column(Modifier.padding(24.dp)) {
        Text(
            text = "Kalkulator BMI",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Spacer(Modifier.height(32.dp))
        Text("Wpisane dane",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text("Waga: $wTxt kg",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text("Wzrost: $hTxt cm",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.height(16.dp))
        Text("BMI: $bmiTxt — $label", color = color,
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(Modifier.height(12.dp))
        Slider(
            value = clamped,
            onValueChange = {}, // wskaźnik
            valueRange = minBmi..maxBmi,
            enabled = false,
            colors = SliderDefaults.colors(
                activeTrackColor = color,
                thumbColor = color,
                disabledActiveTrackColor = color,
                disabledThumbColor = color
            )
        )
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("$minBmi")
            Text("$maxBmi")
        }

        Spacer(Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onHistory) { Text("Historia") }
            OutlinedButton(onClick = onBack) { Text("Wstecz") }
        }
    }
}
