package com.example.calculatorbmi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    items: List<BmiEntry>,
    onBack: () -> Unit
) {
    Column {
        TopAppBar(title = { Text("Historia",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)) })

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items, key = { it.id }) { e ->
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp)) {
                        Text("Waga: ${String.format("%.1f", e.weightKg)} kg")
                        Text("Wzrost: ${String.format("%.0f", e.heightCm)} cm")
                        Text("BMI: ${String.format("%.2f", e.bmi)} — ${e.category.name}")
                    }
                }
            }
        }
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) { Text("Wstecz") }
    }
}
