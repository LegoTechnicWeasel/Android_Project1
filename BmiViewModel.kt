package com.example.calculatorbmi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.roundToInt

data class BmiEntry(
    val id: Long,
    val weightKg: Float,
    val heightCm: Float,
    val bmi: Float,
    val category: BmiCategory
)

enum class BmiCategory { UNDERWEIGHT, NORMAL, OVERWEIGHT, OBESE }

class BmiViewModel : ViewModel() {

    private val _history = MutableStateFlow<List<BmiEntry>>(emptyList())
    val history: StateFlow<List<BmiEntry>> = _history.asStateFlow()

    private var counter = 0L

    fun computeBmi(weightKg: Float, heightCm: Float): Pair<Float, BmiCategory> {
        val hMeters = (heightCm / 100f).coerceIn(0.5f, 2.5f)
        val w = weightKg.coerceIn(10f, 400f)
        val bmi = w / (hMeters * hMeters)
        val cat = when {
            bmi < 18.5f -> BmiCategory.UNDERWEIGHT
            bmi < 25f -> BmiCategory.NORMAL
            bmi < 30f -> BmiCategory.OVERWEIGHT
            else -> BmiCategory.OBESE
        }
        return bmi to cat
    }

    fun autoSave(weightKg: Float, heightCm: Float) {
        val (bmi, cat) = computeBmi(weightKg, heightCm)
        val entry = BmiEntry(
            id = ++counter,
            weightKg = weightKg,
            heightCm = heightCm,
            bmi = ((bmi * 100).roundToInt() / 100f),
            category = cat
        )
        _history.update { listOf(entry) + it }
    }
}
